package com.seckill.redis;

/**
 * redis key前缀 抽象类 模板模式
 * @author EMINEM
 */
public abstract class BasePrefix implements KeyPrefix{

    private int expireSeconds;

    private String prefix;

    public BasePrefix(String prefix) {
        this(0, prefix);
    }

    public BasePrefix(int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix + ":";
    }

    @Override
    public String getKey(Object prefix) {
        String className = getClass().getSimpleName();
        return className + ":" + this.prefix + ":" + prefix;
    }
}
