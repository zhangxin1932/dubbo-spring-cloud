package com.zy.producer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDubbo
@RestController
@EnableTransactionManagement
@MapperScan("com.zy.producer.*.mapper")
public class DubboSpringCloudProducerApp {
    public static void main(String[] args) {
        SpringApplication.run(DubboSpringCloudProducerApp.class, args);
    }
}
