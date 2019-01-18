package com.ubisys.drone.modules.base.service.mybatis;

import com.baomidou.mybatisplus.service.IService;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.modules.base.entity.DronStatistics;

import java.util.List;

/**
 * 统计接口
 *
 * @author Jhon Davis
 */
public interface DronStatisticsService extends IService<DronStatistics> {

    List<DronStatistics> findDronValue();

    //统计分布
    AjaxResponse findStaticCount();
}