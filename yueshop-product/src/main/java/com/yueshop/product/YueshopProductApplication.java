package com.yueshop.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class YueshopProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(YueshopProductApplication.class, args);
    }

}
