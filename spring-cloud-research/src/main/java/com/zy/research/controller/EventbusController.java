package com.zy.research.controller;

import com.google.common.eventbus.AsyncEventBus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class EventbusController {

    @Resource
    private AsyncEventBus asyncEventBus;

    @RequestMapping("eventbus")
    public String eventbus(String msg) {
        // eventbus 根据参数类型调用
        asyncEventBus.post(msg);
        return "success";
    }
}
