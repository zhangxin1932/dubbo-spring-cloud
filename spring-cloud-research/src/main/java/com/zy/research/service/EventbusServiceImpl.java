package com.zy.research.service;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service("eventbusService")
public class EventbusServiceImpl {

    @Resource
    private AsyncEventBus asyncEventBus;

    @PostConstruct
    public void init() {
        asyncEventBus.register(this);
    }

    /**
     * 可被 eventbus 识别的方法
     * @param str
     */
    @AllowConcurrentEvents//线程安全
    @Subscribe
    public void async01(String str){
        System.out.println("str is: " + str);
    }

    /**
     * 普通方法
     * @param msg
     */
    public void async02(String msg) {
        System.out.println("msg is: " + msg);
    }
}
