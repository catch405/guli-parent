package com.atguigu.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu"})
@EnableDiscoveryClient //Nacos注册中心注解
@EnableFeignClients  //Feign服务调用者注解
public class EduApplication {

    public static void main(String[] args) {

        SpringApplication.run(EduApplication.class, args);
    }
}
