package com.zy.spring.data.redis.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

@SpringBootApplication
public class SpringRawRedisApp {

    @Value("${spring.redis.lockKeyPrefix}")
    private String redisLockKeyPrefix;

    public static void main(String[] args) {
        SpringApplication.run(SpringRawRedisApp.class, args);
    }

    @Bean
    public RedisLockRegistry springRedisLockRegistry(RedisConnectionFactory connectionFactory) {
        return new RedisLockRegistry(connectionFactory, redisLockKeyPrefix);
    }
}
