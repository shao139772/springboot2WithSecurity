package com.ubisys.drone;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/14 10:18
 */
@SpringBootApplication
//启用缓存
@EnableCaching
//启用异步
@EnableAsync
//开启定时任务
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Slf4j
//暂时关闭security校验
/*@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})*/

public class RyApplication  {
    public static void main(String[] args) {
        log.info("  中科融通   瑞鹰  无人机  启动  ing!!!  ");
        SpringApplication.run(RyApplication.class, args);
    }


    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(RyApplication.class);
    }*/


}
