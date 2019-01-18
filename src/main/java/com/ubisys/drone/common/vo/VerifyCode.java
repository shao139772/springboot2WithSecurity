package com.ubisys.drone.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 验证码 vo
 */
@Data
public class VerifyCode implements Serializable {

    @ApiModelProperty(value = "验证码id")
    private String verifyCodeId;

    @ApiModelProperty(value = "验证码")
    private String code;
}
