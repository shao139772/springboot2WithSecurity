package com.ubisys.drone.modules.base.service;

import com.ubisys.drone.basetemplate.DroneBaseService;
import com.ubisys.drone.modules.base.entity.UbisysPendingCertification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Ubisys无人机待认证接口
 * @author Jhon Davis
 */
public interface UbisysPendingCertificationService extends DroneBaseService<UbisysPendingCertification,Integer> {

}