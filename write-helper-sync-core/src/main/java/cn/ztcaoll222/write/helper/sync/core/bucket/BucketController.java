package cn.ztcaoll222.write.helper.sync.core.bucket;

import cn.ztcaoll222.write.helper.sync.common.ObjectUtil;
import cn.ztcaoll222.write.helper.sync.entity.ResponseEntity;
import cn.ztcaoll222.write.helper.sync.entity.ResponseEnum;
import cn.ztcaoll222.write.helper.sync.interfaces.bucket.BucketService;
import cn.ztcaoll222.write.helper.sync.rsync.entity.Patch;
import lombok.extern.flogger.Flogger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ztcaoll222
 * Create time: 2019/8/27 17:30
 */
@RestController
@RequestMapping("/bucket")
@Flogger
public class BucketController {
    @Resource(name = "bucketService")
    private BucketService bucketService;

    @PostMapping("/sync")
    public ResponseEntity<Boolean> sync(String id, String rootDirName, String filename, Patch patch) {
        log.atInfo().log("sync: id: %s", id);
        if (!ObjectUtil.checkObj(id, rootDirName, filename, patch)) {
            return ResponseEntity.create(ResponseEnum.ERROR);
        }
        return ResponseEntity.create(ResponseEnum.SUCCESS, bucketService.save(id, rootDirName, filename, patch));
    }
}
