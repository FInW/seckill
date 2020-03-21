package com.seckill.redis;

/**
 * redis key前缀接口
 * @author EMINEM
 */
public interface KeyPrefix {

    int expireSeconds();

    String getPrefix();

    String getPrefix(Object prefix);
}
