package com.zy.research.config.eventbus;

import com.google.common.eventbus.AsyncEventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 事件注入中心
 */
@Configuration
public class AsynEventBusConfig {

    @Bean
    @Scope("singleton")
    public AsyncEventBus asyncEventBus() {
        final ThreadPoolTaskExecutor executor = executor();
        return new AsyncEventBus(executor);
    }

    @Bean
    public ThreadPoolTaskExecutor executor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(1000);
        return executor;
    }
}