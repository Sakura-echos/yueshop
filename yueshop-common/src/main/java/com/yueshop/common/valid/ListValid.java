package com.yueshop.common.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {ListValueConstraintor.class})
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RUNTIME)
public @interface ListValid {
    //    message()校验出现错误信息去哪取 ,使用这个属性在配置文件中获取
    String message() default "{com.yueshop.common.valid.ListValue.message}";
    //    groups() 支持分组校验功能
    Class<?>[] groups() default { };
    //    payload() 还可以自定义负载信息
    Class<? extends Payload>[] payload() default { };
    //定义一个数组存储范围内的值
    int[] vals()  default { };
}
