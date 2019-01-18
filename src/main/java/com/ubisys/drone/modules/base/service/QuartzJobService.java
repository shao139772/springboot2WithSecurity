package com.ubisys.drone.modules.base.service;


import com.ubisys.drone.basetemplate.DroneBaseService;
import com.ubisys.drone.modules.base.entity.QuartzJob;

import java.util.List;

/**
 * 定时任务接口
 */
public interface QuartzJobService extends DroneBaseService<QuartzJob,Integer> {

    /**
     * 通过类名获取
     * @param jobClassName
     * @return
     */
    List<QuartzJob> findByJobClassName(String jobClassName);
}