package cn.ztcaoll222.write.helper.sync.interfaces.bucket;

import cn.ztcaoll222.write.helper.sync.rsync.entity.Patch;

/**
 * @author ztcaoll222
 * Create time: 2019/8/27 17:39
 */
public interface BucketService {
    boolean save(String id, String rootDirName, String filename, Patch patch);
}
