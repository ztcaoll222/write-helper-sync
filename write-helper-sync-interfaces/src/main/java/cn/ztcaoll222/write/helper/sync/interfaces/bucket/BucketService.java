package cn.ztcaoll222.write.helper.sync.interfaces.bucket;

/**
 * @author ztcaoll222
 * Create time: 2019/8/27 17:39
 */
public interface BucketService {
    boolean save(String id, String path, String text);

    String get(String id, String path, String text);
}
