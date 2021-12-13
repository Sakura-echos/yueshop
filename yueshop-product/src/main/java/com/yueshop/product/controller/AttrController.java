package com.yueshop.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.yueshop.product.entity.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yueshop.product.entity.AttrEntity;
import com.yueshop.product.service.AttrService;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.common.utils.R;



/**
 * 商品属性
 *
 * @author alen
 * @email alen@gmail.com
 * @date 2021-11-30 21:49:03
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable("attrType")String type,
                  @PathVariable("categoryId")Long categoryId){
        PageUtils page = attrService.queryBaseAttrPage(params,type,categoryId);
        return R.ok().put("page", page);
    }

//    /**
//     * 列表
//     */
//    @RequestMapping("/list")
//    public R list(@RequestParam Map<String, Object> params){
//        PageUtils page = attrService.queryPage(params);
//
//        return R.ok().put("page", page);
//    }



    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId){
		AttrEntity attr = attrService.getById(attrId);

        return R.ok().put("attr", attr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrVo attr){
//        attrService.save(attr);
        attrService.saveAttrVo(attr);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrEntity attr){
		attrService.updateById(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
