package com.seckill.utils;

import com.seckill.redis.BasePrefix;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisUtil {

    private StringRedisTemplate redisTemplate;

    public RedisUtil(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public String get(BasePrefix prefix, Object key) {
        return redisTemplate.opsForValue().get(prefix.getKey(key));
    }

    public String get(BasePrefix prefix) {
        return redisTemplate.opsForValue().get(prefix.getPrefix());
    }

    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(BasePrefix prefix, String value) {
        if (prefix.expireSeconds() != 0) {
            redisTemplate.opsForValue().set(prefix.getPrefix(), value, prefix.expireSeconds(), TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(prefix.getPrefix(), value);
        }
    }

    public void set(BasePrefix prefix, Object key, String value) {
        if (prefix.expireSeconds() != 0) {
            redisTemplate.opsForValue().set(prefix.getKey(key), value, prefix.expireSeconds(), TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(prefix.getKey(key), value);
        }
    }

    public Long decrement(BasePrefix prefix, Object key) {
        return redisTemplate.opsForValue().decrement(prefix.getKey(key));
    }

    public Long decrement(BasePrefix prefix, Object key, long delta) {
        return redisTemplate.opsForValue().decrement(prefix.getKey(key), delta);
    }

    public Boolean hasKey(BasePrefix prefix, Object key) {
        return redisTemplate.hasKey(prefix.getKey(key));
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

}
