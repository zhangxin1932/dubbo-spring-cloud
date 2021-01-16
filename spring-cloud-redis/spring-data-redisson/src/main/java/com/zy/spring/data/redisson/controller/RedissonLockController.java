package com.zy.spring.data.redisson.controller;

import com.zy.spring.data.redisson.common.Constants;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/redissonLock/")
public class RedissonLockController {

    @Resource
    private RedissonClient client;

    private int count = 20;

    @RequestMapping("secKill")
    public ResponseEntity<Object> secKill(String goodsId) {
        RLock lock = client.getLock(String.format(Constants.LOCK_PREFIX, goodsId));
        boolean b = lock.tryLock();
        String msg = "扣减失败";
        if (b) {
            try {
                int count = getCount();
                if (count > 0) {
                    decreaseCount();
                    msg = "扣减成功";
                } else {
                    msg = "库存不足";
                }
            } finally {
                lock.unlock();
            }
        }
        return ResponseEntity.ok(msg);
    }

    /**
     * 模拟从 DB 中查询出来的库存数据
     */
    private int getCount() {
        return count;
    }

    private void decreaseCount() {
        this.count--;
    }
}
