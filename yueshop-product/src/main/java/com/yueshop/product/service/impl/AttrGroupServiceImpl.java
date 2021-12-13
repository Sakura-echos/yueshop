package com.yueshop.product.service.impl;

import com.yueshop.product.entity.CategoryEntity;
import com.yueshop.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yueshop.common.utils.PageUtils;
import com.yueshop.common.utils.Query;

import com.yueshop.product.dao.AttrGroupDao;
import com.yueshop.product.entity.AttrGroupEntity;
import com.yueshop.product.service.AttrGroupService;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Autowired
    CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByCategoryId(Map<String, Object> params, Long categoryId) {
        IPage<AttrGroupEntity> page = null;
        //1、分类id为空或者为0 --查询所有分组
        if (categoryId == null || categoryId == 0) {
            //查询条件构造器  -- 有查询条件(可以是id，也可以是组名的模糊查询)
            QueryWrapper wrapper = new QueryWrapper<AttrGroupEntity>();
            //不为空则添加查询条件
            String key = (String) params.get("key");
            if (key != null && !key.equals("")) {
                wrapper.eq("attr_group_id", key);//id查询条件
                wrapper.or();
                wrapper.like("attr_group_name", key);
            }
            page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    wrapper
            );
        } else {
            //2、分类id不为空 --根据分类查询分组
            page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    new QueryWrapper<AttrGroupEntity>()
                            .eq("catelog_id", categoryId)//分类id
            );
        }
        //返回结果
        return new PageUtils(page);
    }

    //根据三级分类id把分类的id也查询处理，并组成Long数组
    @Override
    public Long[] getCatelogPath(Long categoryId) {
        //1.定义一个list集合用于存放long类型id
        List<Long> paths = new ArrayList<>();
        //2.调用递归方法
        List<Long> Path = findParentPath(categoryId,paths);
        //3.获取数据，调整顺序
        Collections.reverse(Path);
        return Path.toArray(new Long[Path.size()]);
    }

    private List<Long> findParentPath(Long categoryId, List<Long> paths) {
        //1.将当前的三级分类id加入paths
        paths.add(categoryId);
        //2.根据当前id来查找父id
        CategoryEntity parent_category = categoryService.getById(categoryId);
        //3.判断是否有父分类
        if(parent_category.getParentCid()!=0){
            findParentPath(parent_category.getParentCid(),paths);
        } 
        return paths;
    }

}