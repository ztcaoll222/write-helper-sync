package cn.ztcaoll222.write.helper.sync.core.id;

import cn.ztcaoll222.write.helper.sync.interfaces.id.IdService;
import lombok.extern.flogger.Flogger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ztcaoll222
 * Create time: 2019/8/27 18:31
 */
@RestController
@RequestMapping("/id")
@Flogger
public class IdController {
    @Resource(name = "idService")
    private IdService idService;

    @GetMapping("/genId")
    public String genId() {
        String id = idService.genId();
        log.atInfo().log("genId: %s", id);
        return id;
    }
}
