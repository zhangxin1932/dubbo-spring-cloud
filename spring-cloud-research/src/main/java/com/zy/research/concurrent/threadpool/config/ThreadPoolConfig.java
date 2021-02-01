package com.zy.research.concurrent.threadpool.config;

import com.zy.research.concurrent.threadpool.common.PoolName;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * https://www.cnblogs.com/warehouse/p/10732965.html
 * https://tech.meituan.com/2020/04/02/java-pooling-pratice-in-meituan.html
 */
@Configuration
public class ThreadPoolConfig {

    private static final int PROCESSORS = Runtime.getRuntime().availableProcessors();
    public static final ConcurrentMap<String, ThreadPoolExecutor> EXECUTOR_MAP = new ConcurrentHashMap<>();

    @Bean(name = "refundThreadPoolExecutor")
    public BlockedAndFixedThreadPoolExecutor refundThreadPoolExecutor() {
        return new BlockedAndFixedThreadPoolExecutor(PROCESSORS, 10, "refund");
    }

    @Bean(name = "stuThreadPoolExecutor")
    public ThreadPoolExecutor stuThreadPoolExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,
                2,
                0L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new DefaultThreadFactory(PoolName.stu_executor.name()),
                new ThreadPoolExecutor.AbortPolicy());
        EXECUTOR_MAP.put(PoolName.stu_executor.name(), executor);
        return executor;
    }

    @Bean(name = "teacherThreadPoolExecutor")
    public ThreadPoolExecutor teacherThreadPoolExecutor() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(PROCESSORS * 2,
                PROCESSORS * 2,
                0L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1024),
                new DefaultThreadFactory(PoolName.teacher_executor.name()),
                new ThreadPoolExecutor.CallerRunsPolicy());
        EXECUTOR_MAP.put(PoolName.teacher_executor.name(), executor);
        return executor;
    }
}
