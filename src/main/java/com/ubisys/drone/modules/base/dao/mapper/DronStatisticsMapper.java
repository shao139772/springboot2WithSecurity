package com.ubisys.drone.modules.base.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ubisys.drone.modules.base.entity.DronStatistics;

import java.util.List;

/**
 * 统计数据处理层
 *
 * @author Jhon Davis
 */
public interface DronStatisticsMapper extends BaseMapper<DronStatistics> {
    List<DronStatistics> findDronValue();


    // 报警次数
    Long countAlarms();


    // 白名单个数
    Long countWhites();

    //站点个数
    Long countSensor();

    //打击次数
    Long countAttacks();


}