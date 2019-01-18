package com.ubisys.drone.modules.base.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ubisys.drone.modules.base.entity.Drone;
import com.ubisys.drone.modules.base.model.DroneHistory;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/19 19:03
 */
public interface DroneHistoryMapper {
    List<DroneHistory> queryDroneHistoryStatisticsByDate(@Param(value="map") Map map);

    int queryDroneHistoryStatisticsCountByDate(@Param(value="map") Map map);

    int delDroneHistoryEvents(@Param(value="times") String times);
}
