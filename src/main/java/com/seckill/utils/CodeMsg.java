package com.seckill.utils;

public enum CodeMsg {
    SUCCESS(0, "success"),
    SERVER_ERROR(500100, "服务端异常"),
    BIND_ERROR(500101, "参数校验异常: %s"),


    //登录模块 5002XX
    SESSION_ERROR(500210, "Session不存在"),
    PASSWORD_EMPTY(500211, "登录密码不能为空"),
    MOBILE_EMPTY(500212, "手机号不能为空"),
    MOBILE_ERROR(500213, "手机号格式错误"),
    MOBILE_NOT_EXIST(500214, "手机号不存在"),
    PASSWORD_ERROR(500215, "密码错误"),
    //商品模块 5003XX

    //订单模块 5004XX

    //秒杀模块 5005XX
    MIAO_SHA_OVER(500500, "商品已经秒杀结束"),
    REPEATE_MIAOSHA(500501, "不能重复秒杀");

    private int code;
    private String msg;

    CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }

    public CodeMsg fillArgs(Object... args) {
        this.msg = String.format(this.msg, args);
        return this;
    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }
}
