package com.ubisys.drone.config.exception;

import lombok.Data;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * <p>Title: 认证失败的异常扩展</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/19 16:44
 */
@Data
public class LoginFailLimitException extends InternalAuthenticationServiceException {
    private String msg;

    public LoginFailLimitException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public LoginFailLimitException(String msg, Throwable t) {
        super(msg, t);
        this.msg = msg;
    }
}
