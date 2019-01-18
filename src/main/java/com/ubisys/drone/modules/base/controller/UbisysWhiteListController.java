package com.ubisys.drone.modules.base.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ubisys.drone.basetemplate.DroneBaseController;
import com.ubisys.drone.common.utils.JsonUtils;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.common.vo.PageVo;
import com.ubisys.drone.modules.base.entity.UbisysDroneInfo;
import com.ubisys.drone.modules.base.entity.UbisysPendingCertification;
import com.ubisys.drone.modules.base.entity.UbisysWhiteList;
import com.ubisys.drone.modules.base.service.UbisysDroneInfoService;
import com.ubisys.drone.modules.base.service.UbisysWhiteListService;
import com.ubisys.drone.modules.base.service.mybatis.UbWhiteListService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author Jhon Davis
 */
@Slf4j
@RestController
@Api(description = "Ubisys无人机白名单管理接口")
@RequestMapping("/drone/ubisysWhiteList")
@Transactional
public class UbisysWhiteListController extends DroneBaseController<UbisysWhiteList, Integer> {

    @Autowired
    private UbisysWhiteListService ubisysWhiteListService;

    @Autowired
    private UbWhiteListService ubWhiteListService;

    @Override
    public UbisysWhiteListService getService() {
        return ubisysWhiteListService;
    }


    @Autowired
    private UbisysDroneInfoService ubisysDroneInfoService;

    /**
     * 踢出白名单
     *
     * @return
     * @author Jhon Davis
     * @date 2018-12-25
     * 192.168.1.36:8000/drone/ubisysWhiteList/kickOutWhiteList
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/kickOutWhiteList", method = RequestMethod.POST)
    public AjaxResponse kickOutWhiteList(@RequestParam String info) {
        try {
            JSONArray jsonArray = JSON.parseArray(info);
            List<UbisysWhiteList> ll = JSONObject.parseArray(jsonArray.toJSONString(), UbisysWhiteList.class);
            for (UbisysWhiteList ubisysWhiteList : ll) {
                Date date = new Date();
                Integer id = ubisysWhiteList.getId();
                Date registrationTime = ubisysWhiteList.getRegistrationTime();//登记时间
                String serialNumber = ubisysWhiteList.getSerialNumber();//序列号
                String chipNumber = ubisysWhiteList.getChipNumber();//芯片号
                Date startTime = ubisysWhiteList.getStartTime();//有效开始时间
                Date endTime = ubisysWhiteList.getStartTime();//有效截止时间
                Date createTime = ubisysWhiteList.getCreateTime();
                String createBy = ubisysWhiteList.getCreateBy();
                String ubDroneUseWay = ubisysWhiteList.getUbDroneUseWay();//用途
                String vendorName = ubisysWhiteList.getVendorName();//厂商名称
                Integer droneInfoId = ubisysWhiteList.getDroneInfoId();
                if (StringUtils.isEmpty(droneInfoId.toString())) {
                    return AjaxResponse.failed("droneInfoId不能为空");
                }
                UbisysDroneInfo ubisysDroneInfo = new UbisysDroneInfo();
                //状态：踢出白名单
                ubisysDroneInfo.setId(droneInfoId);
                ubisysDroneInfo.setVendorName(vendorName);
                ubisysDroneInfo.setUbDroneUseWay(ubDroneUseWay);
                ubisysDroneInfo.setSerialNumber(serialNumber);
                ubisysDroneInfo.setEndTime(endTime);
                ubisysDroneInfo.setStartTime(startTime);
                ubisysDroneInfo.setChipNumber(chipNumber);
                ubisysDroneInfo.setCreateTime(createTime);
                ubisysDroneInfo.setCreateBy(createBy);
                ubisysDroneInfo.setUbDroneStatus(4);
                ubisysDroneInfo.setUpdateBy("admin");
                ubisysDroneInfo.setUpdateTime(date);
                UbisysDroneInfo upUbisysDroneInfo = ubisysDroneInfoService.update(ubisysDroneInfo);
                if (upUbisysDroneInfo != null) {
                    UbisysWhiteList deup = new UbisysWhiteList();
                    deup.setId(id);
                    ubisysWhiteListService.delete(deup);
                    return AjaxResponse.success("一切正常");
                }
                return AjaxResponse.failed("修改状态失败");

            }


            return AjaxResponse.failed("存入白名单列表时失败");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return AjaxResponse.failed("未知错误");
        }
    }


    /**
     * 查询待认证无人机登记信息
     *
     * @return
     * @author Jhon Davis
     * 192.168.1.36:8000/drone/ubisysWhiteList/queryWhiteList
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/queryWhiteList", method = RequestMethod.POST)
    public AjaxResponse queryWhiteList(@ModelAttribute PageVo pageVo, HttpServletRequest request) {
        try {
            /* 前端获取查询数据 */
            String serialNumber = request.getParameter("serialNumber");
            EntityWrapper<UbisysWhiteList> entityWrapper = new EntityWrapper<UbisysWhiteList>();
            if(StringUtils.isNotEmpty(serialNumber)){
                entityWrapper.eq("serial_number", serialNumber);
            }
            Page<UbisysWhiteList> page = new Page<UbisysWhiteList>(1, 10);
            Page<UbisysWhiteList> uwPage = ubWhiteListService.selectPage(page, entityWrapper);
            AjaxResponse response = AjaxResponse.success();
            List<UbisysWhiteList> list=uwPage.getRecords();
            response.setRows(list);
            response.setTotal((int) list.size());
            log.info(response.toString());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResponse.failed("未知错误");
        }
    }

}
