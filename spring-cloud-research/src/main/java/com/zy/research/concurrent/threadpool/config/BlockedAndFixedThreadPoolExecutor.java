package com.zy.research.concurrent.threadpool.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class BlockedAndFixedThreadPoolExecutor extends ThreadPoolExecutor {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = this.lock.newCondition();
    private static final int BLOCKING_QUEUE_SIZE = 1024 * 2;
    private final AtomicBoolean dbAlive = new AtomicBoolean(true);
    private final AtomicBoolean rpcAlive = new AtomicBoolean(true);
    @Getter
    private final int blockingQueueSize;

    public BlockedAndFixedThreadPoolExecutor(int poolSize, int blockingQueueSize, String threadPrefix) {
        super(poolSize, poolSize, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(blockingQueueSize <= 0 ? BLOCKING_QUEUE_SIZE : blockingQueueSize), new ThreadFactory() {
            private final LongAdder threadNo = new LongAdder();

            @Override
            public Thread newThread(Runnable r) {
                threadNo.increment();
                String threadName = threadPrefix + threadNo.longValue();
                Thread thread = new Thread(r, threadName);
                thread.setDaemon(true);
                return thread;
            }
        });
        this.blockingQueueSize = blockingQueueSize <= 0 ? BLOCKING_QUEUE_SIZE : blockingQueueSize;
    }

    @Override
    public void execute(Runnable command) {
        this.lock.lock();
        try {
            while (!dbAlive.get() || !rpcAlive.get()) {
                this.condition.await(3L, TimeUnit.SECONDS);
            }
            super.execute(command);
        } catch (Throwable e) {
            log.warn("failed to execute task.", e);
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * 其他任务通过定时任务修改此状态时, 应保证确定应用启动后修改此状态. 比如初始化后延迟 10min 后调度.
     * @param alive
     */
    public void setDbAlive(boolean alive) {
        this.lock.lock();
        try {
            dbAlive.set(alive);
            if (dbAlive.get() && rpcAlive.get()) {
                this.condition.signalAll();
            }
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * 其他任务通过定时任务修改此状态时, 应保证确定应用启动后修改此状态. 比如初始化后延迟 10min 后调度.
     * @param alive
     */
    public void setRpcAlive(boolean alive) {
        this.lock.lock();
        try {
            rpcAlive.set(alive);
            if (dbAlive.get() && rpcAlive.get()) {
                this.condition.signalAll();
            }
        } finally {
            this.lock.unlock();
        }
    }

    public boolean isDbAlive() {
        return dbAlive.get();
    }

    public boolean isRpcAlive() {
        return rpcAlive.get();
    }

}
