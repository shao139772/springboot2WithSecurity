package com.ubisys.drone.modules.base.service.mybatis;

import com.baomidou.mybatisplus.service.IService;
import com.ubisys.drone.modules.base.entity.Duration;

import java.util.List;

/**
 * 统计接口
 *
 * @author Jhon Davis
 */
public interface DurationService extends IService<Duration> {
    //入侵时长统计
    List<Duration> findDuration();

    //时间分布统计
    List<Duration> findCreatTime();

    List<Duration> findFreq();

}