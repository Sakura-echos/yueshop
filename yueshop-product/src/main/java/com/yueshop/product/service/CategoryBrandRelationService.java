package com.yueshop.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.product.entity.BrandEntity;
import com.yueshop.product.entity.CategoryBrandRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author Jerry
 * @email Jerrt@gmail.com
 * @date 2021-11-25 17:02:03
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);

    boolean queryByCidAndBid(CategoryBrandRelationEntity categoryBrandRelation);

    void updateBrandName(Long brandId,String brandName);

    void updateCategoryName(Long categoryId,String name);

    List<BrandEntity> getBrandsByCatId(Long catId);
}

