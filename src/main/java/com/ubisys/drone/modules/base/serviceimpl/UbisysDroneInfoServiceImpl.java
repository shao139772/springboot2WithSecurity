package com.ubisys.drone.modules.base.serviceimpl;

import com.ubisys.drone.modules.base.dao.UbisysDroneInfoDao;
import com.ubisys.drone.modules.base.entity.UbisysDroneInfo;
import com.ubisys.drone.modules.base.service.UbisysDroneInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Ubisys无人机信息登记接口实现
 * @author Jhon Davis
 */
@Slf4j
@Service
@Transactional
public class UbisysDroneInfoServiceImpl implements UbisysDroneInfoService {

    @Autowired
    private UbisysDroneInfoDao ubisysDroneInfoDao;

    @Override
    public UbisysDroneInfoDao getRepository() {
        return ubisysDroneInfoDao;
    }
}