package com.zy.service.impl;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service("accountService")
public class AccountServiceImpl {

    public void execute() {
        System.out.println(Thread.currentThread().getName() + " --------> " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now()) + ": 执行转账操作.");
    }
}
