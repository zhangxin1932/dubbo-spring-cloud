package com.zy.research.concurrent.threadpool.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreadPoolExecutorDTO implements Serializable {
    private String poolName;
    private Integer corePoolSize;
    private Integer maximumPoolSize;
    private Integer largestPoolSize;
    private Integer poolSize;
    private Integer queueSize;
    private Long keepAliveTime;
    private Long completedTaskCount;
    private Integer activeCount;
    private Long taskCount;
    private String rejectHandlerClassName;
    private Boolean allowCoreThreadTimeOut;
}
