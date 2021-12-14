package com.yueshop.product.entity;

import lombok.Data;

//用于返回视图的属性的数据模型VO
@Data
public class AttrRespVo extends AttrVo {
    //分类名--分组名
    private String catelogName;
    private String groupName;
    //三级分类的路径 long的数组
    private Long[] catelogPath;
}
