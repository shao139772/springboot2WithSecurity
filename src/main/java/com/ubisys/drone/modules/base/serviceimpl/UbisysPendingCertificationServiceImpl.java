package com.ubisys.drone.modules.base.serviceimpl;

import com.ubisys.drone.modules.base.dao.UbisysPendingCertificationDao;
import com.ubisys.drone.modules.base.entity.UbisysPendingCertification;
import com.ubisys.drone.modules.base.service.UbisysPendingCertificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Ubisys无人机待认证接口实现
 * @author Jhon Davis
 */
@Slf4j
@Service
@Transactional
public class UbisysPendingCertificationServiceImpl implements UbisysPendingCertificationService {

    @Autowired
    private UbisysPendingCertificationDao ubisysPendingCertificationDao;

    @Override
    public UbisysPendingCertificationDao getRepository() {
        return ubisysPendingCertificationDao;
    }
}