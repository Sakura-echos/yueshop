package com.yueshop.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.yueshop.product.dao.BrandDao;
import com.yueshop.product.dao.CategoryDao;
import com.yueshop.product.entity.BrandEntity;
import com.yueshop.product.entity.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.common.utils.Query;

import com.yueshop.product.dao.CategoryBrandRelationDao;
import com.yueshop.product.entity.CategoryBrandRelationEntity;
import com.yueshop.product.service.CategoryBrandRelationService;




@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    CategoryDao categoryDao;
    @Autowired
    BrandDao brandDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        Long catelogId = categoryBrandRelation.getCatelogId();
        Long brandId = categoryBrandRelation.getBrandId();
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        BrandEntity brandEntity = brandDao.selectById(brandId);
        categoryBrandRelation.setBrandName(brandEntity.getName());
        categoryBrandRelation.setCatelogName(categoryEntity.getName());
        this.save(categoryBrandRelation);
    }

    @Override
    public Boolean queryCidAndBid(CategoryBrandRelationEntity categoryBrandRelation) {
        CategoryBrandRelationEntity one = this.getOne(new QueryWrapper<CategoryBrandRelationEntity>()
                .eq("catelog_id", categoryBrandRelation.getCatelogId())
                .eq("brand_id", categoryBrandRelation.getBrandId()));

        return one!=null;
    }

    @Override
    public void updateBrandName(Long brandId, String brandName) {
        CategoryBrandRelationEntity entity = new CategoryBrandRelationEntity();
        entity.setBrandId(brandId);
        entity.setBrandName(brandName);
        this.update(entity,new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id",brandId));
    }

    @Override
    public void updateCategoryName(Long catId, String name) {
        CategoryBrandRelationEntity entity = new CategoryBrandRelationEntity();
        entity.setCatelogName(name);
        this.update(entity,new UpdateWrapper<CategoryBrandRelationEntity>().eq("catelog_id",catId));
    }


}