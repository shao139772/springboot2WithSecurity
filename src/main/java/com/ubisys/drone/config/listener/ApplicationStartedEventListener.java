package com.ubisys.drone.config.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @Auther: cw
 * @Date: 2018/11/1 09:11
 * @Description: spb启动监听器
 */
@Slf4j
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        log.info("......ApplicationStartedEvent  O(∩_∩)O哈哈~ listening......");
    }


}
