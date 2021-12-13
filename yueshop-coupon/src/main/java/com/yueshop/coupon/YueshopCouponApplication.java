package com.yueshop.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/*
* 1.在项目中加入openfeign依赖
* 2.编写一个接口，告诉spring cloud中可以使用哪个接口服务
*           声明接口哪个方法可以进行调用
* 3.开启园春调用的功能
* */
@EnableDiscoveryClient
@SpringBootApplication
public class YueshopCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(YueshopCouponApplication.class, args);
    }

}
