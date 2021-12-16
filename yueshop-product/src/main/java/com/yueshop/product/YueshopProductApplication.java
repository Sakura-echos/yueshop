package com.yueshop.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.yueshop.product.feign")
public class YueshopProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(YueshopProductApplication.class, args);
    }

}
