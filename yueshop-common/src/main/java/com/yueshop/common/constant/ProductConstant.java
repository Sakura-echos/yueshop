package com.yueshop.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

//商品模块的常量类
public class ProductConstant {

    //属性--类型【基本属性--销售属性】枚举类型
    @Getter
    @AllArgsConstructor
    public enum AttrEnum{
        ATTR_BASE_TYPE(1,"规格参数"),ATTR_SALE_TYPE(0,"销售属性");
        private int code;
        private String message;
    }

}
