package com.ubisys.drone.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * es  vo
 */
@Data
public class SearchVo implements Serializable {

    private String startDate;

    private String endDate;
}
