package com.ubisys.drone.config.security.jwt;


import com.ubisys.drone.common.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 接口访问拒绝
 *
 * 用来解决认证过的用户访问无权限资源时的异常
 */
@Component
@Slf4j
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        ResponseUtil.out(response, ResponseUtil.resultMap(false, 403, "抱歉，您没有访问权限"));
    }

}
