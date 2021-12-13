package com.yueshop.common.constant;

public enum BizCodeEnum {
    //定义枚举 
    UNKNOW_EXCEPTION(10000,"系统位置异常"),
    VALID_EXCEPTION(10001,"参数格式校验失败");
  
    private int code;
    private String msg;
  
        BizCodeEnum(int code,String msg){
        this.code=code;
        this.msg= msg;
    }
    //对外提供方法
        public int getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }
}
