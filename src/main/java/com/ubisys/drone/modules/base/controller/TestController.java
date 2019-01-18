package com.ubisys.drone.modules.base.controller;

import com.ubisys.drone.basetemplate.DroneBaseController;
import com.ubisys.drone.common.utils.PageUtil;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.modules.base.entity.Test;
import com.ubisys.drone.modules.base.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author miaomiao
 */
@Slf4j
@RestController
@Api(description = "O(∩_∩)O哈哈~管理接口")
@RequestMapping("/drone/test")
@Transactional
public class TestController extends DroneBaseController<Test, Integer>{

    @Autowired
    private TestService testService;

    @Override
    public TestService getService() {
        return testService;
    }



    public  AjaxResponse tt(){
        return  AjaxResponse.success();
    }
}
