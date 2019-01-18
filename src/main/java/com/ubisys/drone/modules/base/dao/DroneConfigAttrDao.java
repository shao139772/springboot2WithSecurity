package com.ubisys.drone.modules.base.dao;

import com.ubisys.drone.basetemplate.DroneBaseDao;
import com.ubisys.drone.modules.base.entity.DroneConfigAttr;

import java.util.List;

/**
 * O(∩_∩)O哈哈~数据处理层
 * @author miaomiao
 */
public interface DroneConfigAttrDao extends DroneBaseDao<DroneConfigAttr,Integer> {


    /**
     * 根据 configcode  获取配置属性
     * @param configCode
     * @return
     */
    List<DroneConfigAttr> findByConfCode(String configCode);
}