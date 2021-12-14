package com.yueshop.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;

//自定义了一个ListValue的校验器    第一个泛型为使用的校验注解类型  第二个泛型为校验的数据类型
public class ListValueConstraintor implements ConstraintValidator<ListValue,Integer> {
    //实现逻辑：在一个散列表中保存允许通过的数值，在校验方法中判断现在的属性值是否存在散列表
    private HashSet set = new HashSet();

    //在初始化方法中初始化数值
    @Override
    public void initialize(ListValue constraintAnnotation) {
        //把允许通过的数值保存在散列表中
        for (int item:constraintAnnotation.values()) {
            set.add(item);
        }
    }

    //在校验方法中进行数值的校验
    @Override
    public boolean isValid(Integer num, ConstraintValidatorContext constraintValidatorContext) {
        return set.contains(num);
    }
}
