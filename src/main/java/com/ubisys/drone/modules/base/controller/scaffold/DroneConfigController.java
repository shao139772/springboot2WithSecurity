package com.ubisys.drone.modules.base.controller.scaffold;

import com.alibaba.fastjson.JSONObject;
import com.ubisys.drone.basetemplate.DroneBaseController;
import com.ubisys.drone.common.constant.DroneConstant;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.modules.base.entity.DroneConfig;
import com.ubisys.drone.modules.base.service.DroneConfigService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


/**
 * 系统配置接口
 *
 * @author miaomiao
 */
@Slf4j
@RestController
@Api(description = "系统配置接口")
@RequestMapping("/drone/droneConfig")
@Transactional
public class DroneConfigController extends DroneBaseController<DroneConfig, Integer> {

    @Autowired
    private DroneConfigService droneConfigService;

    @Override
    public DroneConfigService getService() {
        return droneConfigService;
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse save(@ModelAttribute DroneConfig droneConfig) {
        log.info(" 新增配置信息 " + droneConfig.toString());
        if (StringUtils.isBlank(droneConfig.getConfigCode())) {
            return AjaxResponse.build(501, "配置的基本信息不能为空!");
        }
        try {
            return droneConfigService.saveConfig(droneConfig);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(DroneConstant.ERR_MSG, e);
            return AjaxResponse.build(502, " 新增配置信息失败！ ");
        }
    }


    @RequestMapping(value = "/getALL", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse getALL() {
        log.info(" 获取系统配置信息  getALL " );

        try {
            return droneConfigService.getALL();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(DroneConstant.ERR_MSG, e);
            return AjaxResponse.build(502, " 获取配置信息失败！ ");
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse update(@ModelAttribute DroneConfig droneConfig,@RequestParam(required = false) String info) {
        log.info(" 修改配置信息 " + droneConfig.toString()+ " info "+info);
        try {
            if(StringUtils.isNotBlank(info)){
                droneConfig= JSONObject.parseObject(info,DroneConfig.class);
            }
            return droneConfigService.updateConfig(droneConfig);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(DroneConstant.ERR_MSG, e);
            return AjaxResponse.build(502, " 修改配置信息失败！ ");
        }
    }


}
