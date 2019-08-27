package cn.ztcaoll222.write.helper.sync.core.bucket;

import cn.ztcaoll222.write.helper.sync.common.ObjectUtil;
import cn.ztcaoll222.write.helper.sync.interfaces.bucket.BucketService;
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
    public boolean sync(String id, String path, String text) {
        log.atInfo().log("id: %s, path: %s", id, path);
        if (!ObjectUtil.checkObj(id, path)) {
            return false;
        }
        return bucketService.save(id, path, text);
    }
}
