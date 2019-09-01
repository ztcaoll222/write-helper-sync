package cn.ztcaoll222.write.helper.sync.rsync.core;

import cn.ztcaoll222.write.helper.sync.common.FileUtil;
import cn.ztcaoll222.write.helper.sync.common.GlobalConst;
import cn.ztcaoll222.write.helper.sync.common.MD5Util;
import cn.ztcaoll222.write.helper.sync.rsync.entity.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * rsync 工具类
 *
 * @author ztcaoll222
 * Create time: 2019/9/1 14:49
 */
public class Rsync {
    /**
     * 块大小
     */
    private static final int CHUNK_SIZE = 512;

    /**
     * 计算每块的 checksum
     */
    @NotNull
    private static Chunk calcCheckSum(byte[] bytes) throws NoSuchAlgorithmException {
        Chunk chunk = new Chunk();
        chunk.setWeakCheckSum(Alder32.sum(bytes));
        chunk.setStrongCheckSum(MD5Util.encodeToBase64(bytes));
        return chunk;
    }

    /**
     * 计算文件的 CheckSum 列表
     *
     * @param filePath 文件路径
     */
    public static Map<Long, List<Chunk>> calcCheckSumFromFile(@NotNull String filePath) throws Exception {
        Map<Long, List<Chunk>> chunkMaps = new HashMap<>(GlobalConst.DEFAULT_MAP_SIZE);

        File file = FileUtil.checkFile(filePath);

        long length = file.length();
        int index = 0;
        RandomAccessFile raf = new RandomAccessFile(filePath, "r");
        while (length > 0) {
            long readSize = Math.min(length, CHUNK_SIZE);
            byte[] bytes = new byte[Math.toIntExact(readSize)];
            raf.read(bytes);
            Chunk chunk = calcCheckSum(bytes);
            chunk.setIndex(index++);

            Optional.of(chunkMaps.get(chunk.getWeakCheckSum())).ifPresentOrElse(chunkList -> chunkList.add(chunk),
                    () -> {
                        List<Chunk> chunkList = new ArrayList<>();
                        chunkList.add(chunk);
                        chunkMaps.put(chunk.getWeakCheckSum(), chunkList);
                    });

            length -= readSize;
        }

        return chunkMaps;
    }

    /**
     * 根据传入的字节数组去匹配，如果匹配到则返回该 Chunk, 否者为空
     */
    public static Optional<Chunk> match(Map<Long, List<Chunk>> chunkMaps, byte[] bytes) {
        return Optional.of(chunkMaps.get(Alder32.sum(bytes)))
                .flatMap(chunkList -> chunkList.stream().
                        filter(chunk -> MD5Util.encodeToBase64Optional(bytes).map(chunk.getStrongCheckSum()::equals)
                                .orElse(false))
                        .findFirst());
    }

    /**
     * 读取下一块
     */
    public static byte[] readNextChunk(@NotNull RandomAccessFile raf, long length) throws IOException {
        long readSize = Math.min(length, CHUNK_SIZE);
        byte[] bytes = new byte[Math.toIntExact(readSize)];
        raf.read(bytes);
        return bytes;
    }

    /**
     * 抛去头一个字节, 读取下一个字节
     */
    public static void readNextByte(@NotNull RandomAccessFile raf, byte[] bytes) throws IOException {
        byte[] next = new byte[1];
        raf.read(next);
        if (bytes.length - 1 >= 0) {
            System.arraycopy(bytes, 1, bytes, 0, bytes.length - 1);
        }
        bytes[bytes.length - 1] = next[0];
    }

    /**
     * 创建补丁
     */
    public static Patch createPatch(@NotNull String filePath, Map<Long, List<Chunk>> chunkMaps) throws Exception {
        Patch patch = new Patch();

        File file = FileUtil.checkFile(filePath);

        RandomAccessFile raf = new RandomAccessFile(file, "r");
        long length = file.length();
        byte[] bytes = {};
        List<Byte> diffBytes = new ArrayList<>();
        boolean nextBlock = true;
        Chunk chunk = null;

        while (length > 0) {
            if (nextBlock) {
                bytes = readNextChunk(raf, length);
                length -= bytes.length;
            } else {
                readNextByte(raf, bytes);
                length--;
            }

            //判断是否匹配
            Optional<Chunk> match = match(chunkMaps, bytes);
            if (match.isPresent()) {
                chunk = match.get();
                // 如果 diff bytes 不为空, 先将它加进 patch
                if (diffBytes.size() > 0) {
                    patch.add(diffBytes);
                    diffBytes = new ArrayList<>();
                }
                patch.add(chunk);
                nextBlock = true;
            } else {
                // 保存头一个字节
                diffBytes.add(bytes[0]);
                nextBlock = false;
            }
        }

        //最后一个 block 没有匹配上 需要把 bytes 中的剩余数据加入到 diffBytes 中，头一个字节已经加入了
        if (chunk == null) {
            for (int i = 1; i < bytes.length; i++) {
                byte aByte = bytes[i];
                diffBytes.add(aByte);
            }
        }
        patch.add(diffBytes);
        raf.close();

        return patch;
    }

    /**
     * 接受补丁
     */
    public static void applyPatch(@NotNull Patch patch, @NotNull String filePath) throws Exception {
        File file = FileUtil.checkFile(filePath);

        String tFilePath = filePath + ".tmp";
        File tFile = new File(tFilePath);

        long length = file.length();
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        RandomAccessFile tRaf = new RandomAccessFile(tFile, "w+");

        for (PatchPart part : patch.getPatchPartList()) {
            if (part instanceof UnMatchPatchPart) {
                byte[] bytes = Base64.getDecoder().decode(((UnMatchPatchPart) part).getContent());
                tRaf.write(bytes);
            } else {
                MatchPatchPart matchPatchPart = (MatchPatchPart) part;

                long pos = matchPatchPart.getIndex() * CHUNK_SIZE;
                raf.seek(pos);

                long size = Math.min(length - pos, CHUNK_SIZE);
                byte[] bytes = new byte[Math.toIntExact(size)];
                raf.read(bytes);
                tRaf.write(bytes);
            }
        }

        raf.close();
        tRaf.close();

        file.deleteOnExit();
        if (!tFile.renameTo(file)) {
            throw new Exception(String.format("%s rename to %s fault!", tFilePath, filePath));
        }

        tFile.deleteOnExit();
    }
}
