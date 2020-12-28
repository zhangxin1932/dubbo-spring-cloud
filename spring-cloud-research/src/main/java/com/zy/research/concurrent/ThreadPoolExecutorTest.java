package com.zy.research.concurrent;

import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {

    private static final Map<Long, Long> MAP = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        for (Long i = 1L; i < 100L; i++) {
            MAP.put(i, i);
        }

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1),
                new DefaultThreadFactory("my-threadFactory"),
                new ThreadPoolExecutor.CallerRunsPolicy());

        for (long i = 1L; i < 100; i++) {
            Long finalI = i;
            threadPoolExecutor.submit(() -> {
                System.out.println(MAP.get(finalI));
                MAP.remove(finalI);
            });
        }
    }

    private static void release(Long id) {
        MAP.put(id, 0L);
    }

}
