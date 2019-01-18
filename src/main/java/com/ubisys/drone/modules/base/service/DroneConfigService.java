package com.ubisys.drone.modules.base.service;

import com.ubisys.drone.basetemplate.DroneBaseService;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.modules.base.entity.DroneConfig;

/**
 * O(∩_∩)O哈哈~接口
 *
 * @author miaomiao
 */
public interface DroneConfigService extends DroneBaseService<DroneConfig, Integer> {

    /**
     * 新增配置信息
     *
     * @param droneConfig
     * @return
     */
    AjaxResponse saveConfig(DroneConfig droneConfig);


    /**
     * 更新配置信息
     *
     * @param droneConfig
     * @return
     */
    AjaxResponse updateConfig(DroneConfig droneConfig);


    /**
     * 获取全部系统配置信息
     * @return
     */
    AjaxResponse getALL();

    /**
     * 加载配置文件到redis
     * @return
     */
    AjaxResponse loadConfigToRedis();


    /**
     * 清空redis缓存
     * @return
     */
    AjaxResponse clearRedis();
}