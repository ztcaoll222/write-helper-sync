package cn.ztcaoll222.write.helper.sync.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ztcaoll222
 * Create time: 2019/9/1 14:53
 */
@Data
public class ResponseEntity<T> implements Serializable {
    private static final long serialVersionUID = 3490582556921946946L;

    private int code;

    private String message;

    private T data;

    public ResponseEntity(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseEntity<T> create(ResponseEnum responseEnum, T data) {
        return new ResponseEntity<T>(responseEnum.getCode(), responseEnum.getMessage(), data);
    }

    public static <T> ResponseEntity<T> create(ResponseEnum responseEnum, String message, T data) {
        return new ResponseEntity<T>(responseEnum.getCode(), message, data);
    }

    public static <T> ResponseEntity<T> create(ResponseEnum responseEnum) {
        return new ResponseEntity<T>(responseEnum.getCode(), responseEnum.getMessage(), null);
    }

    public static <T> ResponseEntity<T> create(ResponseEnum responseEnum, String message) {
        return new ResponseEntity<T>(responseEnum.getCode(), message, null);
    }
}
