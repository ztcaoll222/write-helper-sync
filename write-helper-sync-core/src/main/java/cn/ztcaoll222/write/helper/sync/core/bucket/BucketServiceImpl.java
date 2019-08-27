package cn.ztcaoll222.write.helper.sync.core.bucket;

import cn.ztcaoll222.write.helper.sync.interfaces.bucket.BucketService;
import lombok.extern.flogger.Flogger;
import org.redisson.api.RMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ztcaoll222
 * Create time: 2019/8/27 17:41
 */
@Service(value = "bucketService")
@Flogger
public class BucketServiceImpl implements BucketService {
    @Resource(name = "syncBucket")
    private RMap<String, String> bucket;

    private String genKey(String... keys) {
        return String.join(":", keys);
    }

    @Override
    public boolean save(String id, String path, String text) {
        String key = genKey(id, path);
        return bucket.fastPut(key, text);
    }

    @Override
    public String get(String id, String path, String text) {
        String key = genKey(id, path);
        return bucket.get(key);
    }
}
