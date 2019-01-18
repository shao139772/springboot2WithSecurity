package com.ubisys.drone.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * ip地址   vo
 */
@Data
public class IpLocate implements Serializable {

    private String retCode;

    private City result;
}

