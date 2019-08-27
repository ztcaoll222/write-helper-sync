package cn.ztcaoll222.write.helper.sync.core.id;

import cn.ztcaoll222.write.helper.sync.interfaces.id.IdService;
import lombok.extern.flogger.Flogger;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author ztcaoll222
 * Create time: 2019/8/27 18:34
 */
@Service(value = "idService")
@Flogger
public class IdServiceImpl implements IdService {
    @Override
    public String genId() {
        return UUID.randomUUID().toString();
    }
}
