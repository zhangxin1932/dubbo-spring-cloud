package com.zy.service.impl;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Service("teacherService")
public class TeacherServiceImpl {

    public void teach() {
        System.out.println(Thread.currentThread().getName() + " .........> " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now()) + ": 教师开始教书.");
        // FIXME 模拟超时任务, 测试 MethodInvokingJobDetailFactoryBean#concurrent 为 false 时, 当上一个任务未执行完毕前, 是否开启下一个任务
        // @see com.zy.config.quartz.JobConfiguration03.teacherMethodInvokingJobDetailFactoryBean
        try {
            TimeUnit.SECONDS.sleep(20L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
