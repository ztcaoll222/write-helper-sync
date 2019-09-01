package cn.ztcaoll222.write.helper.sync.common;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author ztcaoll222
 * Create time: 2019/7/26 18:48
 */
public class GlobalConst {
    private GlobalConst() {
    }

    /**
     * map 的默认大小
     */
    public static int DEFAULT_MAP_SIZE = 16;

    /**
     * 默认的字符集
     */
    public static Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
}
