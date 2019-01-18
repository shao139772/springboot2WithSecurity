package com.ubisys.drone.modules.base.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ubisys.drone.modules.base.entity.DronStatistics;
import com.ubisys.drone.modules.base.entity.Duration;

import java.util.List;

/**
 * 统计数据处理层
 * @author Jhon Davis
 */
public interface DurationMapper extends BaseMapper<Duration> {
    List<Duration> findDuration();

    List<Duration> findCreatTime();

    List<Duration> findFreq();
}