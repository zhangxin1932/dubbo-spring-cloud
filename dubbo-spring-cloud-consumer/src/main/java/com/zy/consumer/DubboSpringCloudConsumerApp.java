package com.zy.consumer;

import com.zy.model.DubboReqDTO;
import com.zy.model.DubboRespDTO;
import com.zy.service.IDubboService;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.EchoService;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableDubbo
@RestController
// @EnableJpaRepositories // 这个注解可以不加
@EntityScan(basePackages = "com.zy.*.entity") // 这里由于 entity 在公共包里, 这里需要自行扫描了
@EnableTransactionManagement
public class DubboSpringCloudConsumerApp {

    @DubboReference(group = "g1", version = "1.0.0", cluster = "combine", methods = {@Method(name = "dubboCombine", merger = "list"), @Method(name = "dubbo")})
    private IDubboService iDubboService;

    private RegistryConfig registry;

    public static void main(String[] args) {
        SpringApplication.run(DubboSpringCloudConsumerApp.class, args);
    }

    @RequestMapping("/dubboGeneric")
    public Object dubboGeneric() {
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        // 弱类型接口名
        reference.setInterface("com.zy.service.IDubboService");
        // 声明为泛化接口
        reference.setGeneric(Boolean.TRUE.toString());

        reference.setRegistry(registry);
        reference.setGroup("g1");
        reference.setVersion("1.0.0");

        // 用 GenericService 可以替代所有接口引用
        // GenericService genericService = reference.get();
        GenericService genericService = ReferenceConfigCache.getCache().get(reference);

        return genericService.$invoke("ping", new String[]{"java.lang.String"}, new Object[]{"OK"});

        /*final Map<String, Object> dubboReqDTO = new HashMap<>(2);
        dubboReqDTO.put("name", "tom");
        return genericService.$invoke("dubbo",
                new String[]{"com.zy.model.DubboReqDTO"},
                new Object[]{dubboReqDTO});*/

        // EchoService echoService = (EchoService) genericService;
        // return echoService.$echo("hello world");
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

    @PostConstruct
    public void init() throws ClassNotFoundException {
        this.registry = new RegistryConfig();
        this.registry.setAddress("zookeeper://192.168.0.156:2181");
    }

}
