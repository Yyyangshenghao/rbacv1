package cn.wolfcode.rbac;

import org.mybatis.spring.annotation.MapperScan;
import org.opencv.core.Core;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling // 开启定时任务支持
@MapperScan("cn.wolfcode.rbac.mapper")
public class RbacApplication {
    public static void main(String[] args) {
        SpringApplication.run(RbacApplication.class, args);
    }
}
