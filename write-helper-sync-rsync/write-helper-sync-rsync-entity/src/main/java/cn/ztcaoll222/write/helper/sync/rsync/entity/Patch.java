package cn.ztcaoll222.write.helper.sync.rsync.entity;

import com.google.common.primitives.Bytes;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * 抽象的补丁类, 源文件 + 补丁 = 目标文件
 *
 * @author ztcaoll222
 * Create time: 2019/9/1 14:17
 */
@Data
public class Patch implements Serializable {
    private static final long serialVersionUID = -7977101584533177393L;

    @Setter(AccessLevel.NONE)
    private List<PatchPart> patchPartList = new ArrayList<>();

    @Setter(AccessLevel.NONE)
    private long timestamp;

    public Patch() {
        this.timestamp = System.currentTimeMillis();
    }

    public Patch add(List<Byte> byteList) {
        byte[] bytes = Bytes.toArray(byteList);
        String base64 = Base64.getEncoder().encodeToString(bytes);
        patchPartList.add(new UnMatchPatchPart(base64));
        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public Patch add(Chunk chunk) {
        patchPartList.add(new MatchPatchPart(chunk.getIndex()));
        this.timestamp = System.currentTimeMillis();
        return this;
    }
}
