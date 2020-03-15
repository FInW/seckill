package com.panic_buying.redis;

/**
 * redis key前缀接口
 * @author EMINEM
 */
public interface KeyPrefix {

    int expireSeconds();

    String getPrefix();
}
