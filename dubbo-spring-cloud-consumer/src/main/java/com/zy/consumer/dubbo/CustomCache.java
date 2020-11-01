package com.zy.consumer.dubbo;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.apache.dubbo.cache.Cache;
import org.apache.dubbo.common.URL;

import java.util.concurrent.TimeUnit;

public class CustomCache implements Cache {

    private final com.github.benmanes.caffeine.cache.Cache<Object, Object> cache;

    // private final com.google.common.cache.Cache<Object, Object> cache;

    public CustomCache(URL url) {
        this.cache = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .build();
        System.out.println(">>>>>>>>>>>>>>>>");
        System.out.println("初始化了dubbo缓存...");
        System.out.println(">>>>>>>>>>>>>>>>");
    }

    @Override
    public void put(Object key, Object value) {
        cache.put(key, value);
        System.out.println("--------------------------");
        System.out.println("向dubbo 中加入缓存");
        System.out.println("--------------------------");
    }

    @Override
    public Object get(Object key) {
        return cache.getIfPresent(key);
    }

}
