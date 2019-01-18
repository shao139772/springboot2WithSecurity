package com.ubisys.drone.modules.base.controller;

import com.ubisys.drone.basetemplate.DroneBaseController;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.modules.base.entity.ParkRate;
import com.ubisys.drone.modules.base.service.ParkRateService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


/**
 * @author miaomiao
 */
@Slf4j
@RestController
@Api(description = "O(∩_∩)O哈哈~管理接口")
@RequestMapping("/drone/parkRate")
@Transactional
public class ParkRateController extends DroneBaseController<ParkRate, Integer> {

    @Autowired
    private ParkRateService parkRateService;

    @Override
    public ParkRateService getService() {
        return parkRateService;
    }


    @RequestMapping(value = "/testget/{id}", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse testGet(@PathVariable String id) {
        ParkRate rate = parkRateService.get(1);
        return AjaxResponse.success(rate);
    }


}
