package com.zy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 600)
@SpringBootApplication
public class SpringBootMailFreemarkerApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMailFreemarkerApp.class, args);
    }
}
