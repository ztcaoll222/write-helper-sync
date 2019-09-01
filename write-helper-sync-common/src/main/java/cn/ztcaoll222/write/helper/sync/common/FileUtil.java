package cn.ztcaoll222.write.helper.sync.common;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * @author ztcaoll222
 * Create time: 2019/9/1 21:34
 */
public class FileUtil {
    private FileUtil() {
    }

    public static File checkFile(@NotNull String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new Exception(String.format("%s is not exists", filePath));
        }
        return file;
    }
}
