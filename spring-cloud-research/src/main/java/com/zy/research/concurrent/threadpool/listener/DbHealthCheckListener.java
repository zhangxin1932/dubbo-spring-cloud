package com.zy.research.concurrent.threadpool.listener;

import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@Component
public class DbHealthCheckListener {
/*

    private static final String dbHealthCheckSQL = "select 1 from dual";

    @PostConstruct
    public void init() {
        System.out.println("///////////// 开始 db 健康检查");
        ThreadFactory threadFactory = r -> {
            Thread t = new Thread("dbHealthCheck");
            // t.setDaemon(true);
            return t;
        };
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(threadFactory);
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            System.out.println("----> 这里执行 db 健康检查的任务.");
        }, 60L, 10L, TimeUnit.SECONDS);
    }
*/

}
