package com.zy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DubboReqDTO implements Serializable {
    private static final long serialVersionUID = -3900645345635337094L;
    private String name;
    private Integer age;
}
