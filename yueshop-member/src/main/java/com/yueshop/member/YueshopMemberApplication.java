package com.yueshop.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
//扫描远程调用接口的包
@EnableFeignClients(basePackages = "com.yueshop.member.feign")
public class YueshopMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(YueshopMemberApplication.class, args);
    }

}
