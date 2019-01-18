package com.ubisys.drone.modules.base.serviceimpl.mybatis;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ubisys.drone.modules.base.dao.mapper.DronStatisticsMapper;
import com.ubisys.drone.modules.base.dao.mapper.DurationMapper;
import com.ubisys.drone.modules.base.entity.DronStatistics;
import com.ubisys.drone.modules.base.entity.Duration;
import com.ubisys.drone.modules.base.service.mybatis.DronStatisticsService;
import com.ubisys.drone.modules.base.service.mybatis.DurationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 统计接口实现
 * @author Jhon Davis
 */
@Slf4j
@Service
@Transactional
public class DurationServiceImpl extends ServiceImpl<DurationMapper, Duration> implements DurationService {

    @Autowired
    private DurationMapper durationMapper;

    @Override
    public List<Duration> findDuration() {
        return durationMapper.findDuration();
    }

    @Override
    public List<Duration> findCreatTime() {
        return durationMapper.findCreatTime();
    }

    @Override
    public List<Duration> findFreq() {
        return durationMapper.findFreq();
    }

}