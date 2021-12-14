package com.yueshop.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yueshop.product.dao.BrandDao;
import com.yueshop.product.dao.CategoryDao;
import com.yueshop.product.entity.BrandEntity;
import com.yueshop.product.entity.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.common.utils.Query;

import com.yueshop.product.dao.CategoryBrandRelationDao;
import com.yueshop.product.entity.CategoryBrandRelationEntity;
import com.yueshop.product.service.CategoryBrandRelationService;
import org.springframework.transaction.annotation.Transactional;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    //获取CategoryDao、BrandDao
    @Autowired
    BrandDao brandDao;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    CategoryBrandRelationDao relationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        //获取三级分类id--品牌id
        Long categoryId = categoryBrandRelation.getCatelogId();
        Long brandId = categoryBrandRelation.getBrandId();
        //根据三级分类id查询三级分类获取分类名称
        //根据品牌id查询三级分类获取品牌名称
        CategoryEntity categoryEntity = categoryDao.selectById(categoryId);
        BrandEntity brandEntity = brandDao.selectById(brandId);
        //设置到categoryBrandRelation
        categoryBrandRelation.setCatelogName(categoryEntity.getName());
        categoryBrandRelation.setBrandName(brandEntity.getName());
        //进行持久化数据库
        this.save(categoryBrandRelation);
    }

    //用于查询该品牌中是否拥有这个分类
    @Override
    public boolean queryByCidAndBid(CategoryBrandRelationEntity categoryBrandRelation) {
        //根据分类id和品牌id进行查询
        CategoryBrandRelationEntity object = this.getOne(new QueryWrapper<CategoryBrandRelationEntity>()
                .eq("catelog_id", categoryBrandRelation.getCatelogId())
                .eq("brand_id", categoryBrandRelation.getBrandId()));
        return object != null;//拥有
    }

    @Override
    @Transactional
    public void updateBrandName(Long brandId, String brandName) {
        //1、创建一个中间信息对象
        CategoryBrandRelationEntity relation = new CategoryBrandRelationEntity();
        //注入数据
        relation.setBrandName(brandName);
        //2、执行修改操作
        this.update(relation, new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId));
    }

    @Override
    @Transactional
    public void updateCategoryName(Long categoryId, String name) {
        //创建对象
        CategoryBrandRelationEntity entity = new CategoryBrandRelationEntity();
        //赋值
        entity.setCatelogName(name);
        //执行修改
        this.update(entity, new UpdateWrapper<CategoryBrandRelationEntity>().eq("catelog_id", categoryId));
    }

    //根据分类id查询关联的品牌数据
    @Override
    public List<BrandEntity> getBrandsByCatId(Long catId) {
        //1、从分类-品牌中间信息表中查询品牌id
        List<CategoryBrandRelationEntity> relationEntitiesList = relationDao.selectList(new QueryWrapper<CategoryBrandRelationEntity>()
                .eq("catelog_id", catId));
        //2、通过品牌id所有的品牌数据
        List<BrandEntity> brandEntityList = relationEntitiesList.stream().map((item)->{
            //把品牌id获取出来
            Long bid = item.getBrandId();
            //根据id查询品牌对象
            BrandEntity entity = brandDao.selectById(bid);
            //返回品牌对象
            return entity;
        }).collect(Collectors.toList());
        //3、返回集合
        return brandEntityList;
    }

}