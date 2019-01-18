package com.ubisys.drone.modules.base.serviceimpl;

import com.ubisys.drone.modules.base.dao.DroneConfigAttrDao;
import com.ubisys.drone.modules.base.entity.DroneConfigAttr;
import com.ubisys.drone.modules.base.service.DroneConfigAttrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * O(∩_∩)O哈哈~接口实现
 * @author miaomiao
 */
@Slf4j
@Service
@Transactional
public class DroneConfigAttrServiceImpl implements DroneConfigAttrService {

    @Autowired
    private DroneConfigAttrDao droneConfigAttrDao;

    @Override
    public DroneConfigAttrDao getRepository() {
        return droneConfigAttrDao;
    }



    /**
     * 根据 configcode  获取配置属性
     * @param configCode
     * @return
     */
    @Override
    public List<DroneConfigAttr> findByConfCode(String configCode) {
        return droneConfigAttrDao.findByConfCode(configCode);
    }
}