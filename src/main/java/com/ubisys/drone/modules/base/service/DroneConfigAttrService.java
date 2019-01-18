package com.ubisys.drone.modules.base.service;

import com.ubisys.drone.basetemplate.DroneBaseService;
import com.ubisys.drone.modules.base.entity.DroneConfigAttr;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * O(∩_∩)O哈哈~接口
 * @author miaomiao
 */
public interface DroneConfigAttrService extends DroneBaseService<DroneConfigAttr,Integer> {

    /**
     * 根据 configcode  获取配置属性
     * @param configCode
     * @return
     */
    List<DroneConfigAttr> findByConfCode(String configCode);
}