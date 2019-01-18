package com.ubisys.drone.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * token 封装用户权限对象
 */
@Data
@AllArgsConstructor
public class TokenUser implements Serializable {

    //用户名
    private String username;

    //权限信息
    private List<String> permissions;


    private Boolean saveLogin;
}
