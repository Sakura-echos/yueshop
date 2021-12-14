package com.yueshop.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yueshop.product.entity.BrandEntity;
import com.yueshop.product.entity.BrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yueshop.product.entity.CategoryBrandRelationEntity;
import com.yueshop.product.service.CategoryBrandRelationService;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.common.utils.R;


/**
 * 品牌分类关联
 *
 * @author Jerry
 * @email Jerrt@gmail.com
 * @date 2021-11-25 17:02:03
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;


    /**
     * 根据分类id查询关联的品牌数据
     * /product/categorybrandrelation/brands/list?t=1639121272033&catId=225
     */
    @RequestMapping("/brands/list")
    public R relationBrandList(@RequestParam("catId") Long catId) {
        //1\调用业务层方法查询数据  品牌实体类的集合
        List<BrandEntity> brandEntityList = categoryBrandRelationService.getBrandsByCatId(catId);
        //2\品牌实体类的集合   --- 》 封装为BrandVo的集合
        List<BrandVo> vosList =brandEntityList.stream().map((item)->{
            //把item中的数据赋值到vo
            BrandVo vo = new BrandVo();
            vo.setBrandId(item.getBrandId());
            vo.setBrandName(item.getName());
            //返回对象
            return vo;
        }).collect(Collectors.toList());
        //3\返回
        return R.ok().put("data", vosList);
    }

    /**
     * 根据品牌id查询关联的分类列表  /product/categorybrandrelation/catelog/list
     */
    @RequestMapping("/catelog/list")
    public R list(@RequestParam("brandId") Long brandId) {
        //定义条件构造器，设定条件  根据品牌id查询关联的CategoryBrandRelationEntity列表
        QueryWrapper wrapper = new QueryWrapper<>().eq("brand_id", brandId);
        //查询
        List<CategoryBrandRelationEntity> data = categoryBrandRelationService.list(wrapper);
        //返回
        return R.ok().put("data", data);
    }

    /**
     * 品牌列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation) {
//		categoryBrandRelationService.save(categoryBrandRelation);
        //判断当前品牌中是否有该分类，如果有则不添加 //true表示拥有该分类，不添加
        if (!categoryBrandRelationService.queryByCidAndBid(categoryBrandRelation)){
            //没有则添加
            categoryBrandRelationService.saveDetail(categoryBrandRelation);
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation) {
        categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
