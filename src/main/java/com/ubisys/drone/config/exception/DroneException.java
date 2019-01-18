package com.ubisys.drone.config.exception;

import lombok.Data;

/**
 * 自定义异常类
 */
@Data
public class DroneException extends RuntimeException {

    private String msg;

    public DroneException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
