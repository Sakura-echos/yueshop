package com.yueshop.product.service.impl;

import com.yueshop.product.service.CategoryBrandRelationService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


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
        //1、把所有分类数据都先查询
        List<CategoryEntity> lists = baseMapper.selectList(null);
        //2、通过集合中的stream()过滤器进行一级分类的过滤
        List<CategoryEntity> listMenu1 = lists.stream()
                //一级分类过滤获取：条件-->当前的分类的父id为0【一级分类】
                .filter(categoryEntity->categoryEntity.getParentCid()==0)//过滤逻辑为 父分类id为0
                //map方法理解为迭代器，给每一个一级分类获取二级分类
                .map(category->{
                    //嵌套获取每个分类中的子类  【把二级分类获取，在设置到一级分类中】
                    category.setChildren(getChildrens(category,lists));
                    return category;
                })
                //排序：根据sort字段来进行排序【1-2就是升序；2-1就是降序】
                .sorted((menu1,menu2)->{
                    //根据两个对象的sort的差值进行排序
                    return (menu2.getSort()==null?0:menu2.getSort())-(menu1.getSort()==null?0:menu1.getSort());
                })
                //过滤后的结果，归并为一个list集合
                .collect(Collectors.toList());//组装为list
        //返回结果
        return listMenu1;
    }

    //逻辑删除分类的实现方法
    @Override
    @Transactional
    public void removeMenuByIds(List<Long> asList) {
        baseMapper.deleteBatchIds(asList);
    }

    @Override
    @Transactional
    public void updateDetailById(CategoryEntity category) {
        //1、修改分类的信息
        this.updateById(category);
        //2、修改品牌分类中间信息表的分类名数据
        if(!StringUtils.isEmpty(category.getName())){
            categoryBrandRelationService.updateCategoryName(category.getCatId(),category.getName());
        }
    }

    /**
     * 定义一个递归方法来获取分类中的子分类列表数据
     * 入参一个父父类，全分类数据集
     * 思路：在所有的分类数据集中进行过滤，分类数据中的parentid和当前的root的id一致的
     */
    private List<CategoryEntity> getChildrens(CategoryEntity root,List<CategoryEntity> all){
        List<CategoryEntity> children = all.stream()
                //获取父分类中的子分类列表
                .filter(categoryEntity->categoryEntity.getParentCid()==root.getCatId())//分类数据中的parentid和当前的root的id一致的
                //遍历所有的二级分类，把二级分类的子分类列表获取并设置
                .map(category->{
                    //嵌套获取每个分类中的子类
                    category.setChildren(getChildrens(category,all));
                    return category;
                })
                //排序：根据sort字段来进行排序
                .sorted((menu1,menu2)->{
                    //根据两个对象的sort的差值进行排序
                    return (menu2.getSort()==null?0:menu2.getSort())-(menu1.getSort()==null?0:menu1.getSort());
                })
                .collect(Collectors.toList());//组装为list
        //返回结果
        return children;
    }

}