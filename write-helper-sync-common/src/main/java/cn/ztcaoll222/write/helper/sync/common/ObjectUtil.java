package cn.ztcaoll222.write.helper.sync.common;

import java.util.Arrays;

/**
 * @author ztcaoll222
 * Create time: 2019/7/26 18:23
 */
public class ObjectUtil {
    private ObjectUtil() {
    }

    public static boolean checkObj(Object obj) {
        return obj != null && !obj.toString().trim().isEmpty();
    }

    public static boolean checkObj(Object... objs) {
        return Arrays.stream(objs).allMatch(ObjectUtil::checkObj);
    }
}
