package cn.ztcaoll222.write.helper.sync.entity;

import lombok.Getter;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author ztcaoll222
 * Create time: 2019/9/1 14:54
 */
@Getter
public enum ResponseEnum {
    /**
     * success
     */
    SUCCESS("success", 200),
    /**
     * error
     */
    ERROR("error", 1),
    /**
     * forbidden
     */
    FORBIDDEN("forbidden", 403),
    /**
     * no found
     */
    NO_FOUND("no found", 404),
    /**
     * unknown
     */
    OTHER("unknown", 0);

    private final String message;
    private final int code;

    ResponseEnum(String message, int code) {
        this.message = message;
        this.code = code;
    }

    @Nonnull
    public static ResponseEnum fromCode(Integer code, ResponseEnum defaultObj) {
        defaultObj = Objects.requireNonNullElse(defaultObj, OTHER);
        return Arrays.stream(ResponseEnum.values())
                .filter(it -> Objects.equals(code, it.getCode()))
                .findFirst()
                .orElse(defaultObj);
    }
}
