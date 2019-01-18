package com.ubisys.drone.modules.base.service;

import com.ubisys.drone.basetemplate.DroneBaseService;
import com.ubisys.drone.modules.base.entity.UbisysDroneInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Ubisys无人机信息登记接口
 * @author Jhon Davis
 */
public interface UbisysDroneInfoService extends DroneBaseService<UbisysDroneInfo,Integer> {

}