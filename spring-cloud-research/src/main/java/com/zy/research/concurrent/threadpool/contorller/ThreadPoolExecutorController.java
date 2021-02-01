package com.zy.research.concurrent.threadpool.contorller;

import com.zy.research.concurrent.threadpool.config.BlockedAndFixedThreadPoolExecutor;
import com.zy.research.concurrent.threadpool.dto.ThreadPoolExecutorDTO;
import com.zy.research.concurrent.threadpool.service.ThreadPoolExecutorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/threadPool/")
public class ThreadPoolExecutorController {

    @Autowired
    private ThreadPoolExecutorServiceImpl threadPoolExecutorService;

    @Autowired
    private BlockedAndFixedThreadPoolExecutor refundThreadPoolExecutor;

    @PostConstruct
    public void init() {
        AtomicLong num = new AtomicLong();
        System.out.println("......>");
        refundThreadPoolExecutor.execute(() -> System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now()) + " ---> " + num.incrementAndGet()));
        System.out.println("------>");
    }

    @RequestMapping("modifyDBStatus")
    public boolean modifyDBStatus(boolean b) {
        refundThreadPoolExecutor.setDbAlive(b);
        return refundThreadPoolExecutor.getDbAlive();
    }

    @RequestMapping("getThreadPoolExecutorByName")
    public ThreadPoolExecutorDTO getThreadPoolExecutorByName(String poolName) {
        Assert.hasText(poolName, "poolName cannot be empty.");
        return threadPoolExecutorService.getThreadPoolExecutorByName(poolName);
    }

    @RequestMapping("getAllThreadPoolExecutors")
    public List<ThreadPoolExecutorDTO> getAllThreadPoolExecutors() {
        return threadPoolExecutorService.getAllThreadPoolExecutors();
    }

    /**
     * 更新操作时需注意:
     * 1.参数校验: 严格校验范围
     * 2.当这个方法被设计为 RPC 接口方法作为服务提供方时, 由于服务调用方(比如后台)默认是只调用一台机器, 故需广播调用
     * 2.1 方案1: dubbo 配置集群调用
     * 2.2 方案2: 调用方发 MQ 消息, 应用消费 MQ 消息(消费模式为 广播消费)
     * @param threadPoolExecutorDTO
     * @return
     */
    @RequestMapping("updateThreadPoolByName")
    public Boolean updateThreadPoolByName(@RequestBody ThreadPoolExecutorDTO threadPoolExecutorDTO) {
        Assert.notNull(threadPoolExecutorDTO, "threadPoolExecutorDTO cannot be null.");
        Assert.hasText(threadPoolExecutorDTO.getPoolName(), "poolName cannot be empty.");
        return threadPoolExecutorService.updateThreadPoolByName(threadPoolExecutorDTO);
    }

}
