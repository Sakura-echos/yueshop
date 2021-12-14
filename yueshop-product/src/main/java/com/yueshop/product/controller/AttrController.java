package com.yueshop.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.yueshop.product.entity.AttrRespVo;
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
 * @author Jerry
 * @email Jerrt@gmail.com
 * @date 2021-11-25 17:02:03
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    //请求uri为   ：  product/attr/base/list/0?t=1638955398472&page=1&limit=10&key=
    @RequestMapping("{atrrType}/list/{categoryId}")
    public R attrList(@RequestParam Map<String, Object> params,
                      @PathVariable("atrrType") String type,
                      @PathVariable("categoryId") Long categoryId) {
//        PageUtils page = attrService.queryPage(params);
        PageUtils page = attrService.queryBaseAttrPage(params,type,categoryId);
        return R.ok().put("page", page);
    }

    @RequestMapping("/list")
    public R attrList(@RequestParam Map<String, Object> params) {
        PageUtils page = attrService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    public R info(@PathVariable("attrId") Long attrId) {
//        AttrEntity attr = attrService.getById(attrId);
        AttrRespVo attr = attrService.getAttrinfoById(attrId);

        return R.ok().put("attr", attr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrVo attr) {
//		attrService.save(attr);
        attrService.addAttrVo(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrRespVo attr) {
//        attrService.updateById(attr);
        attrService.updateAttrById(attr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrIds) {
        attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
