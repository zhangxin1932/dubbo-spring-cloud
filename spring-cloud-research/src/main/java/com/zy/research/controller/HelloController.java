package com.zy.research.controller;

import com.zy.research.config.async.SpringAsyncService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/hello/")
public class HelloController {
    @Resource
    private SpringAsyncService springAsyncService;

    @RequestMapping("async")
    public void asyncMethod(String msg) {
        System.out.println("begin ---> ");
        springAsyncService.asyncMethod(msg);
        System.out.println("end ---> ");
    }
}
