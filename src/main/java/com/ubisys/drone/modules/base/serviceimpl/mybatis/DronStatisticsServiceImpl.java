package com.ubisys.drone.modules.base.serviceimpl.mybatis;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.modules.base.dao.mapper.DronStatisticsMapper;
import com.ubisys.drone.modules.base.entity.DronStatistics;
import com.ubisys.drone.modules.base.service.mybatis.DronStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计接口实现
 *
 * @author Jhon Davis
 */
@Slf4j
@Service
@Transactional
public class DronStatisticsServiceImpl extends ServiceImpl<DronStatisticsMapper, DronStatistics> implements DronStatisticsService {

    @Autowired
    private DronStatisticsMapper dronStatisticsMapper;

    @Override
    public List<DronStatistics> findDronValue() {
        return dronStatisticsMapper.findDronValue();
    }

    @Override
    public AjaxResponse findStaticCount() {
        AjaxResponse response = AjaxResponse.success();
        Map<String, Object> result = new HashMap<String, Object>();

        Long alarms = dronStatisticsMapper.countAlarms();
        Long attacks = dronStatisticsMapper.countAttacks();
        Long sensors = dronStatisticsMapper.countSensor();
        Long whites = dronStatisticsMapper.countWhites();

        result.put("alarms", alarms);
        result.put("sensors", sensors);
        result.put("attacks", attacks);
        result.put("whites", whites);

        response.setAttributes(result);


        return response;
    }
}