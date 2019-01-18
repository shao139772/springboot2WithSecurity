package com.ubisys.drone.modules.base.dao;


import com.ubisys.drone.basetemplate.DroneBaseDao;
import com.ubisys.drone.modules.base.entity.QuartzJob;

import java.util.List;

/**
 * 定时任务数据处理层
 */
public interface QuartzJobDao extends DroneBaseDao<QuartzJob,Integer> {

    /**
     * 通过类名获取
     * @param jobClassName
     * @return
     */
    List<QuartzJob> findByJobClassName(String jobClassName);
}