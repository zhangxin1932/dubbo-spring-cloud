package com.zy.research.concurrent.threadpool.contorller;

import com.zy.research.concurrent.threadpool.dto.ThreadPoolExecutorDTO;
import com.zy.research.concurrent.threadpool.service.ThreadPoolExecutorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/threadPool/")
public class ThreadPoolExecutorController {

    @Autowired
    private ThreadPoolExecutorServiceImpl threadPoolExecutorService;

    @RequestMapping("getThreadPoolExecutorByName")
    public ThreadPoolExecutorDTO getThreadPoolExecutorByName(String poolName) {
        Assert.hasText(poolName, "poolName cannot be empty.");
        return threadPoolExecutorService.getThreadPoolExecutorByName(poolName);
    }

    @RequestMapping("getAllThreadPoolExecutors")
    public List<ThreadPoolExecutorDTO> getAllThreadPoolExecutors() {
        return threadPoolExecutorService.getAllThreadPoolExecutors();
    }

    @RequestMapping("updateThreadPoolByName")
    public Boolean updateThreadPoolByName(@RequestBody ThreadPoolExecutorDTO threadPoolExecutorDTO) {
        Assert.notNull(threadPoolExecutorDTO, "threadPoolExecutorDTO cannot be null.");
        Assert.hasText(threadPoolExecutorDTO.getPoolName(), "poolName cannot be empty.");
        return threadPoolExecutorService.updateThreadPoolByName(threadPoolExecutorDTO);
    }

}
