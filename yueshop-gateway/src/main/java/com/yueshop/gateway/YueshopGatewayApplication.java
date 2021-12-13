package com.yueshop.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
//exclude不需要加载某个自动装配
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class YueshopGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(YueshopGatewayApplication.class, args);
    }

}
