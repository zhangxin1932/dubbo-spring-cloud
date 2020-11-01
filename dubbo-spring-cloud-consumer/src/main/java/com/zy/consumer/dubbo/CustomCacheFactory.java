package com.zy.consumer.dubbo;

import org.apache.dubbo.cache.Cache;
import org.apache.dubbo.cache.support.AbstractCacheFactory;
import org.apache.dubbo.common.URL;

public class CustomCacheFactory extends AbstractCacheFactory {
    @Override
    protected Cache createCache(URL url) {
        return new CustomCache(url);
    }
}
