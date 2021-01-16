package com.zy.spring.data.redis.single;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConditionalOnExpression("'${spring.profiles.active}'.equals('redis-single')")
@PropertySource(value = {"classpath:application-redis-single.yml"})
public class RedisSingletonConfiguration {

}
