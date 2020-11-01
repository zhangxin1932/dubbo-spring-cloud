package com.zy.consumer;

import com.zy.model.DubboReqDTO;
import com.zy.model.DubboRespDTO;
import com.zy.service.IDubboService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDubbo
@RestController
// @EnableJpaRepositories // 这个注解可以不加
@EntityScan(basePackages = "com.zy.*.entity") // 这里由于 entity 在公共包里, 这里需要自行扫描了
@EnableTransactionManagement
public class DubboSpringCloudConsumerApp {

    @Reference
    private IDubboService iDubboService;

    public static void main(String[] args) {
        SpringApplication.run(DubboSpringCloudConsumerApp.class, args);
    }

    @RequestMapping("/dubbo")
    public DubboRespDTO dubbo(@RequestBody DubboReqDTO req) {
        return iDubboService.dubbo(req);
    }
}
