package com.zy.producer.dubbo;

import com.google.common.collect.Lists;
import com.zy.model.DubboReqDTO;
import com.zy.model.DubboRespDTO;
import com.zy.service.IDubboService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;
import java.util.List;

@DubboService
@Component
public class IDubboServiceImpl implements IDubboService {

    @Override
    public DubboRespDTO dubbo(DubboReqDTO dubboReqDTO) {
        // 这里执行查询数据库的操作
        System.out.println("req....." + dubboReqDTO);
        return new DubboRespDTO("200", "success");
    }

    @Override
    public List<DubboRespDTO> dubboCombine(DubboReqDTO dubboReqDTO) {
        DubboRespDTO success = new DubboRespDTO("200", "success");
        return Lists.newArrayList(success);
    }
}
