package com.zy.research.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TbUser implements Serializable {
    private static final long serialVersionUID = -8690337873926319396L;
    private String username;
    private String password;
}
