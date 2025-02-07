package com.juicew.juicepicbackend;

import org.apache.shardingsphere.spring.boot.ShardingSphereAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

//当前分库分表关闭
@SpringBootApplication(exclude = {ShardingSphereAutoConfiguration.class})
@EnableAsync
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.juicew.juicepicbackend.mapper")
public class JuicePicBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(JuicePicBackendApplication.class, args);
    }

}
