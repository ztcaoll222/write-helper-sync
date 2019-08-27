package cn.ztcaoll222.write.helper.sync.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "cn.ztcaoll222.write.helper.sync")
public class WriteHelperSyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(WriteHelperSyncApplication.class, args);
    }

}
