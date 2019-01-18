package com.ubisys.drone.modules.base.controller;

import com.ubisys.drone.basetemplate.DroneBaseController;
import com.ubisys.drone.basetemplate.DroneBaseService;
import com.ubisys.drone.common.constant.DroneConstant;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.common.vo.PageVo;
import com.ubisys.drone.modules.base.entity.Drone;
import com.ubisys.drone.modules.base.model.DroneHistory;
import com.ubisys.drone.modules.base.service.mybatis.DroneHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author miaomiao
 */
@Slf4j
@RestController
@Api(description = "O(∩_∩)O哈哈~无人机历史事件管理接口")
@RequestMapping("/drone/history")
@Transactional
public class DroneHistoryController  extends DroneBaseController<DroneHistory, Integer> {

    @Autowired
    private DroneHistoryService droneHistoryService;
    /**
     * 分页获取无人机事件  历史统计信息
     *
     * @param page 分页对象
     * @return
     */
    @RequestMapping(value = "/findDroneHistoryStatisticsEvents", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    @ApiOperation(value = " 分页获取无人机事件  历史统计信息")
    public AjaxResponse findDroneHistoryStatisticsEvents(@ApiParam("查询时间") @RequestParam(required = false) String time, @ModelAttribute PageVo page) {
        log.info("  findDroneHistoryEvents  ");
        try {
            return droneHistoryService.queryDroneHistoryStatisticsByDate(time, page);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(DroneConstant.ERR_MSG, e);
        }
        return AjaxResponse.failed(DroneConstant.ERR_MSG);
    }

    /**
     * 批量删除
     *
     * @param page 分页对象
     * @return
     */
    @RequestMapping(value = "/delDroneHistoryEvents", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    @ApiOperation(value = " 批量删除")
    public AjaxResponse delDroneHistoryEvents(@ApiParam("删除日期") @RequestParam String times) {
        log.info("  delDroneHistoryEvents  ");
        try {
            return droneHistoryService.delDroneHistoryByDate(times);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(DroneConstant.ERR_MSG, e);
        }
        return AjaxResponse.failed(DroneConstant.ERR_MSG);
    }

    @Override
    public DroneBaseService<DroneHistory, Integer> getService() {
        return null;
    }
}
