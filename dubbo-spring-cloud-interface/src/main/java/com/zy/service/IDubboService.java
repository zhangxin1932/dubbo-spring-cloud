package com.zy.service;

import com.zy.model.DubboReqDTO;
import com.zy.model.DubboRespDTO;

public interface IDubboService {

    DubboRespDTO dubbo(DubboReqDTO dubboReqDTO);

    DubboRespDTO[] dubboCombine(DubboReqDTO dubboReqDTO);

}
