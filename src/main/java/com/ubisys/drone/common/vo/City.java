package com.ubisys.drone.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 城市  city  VO
 */
@Data
public class City implements Serializable {

    String country;

    String province;

    String city;
}
