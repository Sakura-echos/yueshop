package com.yueshop.product.advice;

import com.yueshop.common.constant.BizCodeEnum;
import com.yueshop.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

//统一处理异常的处理类   扫描controller的异常来进行统一处理
@Slf4j
@RestControllerAdvice("com.yueshop.product.controller")
public class YueshopExceptionControllerAdvice {

    //定义异常的处理   :  指定异常类型进行处理MethodArgumentNotValidException
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleVaildException(MethodArgumentNotValidException e){
        log.error("数据校验的问题{}，异常类型为{}",e.getMessage(),e.getClass());
        //创建一个map用于存放错误信息的键值对
        HashMap map = new HashMap();
        //获取，并遍历错误信息，保存到map中，进行返回
        e.getBindingResult().getFieldErrors().forEach(item->{
            //获取字段名
            String fieldName = item.getField();
            //获取错误信息
            String message = item.getDefaultMessage();
            //保存数据到map中
            map.put(fieldName,message);
        });
        //返回数据
        return R.error(BizCodeEnum.VALID_EXCEPTION.getCode(),BizCodeEnum.VALID_EXCEPTION.getMessage()).put("data",map);
    }

    /*//定义一个其他异常的处理方法
    @ExceptionHandler(value = Throwable.class)
    public R handleVaildException(Throwable e){
        //返回数据
        return R
                .error(BizCodeEnum.UNKNOWN_EXCEPTION.getCode(), BizCodeEnum.UNKNOWN_EXCEPTION.getMessage())
                .put("message",e.getStackTrace());
    }*/
}
