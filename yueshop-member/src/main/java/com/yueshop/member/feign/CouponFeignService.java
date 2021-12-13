package com.yueshop.member.feign;

import com.yueshop.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

//这是一个声明式的远程调用接口，声明调用哪个服务模块
@FeignClient("yueshop-coupon")
public interface CouponFeignService {

    //声明远程调度用哪个方法    请求uri和coupon中的uri是一致的
    @RequestMapping("coupon/coupon/member/list")
    public R memberCoupons();

}
