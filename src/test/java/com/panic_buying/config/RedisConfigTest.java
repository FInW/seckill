package com.panic_buying.config;

import com.alibaba.fastjson.JSON;
import com.panic_buying.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;


@SpringBootTest
class RedisConfigTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    @Qualifier("redisTemplate1")
    private RedisTemplate redis1Template;

    @Test
    public void testObj() {
        User user = new User(0, "test");
        redisTemplate.opsForValue().set("test", JSON.toJSONString(user));
        System.out.println(redisTemplate.opsForValue().get("test"));

        User user1 = new User(1, "test");
        redis1Template.opsForValue().set("test", JSON.toJSONString(user));
        System.out.println(redis1Template.opsForValue().get("test"));
    }
}