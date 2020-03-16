package com.seckill.redis;

public class PanicBuyingUserKey extends BasePrefix{
    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;
    private PanicBuyingUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static PanicBuyingUserKey token = new PanicBuyingUserKey(TOKEN_EXPIRE, "tk");

}
