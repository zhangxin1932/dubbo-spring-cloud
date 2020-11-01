package com.zy.producer.dubbo;

import com.zy.model.DubboReqDTO;
import com.zy.model.DubboRespDTO;
import com.zy.service.IDubboService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Service
@Component
public class IDubboServiceImpl implements IDubboService {
    @Override
    public DubboRespDTO dubbo(DubboReqDTO dubboReqDTO) {
        // 这里执行查询数据库的操作
        System.out.println("req....." + dubboReqDTO);
        return new DubboRespDTO("200", "success");
    }
}
