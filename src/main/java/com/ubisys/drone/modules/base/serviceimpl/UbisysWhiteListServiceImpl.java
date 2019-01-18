package com.ubisys.drone.modules.base.serviceimpl;

import com.ubisys.drone.modules.base.dao.UbisysWhiteListDao;
import com.ubisys.drone.modules.base.entity.UbisysWhiteList;
import com.ubisys.drone.modules.base.service.UbisysWhiteListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Ubisys无人机白名单接口实现
 * @author Jhon Davis
 */
@Slf4j
@Service
@Transactional
public class UbisysWhiteListServiceImpl implements UbisysWhiteListService {

    @Autowired
    private UbisysWhiteListDao ubisysWhiteListDao;

    @Override
    public UbisysWhiteListDao getRepository() {
        return ubisysWhiteListDao;
    }
}