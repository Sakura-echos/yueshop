package com.yueshop.member.controller;

import java.util.Arrays;
import java.util.Map;

import com.yueshop.member.feign.CouponFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yueshop.member.entity.MemberEntity;
import com.yueshop.member.service.MemberService;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.common.utils.R;



/**
 * 会员
 *
 * @author alen
 * @email alen@gmail.com
 * @date 2021-11-30 22:14:01
 */
@RestController
@RequestMapping("member/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private CouponFeignService couponFeignService;
    /*
     * 定义一个方法来测试是否能够通过远程调度来获取优惠卷数据
     * */
    @RequestMapping("/coupons")
    public R test(){
        //模拟一个会员拿到优惠卷
        //创建一个会员
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("张三");
        //远程调用查询优惠卷接口
        R coupons = couponFeignService.memberCoupons();
        return R.ok().put("memberEntity", memberEntity).put("coupons",coupons);
    }





    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
