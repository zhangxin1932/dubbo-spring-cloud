package com.zy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DubboRespDTO implements Serializable {
    private static final long serialVersionUID = -4242143289874373002L;
    private String code;
    private String msg;
}
