package com.yueshop.product.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.yueshop.common.valid.AddGroup;
import com.yueshop.common.valid.UpdateGroup;
import com.yueshop.common.valid.UpdateStatusGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yueshop.product.entity.BrandEntity;
import com.yueshop.product.service.BrandService;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.common.utils.R;

import javax.validation.Valid;


/**
 * 品牌
 *
 * @author Jerry
 * @email Jerrt@gmail.com
 * @date 2021-11-25 17:02:03
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@Validated({AddGroup.class}) @RequestBody BrandEntity brand){//, BindingResult result){
        /*//判断是否有校验错误
        if(result.hasErrors()){
            //创建一个map用于存放错误信息的键值对
            HashMap map = new HashMap();
            //获取，并遍历错误信息，保存到map中，进行返回
            result.getFieldErrors().forEach(item->{
                //获取字段名
                String fieldName = item.getField();
                //获取错误信息
                String message = item.getDefaultMessage();
                //保存数据到map中
                map.put(fieldName,message);
            });
            //返回数据
            return R.error(400,"提交的数据不合法").put("data",map);
        }else{
            //校验没有错误
            brandService.save(brand);
            return R.ok();
        }*/
        //校验没有错误
        brandService.save(brand);
        return R.ok();
    }

    /**
     * 修改品牌信息
     */
    @RequestMapping("/update")
    public R update(@Validated({UpdateGroup.class}) @RequestBody BrandEntity brand){
//		brandService.updateById(brand);
        brandService.updateDetailById(brand);
        return R.ok();
    }
    /**
     * 修改品牌状态
     */
    @RequestMapping("/update/status")
    public R updateStatus(@Validated({UpdateStatusGroup.class}) @RequestBody BrandEntity brand){
		brandService.updateById(brand);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
