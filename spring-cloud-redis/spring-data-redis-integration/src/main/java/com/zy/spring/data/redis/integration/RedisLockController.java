package com.zy.spring.data.redis.integration;

import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@RestController
public class RedisLockController {

    @Resource
    private RedisLockRegistry springRedisLockRegistry;

    /**
     * spring 官方提供的分布式锁使用示例, 这里会有一个锁重试的时间, 而不是首次获取锁失败后立即放弃
     */
    @RequestMapping("lock")
    public void orderLock() {
        Lock lock = springRedisLockRegistry.obtain("my:key");
        boolean b = lock.tryLock();
        if (!b) {
            System.out.println("获取锁失败..");
            return;
        }
        try {
            System.out.println("获取锁成功, 执行业务逻辑");
            TimeUnit.MILLISECONDS.sleep(200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
