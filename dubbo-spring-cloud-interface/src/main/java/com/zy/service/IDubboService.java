package com.zy.service;

import com.zy.model.DubboReqDTO;
import com.zy.model.DubboRespDTO;

import java.util.List;

public interface IDubboService {

    default String ping(String msg) {
        return msg;
    }

    DubboRespDTO dubbo(DubboReqDTO dubboReqDTO);

    List<DubboRespDTO> dubboCombine(DubboReqDTO dubboReqDTO);

}
