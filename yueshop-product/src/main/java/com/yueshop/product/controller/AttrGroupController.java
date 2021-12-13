package com.yueshop.product.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yueshop.product.entity.AttrGroupEntity;
import com.yueshop.product.service.AttrGroupService;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.common.utils.R;



/**
 * 属性分组
 *
 * @author alen
 * @email alen@gmail.com
 * @date 2021-11-30 21:49:03
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    /**
     * 列表
     */
    @RequestMapping("/list/{categoryId}")
    public R list(@RequestParam Map<String, Object> params,
                  @PathVariable("categoryId")Long categoryId){
//        PageUtils page = attrGroupService.queryPage(params);

        PageUtils page = attrGroupService.queryPageByCategoryId(params,categoryId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);

        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
        //1.根据分组id查询属性分组
		attrGroupService.updateById(attrGroup);
		//2.获取三级分类id
        Long categoryId = attrGroup.getCatelogId();
        //3.根据三级分类id把父类的id也查询处理
        Long[] catelogPath = attrGroupService.getCatelogPath(categoryId);
        //4.把Long数组赋值到属性分组列表中
        attrGroup.setCatelogPath(catelogPath);
        return R.ok().put("attrGroup",attrGroup);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
