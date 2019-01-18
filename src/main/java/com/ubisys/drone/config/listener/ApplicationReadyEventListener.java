package com.ubisys.drone.config.listener;

import com.ubisys.drone.common.spring.SpringContextUtil;
import com.ubisys.drone.common.webSocket.WsDemoMsgHandler;
import com.ubisys.drone.common.webSocket.WsDemoStarter;
import com.ubisys.drone.modules.base.service.DroneConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * ApplicationListener
 *
 * @Auther: cw
 * @Date: 2018/11/1 09:13
 * @Description: spb就绪事件监听器  位于 started  之后
 */
@Slf4j
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {


    private String webSocketPort;

    private Boolean isStarted = false;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();
        webSocketPort = environment.getProperty("webSocket.port");
        if (StringUtils.isBlank(webSocketPort)) {
            log.info(" 容器仍未真正启动，再等等！~~~~");
        } else {
            log.info("容器已经真正启动， 准备启动webSocket服务......");
            try {
                if (!isStarted) {
                    WsDemoStarter appStarter = new WsDemoStarter(Integer.valueOf(webSocketPort), new WsDemoMsgHandler());
                    appStarter.start();
                    isStarted = true;
                }

                DroneConfigService configService = SpringContextUtil.getBean(DroneConfigService.class);
                //清空缓存
                configService.clearRedis();
                //加载配置属性到redis中
                configService.loadConfigToRedis();

            } catch (Exception e) {
                e.printStackTrace();
                log.info("服务器启动失败！");
            }
        }
    }


}
