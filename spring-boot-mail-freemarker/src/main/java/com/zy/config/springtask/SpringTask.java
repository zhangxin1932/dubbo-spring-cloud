package com.zy.config.springtask;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SpringTask {

    // @Scheduled(cron = "0/5 * * * * ?")  // 每5秒执行一次
    @Scheduled(fixedDelay = 2000)  // 每2秒执行一次
    public void saySchedule(){
        System.out.println("saySchedule ------------------");
    }

}
