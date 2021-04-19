package com.zy.consumer;

import com.zy.model.DubboReqDTO;
import com.zy.model.DubboRespDTO;
import com.zy.service.IDubboService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.rpc.service.EchoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@EnableDubbo
@RestController
// @EnableJpaRepositories // 这个注解可以不加
@EntityScan(basePackages = "com.zy.*.entity") // 这里由于 entity 在公共包里, 这里需要自行扫描了
@EnableTransactionManagement
public class DubboSpringCloudConsumerApp {

    @DubboReference(cluster = "combine", methods = {@Method(name = "dubboCombine", merger = "list"), @Method(name = "dubbo")})
    private IDubboService iDubboService;

    public static void main(String[] args) {
        SpringApplication.run(DubboSpringCloudConsumerApp.class, args);
    }

    @RequestMapping("/dubboCombine")
    public List<DubboRespDTO> dubboCombine(@RequestBody DubboReqDTO req) {
        // TODO 进行回声测试: 强制转型为 EchoService
        EchoService echoService = (EchoService) iDubboService;
        System.out.println("进行回声测试 --> " + echoService.$echo("hello"));

        // TODO 泛化调用
        // https://www.cnblogs.com/flyingeagle/p/8908317.html
        //
        return iDubboService.dubboCombine(req);
    }

}
