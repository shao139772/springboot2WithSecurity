package com.ubisys.drone.modules.base.service;

import com.ubisys.drone.basetemplate.DroneBaseService;
import com.ubisys.drone.modules.base.entity.UbisysWhiteList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Ubisys无人机白名单接口
 * @author Jhon Davis
 */
public interface UbisysWhiteListService extends DroneBaseService<UbisysWhiteList,Integer> {

}