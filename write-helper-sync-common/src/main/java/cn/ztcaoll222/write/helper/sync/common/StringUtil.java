package cn.ztcaoll222.write.helper.sync.common;

import org.jetbrains.annotations.NotNull;

/**
 * @author ztcaoll222
 * Create time: 2019/9/1 17:28
 */
public class StringUtil {
    private StringUtil() {
    }

    public static String bytesToHex(@NotNull byte[] hashInBytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
