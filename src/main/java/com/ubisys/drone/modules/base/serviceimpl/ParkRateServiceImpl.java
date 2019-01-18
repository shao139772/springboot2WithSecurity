package com.ubisys.drone.modules.base.serviceimpl;

import com.ubisys.drone.modules.base.dao.ParkRateDao;
import com.ubisys.drone.modules.base.entity.ParkRate;
import com.ubisys.drone.modules.base.service.ParkRateService;
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
public class ParkRateServiceImpl implements ParkRateService {

    @Autowired
    private ParkRateDao parkRateDao;

    @Override
    public ParkRateDao getRepository() {
        return parkRateDao;
    }
}