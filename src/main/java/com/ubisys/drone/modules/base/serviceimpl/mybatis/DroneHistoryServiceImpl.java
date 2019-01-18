package com.ubisys.drone.modules.base.serviceimpl.mybatis;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.common.utils.PageUtil;
import com.ubisys.drone.common.vo.PageVo;
import com.ubisys.drone.modules.base.dao.mapper.DroneHistoryMapper;
import com.ubisys.drone.modules.base.entity.Drone;
import com.ubisys.drone.modules.base.model.DroneHistory;
import com.ubisys.drone.modules.base.service.mybatis.DroneHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Administrator on 2019/1/7.
 */
@Slf4j
@Service
@Transactional
public class DroneHistoryServiceImpl implements DroneHistoryService {

    @Autowired
    private DroneHistoryMapper droneHistoryMapper;

    @Override
    public AjaxResponse queryDroneHistoryStatisticsByDate(String time, PageVo page) {
        Map<String , Object> map = new HashMap<String , Object>();
        map.put("whereTime", time);
        map.put("pageNumber", page.getPageNumber() * page.getPageSize() - page.getPageSize());
        map.put("pageSize", page.getPageSize());
        map.put("sort", page.getSort());
        map.put("order", page.getOrder());

        List<DroneHistory> droneHistoryList = droneHistoryMapper.queryDroneHistoryStatisticsByDate(map);
        int total = droneHistoryMapper.queryDroneHistoryStatisticsCountByDate(map);
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setCode(200);
        ajaxResponse.setMsg("ok");
        ajaxResponse.setRows(droneHistoryList);
        ajaxResponse.setTotal(total);
        return ajaxResponse;
    }

    @Override
    public AjaxResponse delDroneHistoryByDate(String times) {
        int i = droneHistoryMapper.delDroneHistoryEvents(times);
        AjaxResponse ajaxResponse = new AjaxResponse();
        if(i != 0){
            ajaxResponse.setCode(200);
            ajaxResponse.setMsg("ok");
        }else{
            ajaxResponse.setCode(500);
            ajaxResponse.setMsg("fail");
        }
        return ajaxResponse;
    }
}
