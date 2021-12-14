package com.yueshop.product.entity;

import lombok.Data;

import java.util.List;

//当前类是用于封装分类查询属性分组及属性的vo
@Data
public class AttrGroupWithAttrsVo {
    //分组id
    private Long attrGroupId;
    //分组名
    private String attrGroupName;
    //排序
    private Integer sort;
    //描述
    private String descript;
    //图标
    private String icon;
    //所属分类id
    private Long catelogId;
    //分组属性
    private List<AttrEntity> attrs;

}
