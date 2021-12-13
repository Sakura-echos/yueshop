package com.yueshop.thirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class YueshopThirdPartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(YueshopThirdPartyApplication.class, args);
    }

}
