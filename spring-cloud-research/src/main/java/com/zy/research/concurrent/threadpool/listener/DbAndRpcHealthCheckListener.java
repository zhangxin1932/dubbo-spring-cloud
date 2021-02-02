package com.zy.research.concurrent.threadpool.listener;

import com.zy.research.concurrent.threadpool.config.BlockedAndFixedThreadPoolExecutor;
import com.zy.research.concurrent.threadpool.config.ThreadPoolConfig;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 应保证确定应用启动后修改此状态. 比如初始化后延迟 10min 后调度.
 * 避免阻塞应用的启动过程
 */
public class DbAndRpcHealthCheckListener implements SpringApplicationRunListener {

    private static final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new DefaultThreadFactory("dbHealthCheckWorker"));

    public static final AtomicBoolean DB_ALIVE = new AtomicBoolean(false);
    public static final AtomicBoolean RPC_ALIVE = new AtomicBoolean(false);

    public DbAndRpcHealthCheckListener(SpringApplication application, String[] args) {
    }

    /**
     * 一定要 catch 异常
     * @param context
     */
    @Override
    public void running(ConfigurableApplicationContext context) {
        BlockedAndFixedThreadPoolExecutor executor = (BlockedAndFixedThreadPoolExecutor) context.getBean(ThreadPoolConfig.REFUND_THREAD_POOL_EXECUTOR);
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            try {
                System.out.println("----> 这里执行 db 健康检查的任务, 根据健康检查的情况, 修改 dbAlive 状态.");
                DB_ALIVE.set(true);
                RPC_ALIVE.set(true);
                if (DB_ALIVE.get() && RPC_ALIVE.get()) {
                    executor.signalAll();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }, 60L, 10L, TimeUnit.SECONDS);
    }

}
