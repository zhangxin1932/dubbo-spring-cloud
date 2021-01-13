package com.zy.sharding.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.zy.sharding.mybatis.mapper")
@SpringBootApplication
public class ShardingMybatisApp {
    public static void main(String[] args) {
        SpringApplication.run(ShardingMybatisApp.class, args);
    }
}
