package com.seckill.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * Redis配置
 *
 * @author EMINEM
 */
@Slf4j
@Configuration
public class RedisConfig {

    /**
     * 配置lettuce连接池
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.redis.lettuce.pool")
    public GenericObjectPoolConfig redisPool() {
        return new GenericObjectPoolConfig<>();
    }

    /**
     * 配置数据源
     * @return
     */

    @Bean("redis")
    @Primary
    @ConfigurationProperties(prefix = "spring.redis")
    public RedisStandaloneConfiguration redisConfig() {
        return new RedisStandaloneConfiguration();
    }

    @Bean("redis1")
    @ConfigurationProperties(prefix = "spring.redis1")
    public RedisStandaloneConfiguration redisConfig1() {
        return new RedisStandaloneConfiguration();
    }

    /**
     * 配置连接工厂
     * @param poolConfig
     * @param redisConfig
     * @return
     */

    @Bean("factory")
    @Primary
    public LettuceConnectionFactory factory(GenericObjectPoolConfig poolConfig,@Qualifier("redis") RedisStandaloneConfiguration redisConfig) {
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(poolConfig).build();
        return new LettuceConnectionFactory(redisConfig, clientConfiguration);
    }

    @Bean("factory1")
    public LettuceConnectionFactory factory1(GenericObjectPoolConfig poolConfig,@Qualifier("redis1") RedisStandaloneConfiguration redisConfig) {
        LettuceClientConfiguration clientConfiguration = LettucePoolingClientConfiguration.builder().poolConfig(poolConfig).build();
        return new LettuceConnectionFactory(redisConfig, clientConfiguration);
    }


    /**
     * 默认redis0
     * @param connectionFactory
     * @return
     */
    @Bean
    @Primary
    public RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory connectionFactory) {
        return getRedisTemplate(connectionFactory);
    }

    @Bean("redisTemplate1")
    public RedisTemplate<String, Serializable> redisTemplate1(@Qualifier("factory1")LettuceConnectionFactory factory) {
        return getRedisTemplate(factory);
    }


    private RedisTemplate<String, Serializable> getRedisTemplate(LettuceConnectionFactory factory) {
        RedisTemplate template = new RedisTemplate();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(factory);
        return template;
    }

}
