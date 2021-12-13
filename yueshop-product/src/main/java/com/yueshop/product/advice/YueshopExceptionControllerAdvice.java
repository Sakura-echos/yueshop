package com.yueshop.product.advice;

import com.yueshop.common.constant.BizCodeEnum;
import com.yueshop.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

//统一异常处理类
@Slf4j
@RestControllerAdvice("com.yueshop.product.controller")
public class YueshopExceptionControllerAdvice {

    //指定该异常类来处理
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    //定义异常处理方法
    public R handlerValidException(MethodArgumentNotValidException e){
        log.error("数据校验的问题{},异常类型为{}",e.getMessage(),e.getClass());
        Map<String,String> map=new HashMap<>();
        //1.获取错误的校验结果
        e.getBindingResult().getFieldErrors().forEach((item)-> {
            //获取发生错误时的message
            String message = item.getDefaultMessage();
            //获取发生错误的字段
            String field = item.getField();
            map.put(field, message);
        });
        return R.error(BizCodeEnum.VALID_EXCEPTION.getCode(), BizCodeEnum.VALID_EXCEPTION.getMsg()).put("data",map);
    }

    //定义一个其他异常的处理方法
    @ExceptionHandler(value = Throwable.class)
    public R handleVaildException(Throwable e){
        //返回数据
        return R.error(BizCodeEnum.VALID_EXCEPTION.getCode(),BizCodeEnum.VALID_EXCEPTION.getMsg());
    }
}
