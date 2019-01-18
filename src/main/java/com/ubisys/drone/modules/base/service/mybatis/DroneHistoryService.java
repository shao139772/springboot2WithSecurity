package com.ubisys.drone.modules.base.service.mybatis;

import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.common.vo.PageVo;
import com.ubisys.drone.modules.base.model.DroneHistory;

import java.util.Date;
import java.util.List;

/**
 * 事件历史数据统计接口
 * Created by Administrator on 2019/1/7.
 */
public interface DroneHistoryService {

    AjaxResponse queryDroneHistoryStatisticsByDate(String time, PageVo page);

    AjaxResponse delDroneHistoryByDate(String times);
}
