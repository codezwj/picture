package com.juicew.juicepicbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.juicew.juicepicbackend.mapper")
public class JuicePicBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(JuicePicBackendApplication.class, args);
    }

}
