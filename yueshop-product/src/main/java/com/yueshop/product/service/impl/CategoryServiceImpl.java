package com.yueshop.product.service.impl;

import com.yueshop.product.service.CategoryBrandRelationService;
import org.apache.commons.lang.StringUtils;
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

import com.yueshop.product.dao.CategoryDao;
import com.yueshop.product.entity.CategoryEntity;
import com.yueshop.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        List<CategoryEntity> list = baseMapper.selectList(null);
        List<CategoryEntity> listMenu1 = list.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid() == 0)
                .map(category ->{
                    //嵌套获取每个分类中的子类
                    category.setChildren(getChildrens(category,list));
                    return category;
                })
                .sorted((menu1,menu2)->{
                    return (menu2.getSort()==null?0:menu2.getSort())-(menu1.getSort()==null?0:menu1.getSort());
                })
                .collect(Collectors.toList());
        return listMenu1;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public void updateDetialById(CategoryEntity category) {
        this.updateById(category);
        if(StringUtils.isNotEmpty(category.getName())){
            categoryBrandRelationService.updateCategoryName(category.getCatId(),category.getName());
        }
    }

    /*
    * 定义一个递归方法来获取分类中的子列表数据
    * */

    public List<CategoryEntity> getChildrens(CategoryEntity parent,List<CategoryEntity> all){
        List<CategoryEntity> children = all.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid() == parent.getCatId())
                .map(category ->{
                    //嵌套获取每个分类中的子类
                    category.setChildren(getChildrens(category,all));
                    return category;
                })
                .collect(Collectors.toList());
        return children;
    }

}