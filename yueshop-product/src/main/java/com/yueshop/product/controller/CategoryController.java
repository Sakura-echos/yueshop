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
 * @author Jerry
 * @email Jerrt@gmail.com
 * @date 2021-11-25 17:02:03
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
    public R list(@RequestParam Map<String, Object> params) {
        //1 定义listWithTree方法进行三级分类的信息查询
        List<CategoryEntity> list = categoryService.listWithTree();
        //2 返回结果
        return R.ok().put("data", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId) {
        CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category) {
        categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category) {
//        categoryService.updateById(category);
        categoryService.updateDetailById(category);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] catIds) {
//        categoryService.removeByIds(Arrays.asList(catIds));
        categoryService.removeMenuByIds(Arrays.asList(catIds));
        return R.ok();
    }

}
