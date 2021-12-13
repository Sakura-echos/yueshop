package com.yueshop.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yueshop.product.entity.CategoryEntity;
import com.yueshop.product.service.CategoryService;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.common.utils.R;



/**
 * 商品三级分类
 *
 * @author alen
 * @email alen@gmail.com
 * @date 2021-11-30 21:49:03
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    /**
     * 列表
     */
    @RequestMapping("/list/tree")
    public R list(@RequestParam Map<String, Object> params){
        //1.查询所有分类数据
        //2.从分类数据中组成父子结构
        List<CategoryEntity> list = categoryService.listWithTree();
        return R.ok().put("data", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category){
//        categoryService.updateById(category);
        categoryService.updateDetialById(category);
        return R.ok();
    }

    /**
     * 逻辑删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] catIds){
        categoryService.removeMenuByIds(Arrays.asList(catIds));
        return R.ok();
    }

}
