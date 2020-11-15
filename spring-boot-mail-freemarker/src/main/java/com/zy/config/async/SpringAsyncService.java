package com.zy.config.async;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class SpringAsyncService {

    @Async
    public void asyncMethod(String msg) {
        System.out.println("asyncMethod begin ......");
        System.out.println(msg);
        System.out.println("asyncMethod end >>>>>>");
    }
}
