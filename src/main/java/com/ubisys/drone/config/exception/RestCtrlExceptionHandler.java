package com.ubisys.drone.config.exception;

import com.ubisys.drone.common.utils.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常切片
 */
@Slf4j
@RestControllerAdvice
public class RestCtrlExceptionHandler {

    @ExceptionHandler(DroneException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public AjaxResponse handleXCloudException(DroneException e) {

        String errorMsg = "drone exception";
        if (e != null) {
            errorMsg = e.getMsg();
            log.error(e.toString());
        }
        return AjaxResponse.failed(errorMsg);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    public AjaxResponse handleException(Exception e) {

        String errorMsg = "Exception";
        if (e != null) {
            errorMsg = e.getMessage();
            log.error(e.toString());
        }
        return AjaxResponse.failed(errorMsg);
    }
}
