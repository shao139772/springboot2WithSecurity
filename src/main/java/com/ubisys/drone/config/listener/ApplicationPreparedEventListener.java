package com.ubisys.drone.config.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @Auther: cw
 * @Date: 2018/11/1 09:08
 * @Description: 监听springboot准备启动
 */
@Slf4j
public class ApplicationPreparedEventListener implements ApplicationListener<ApplicationPreparedEvent> {


    @Override
    public void onApplicationEvent(ApplicationPreparedEvent applicationPreparedEvent) {
        log.info("   applicationPreparedEvent   O(∩_∩)O哈哈~ listening");
    }
}
