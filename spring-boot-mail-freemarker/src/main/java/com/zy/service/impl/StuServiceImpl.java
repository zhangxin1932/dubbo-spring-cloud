package com.zy.service.impl;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service("stuService")
public class StuServiceImpl {

    public void sync() {
        System.out.println(Thread.currentThread().getName() + " .........> " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now()) + ": 执行学生信息同步操作.");
    }
}
