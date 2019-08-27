package cn.ztcaoll222.write.helper.sync.config;

import lombok.extern.flogger.Flogger;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ztcaoll222
 * Create time: 2019/8/27 17:23
 */
@Configuration
@Flogger
public class RedisConfig {
    private final RedissonClient client;

    public RedisConfig(RedissonClient client) {
        this.client = client;
    }

    @Bean(name = "syncBucket")
    public RMap<String, String> syncBucket() {
        log.atInfo().log("init sync:bucket");
        return client.getMap("sync:bucket");
    }
}
