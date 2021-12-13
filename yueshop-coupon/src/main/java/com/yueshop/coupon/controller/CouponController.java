package com.yueshop.coupon.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yueshop.coupon.entity.CouponEntity;
import com.yueshop.coupon.service.CouponService;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.common.utils.R;



/**
 * 优惠券信息
 *
 * @author alen
 * @email alen@gmail.com
 * @date 2021-11-30 22:12:12
 */
@RestController
@RequestMapping("coupon/coupon")
@RefreshScope   //能够进行动态刷新
public class CouponController {
    @Autowired
    private CouponService couponService;

    @Value("${coupon.user.name}")
    private String name;
    @Value("${coupon.user.age}")
    private int age;

    //测试
    @RequestMapping("/test/config")
    public R test(){
        return R.ok().put("name", name).put("age",age);
    }


    //在优惠模块中定义一个请求方法来查询优惠券
    @RequestMapping("/member/list")
    public R memberCoupon(){
        //创建一个优惠卷对象
        CouponEntity entity = new CouponEntity();
        entity.setCouponName("满100减30");
        //以集合的形式返回

        return R.ok().put("page", Arrays.asList(entity));
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = couponService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		CouponEntity coupon = couponService.getById(id);

        return R.ok().put("coupon", coupon);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CouponEntity coupon){
		couponService.save(coupon);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CouponEntity coupon){
		couponService.updateById(coupon);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		couponService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
