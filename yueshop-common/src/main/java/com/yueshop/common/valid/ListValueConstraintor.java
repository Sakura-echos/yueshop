package com.yueshop.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

public class ListValueConstraintor implements ConstraintValidator<ListValid,Integer> {
    private Set<Integer> set = new HashSet<>();
    //初始化方法   将属性 详细信息给我们
    @Override
    public void initialize(ListValid constraintAnnotation) {
        for(int item:constraintAnnotation.vals()){
            set.add(item);
        }
    }
    //判断是否校验成功   返回true校验通过  返回false 校验不通过
    @Override
    public boolean isValid(Integer num, ConstraintValidatorContext context) {
        return set.contains(num);
    }
}
