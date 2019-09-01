package cn.ztcaoll222.write.helper.sync.core.bucket;

import cn.ztcaoll222.write.helper.sync.interfaces.bucket.BucketService;
import cn.ztcaoll222.write.helper.sync.rsync.entity.Patch;
import lombok.extern.flogger.Flogger;
import org.redisson.api.RMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author ztcaoll222
 * Create time: 2019/8/27 17:41
 */
@Service(value = "bucketService")
@Flogger
public class BucketServiceImpl implements BucketService {
    @Resource(name = "syncBucket")
    private RMap<String, Patch> bucket;

    private String genKey(String... keys) {
        return String.join(":", keys);
    }

    @Override
    public boolean save(String id, String rootDirName, String filename, Patch patch) {
        String key = genKey(id, rootDirName, filename);
        return Optional.ofNullable(bucket.get(key)).map(tPatch -> {
            if (tPatch.getTimestamp() < patch.getTimestamp()) {
                bucket.put(key, patch);
                return true;
            } else {
                return false;
            }
        }).orElseGet(() -> {
            bucket.put(key, patch);
            return true;
        });
    }
}
