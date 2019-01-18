package com.ubisys.drone.modules.base.serviceimpl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.common.utils.DroneConvertUtil;
import com.ubisys.drone.modules.base.dao.DroneDao;
import com.ubisys.drone.modules.base.entity.Drone;
import com.ubisys.drone.modules.base.entity.LzDrone;
import com.ubisys.drone.modules.base.model.DroneThirdFloor;
import com.ubisys.drone.modules.base.service.DroneService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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
public class DroneServiceImpl implements DroneService {

    @Autowired
    private DroneDao droneDao;

    @Override
    public DroneDao getRepository() {
        return droneDao;
    }




}