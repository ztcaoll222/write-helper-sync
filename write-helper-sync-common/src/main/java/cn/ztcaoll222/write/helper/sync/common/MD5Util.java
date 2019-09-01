package cn.ztcaoll222.write.helper.sync.common;

import lombok.extern.flogger.Flogger;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

/**
 * md5 工具类
 *
 * @author ztcaoll222
 * Create time: 2019/9/1 17:14
 */
@Flogger
public class MD5Util {
    public static byte[] encode(@NotNull byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(bytes);
        return md.digest();
    }

    public static byte[] encode(@NotNull String str, @NotNull Charset charset) throws NoSuchAlgorithmException {
        return encode(str.getBytes(charset));
    }

    public static byte[] encode(@NotNull String str) throws NoSuchAlgorithmException {
        return encode(str, GlobalConst.DEFAULT_CHARSET);
    }

    public static String encodeToBase64(@NotNull byte[] bytes) throws NoSuchAlgorithmException {
        return Base64.getEncoder().encodeToString(encode(bytes));
    }

    public static Optional<String> encodeToBase64Optional(@NotNull byte[] bytes) {
        try {
            return Optional.ofNullable(Base64.getEncoder().encodeToString(encode(bytes)));
        } catch (NoSuchAlgorithmException e) {
            log.atSevere().log(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public static String encodeToBase64(@NotNull String str) throws NoSuchAlgorithmException {
        return Base64.getEncoder().encodeToString(encode(str));
    }

    public static Optional<String> encodeToBase64Optional(@NotNull String str) {
        try {
            return Optional.ofNullable(Base64.getEncoder().encodeToString(encode(str)));
        } catch (NoSuchAlgorithmException e) {
            log.atSevere().log(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public static String encodeToHex(@NotNull byte[] bytes) throws NoSuchAlgorithmException {
        return StringUtil.bytesToHex(encode(bytes));
    }

    public static Optional<String> encodeToHexOptional(@NotNull byte[] bytes) {
        try {
            return Optional.of(encodeToHex(bytes));
        } catch (NoSuchAlgorithmException e) {
            log.atSevere().log(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public static String encodeToHex(@NotNull String str) throws NoSuchAlgorithmException {
        return StringUtil.bytesToHex(encode(str));
    }

    public static Optional<String> encodeToHexOptional(@NotNull String str) {
        try {
            return Optional.of(encodeToHex(str));
        } catch (NoSuchAlgorithmException e) {
            log.atSevere().log(e.getMessage(), e);
            return Optional.empty();
        }
    }
}
