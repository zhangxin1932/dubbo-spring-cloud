package com.zy;

import com.zy.config.ymlbean.YmlBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 600)
@SpringBootApplication
public class SpringBootMailFreemarkerApp {

    @Autowired
    private YmlBean ymlBean;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMailFreemarkerApp.class, args);
    }

    @PostConstruct
    public void init() {
        System.out.println("yml bean begin .................");
        System.out.println(ymlBean);
        System.out.println("yml bean end .................");

    }
}
