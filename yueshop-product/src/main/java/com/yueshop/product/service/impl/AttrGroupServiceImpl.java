package com.yueshop.product.service.impl;

import com.yueshop.product.entity.AttrEntity;
import com.yueshop.product.entity.AttrGroupWithAttrsVo;
import com.yueshop.product.entity.CategoryEntity;
import com.yueshop.product.service.AttrService;
import com.yueshop.product.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 列表:根据分类的id查询属性分组信息
     */
    @Override
    public PageUtils queryPageByCategory(Map<String, Object> params, Long categoryId) {

        IPage<AttrGroupEntity> page = null;
        System.out.println(" String key = "+(String) params.get("key"));
        //1、分类id为空或者为0 --查询所有分组
        if (categoryId == null || categoryId == 0) {
            //查询条件构造器  -- 有查询条件(可以是id，也可以是组名的模糊查询)
            QueryWrapper wrapper = new QueryWrapper<AttrGroupEntity>();
            //不为空则添加查询条件
            String key = (String) params.get("key");
            if (key!=null&&!key.equals("")) {
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

    //根据三级分类id把父分类的id也查询处理，并且组装成一个long数组【2,6,165】
    @Override
    public Long[] getCatelogPath(Long catelogId) {
        //1、定义一个list集合用于存放long类型的id【路径】
        List<Long> paths = new ArrayList<>();
        //2、调用递归获取方法，获取到long类型【路径】   逻辑：根据三级分类id查询到的数据赋值到paths中
        List<Long> parentPath = findParentPath(catelogId,paths);
        //3、获取到数据，检查顺序 [获取到的顺序是反序，所以要反转]//路径为：[342, 43, 4]
        Collections.reverse(parentPath);
        //4、返回数组
        return parentPath.toArray(new Long[parentPath.size()]);//[将当前list转换为数组]
    }


    //根据分类id查询属性分组和属性数据
    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrs(Long catelogId) {
        //1、查询出当前分类下的所有属性分组
        List<AttrGroupEntity>  groupEntityList= this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id",catelogId));
        //2、属性分组下的所有属性查询出来
        List<AttrGroupWithAttrsVo> voList = groupEntityList.stream().map((group)->{
            //把属性数据拷贝到AttrGroupWithAttrsVo
            AttrGroupWithAttrsVo vo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(group,vo);
            //把属性数据查询出来，然后设置到AttrGroupWithAttrsVo
            List<AttrEntity>  attrEntityList = attrService.getRelationAttr(group.getAttrGroupId());
            vo.setAttrs(attrEntityList);
            //返回
            return vo;
        }).collect(Collectors.toList());
        //返回voList集合
        return voList;
    }


    //递归方法   获取long类型【路径】
    private List<Long> findParentPath(Long catelogId, List<Long> paths) {
        //1、将当前的三级分类id添加到 paths中
        paths.add(catelogId);
        //2、根据当前的三级分类id获取它的父分类id parent_cid
        CategoryEntity parent_category = categoryService.getById(catelogId);
        //3、判断是否还有父分类？    parent_cid==0
        if(parent_category.getParentCid()!=0){//表示还有父分类
            findParentPath(parent_category.getParentCid(), paths);//递归调用
        }
        //出口，返回数据
        return paths;
    }

}