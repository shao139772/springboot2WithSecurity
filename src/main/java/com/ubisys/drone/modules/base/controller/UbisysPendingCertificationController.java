package com.ubisys.drone.modules.base.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ubisys.drone.basetemplate.DroneBaseController;
import com.ubisys.drone.common.utils.DateUtils;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.common.utils.JsonUtils;
import com.ubisys.drone.common.vo.PageVo;
import com.ubisys.drone.modules.base.entity.UbisysDroneInfo;
import com.ubisys.drone.modules.base.entity.UbisysPendingCertification;
import com.ubisys.drone.modules.base.entity.UbisysWhiteList;
import com.ubisys.drone.modules.base.service.UbisysDroneInfoService;
import com.ubisys.drone.modules.base.service.UbisysPendingCertificationService;
import com.ubisys.drone.modules.base.service.UbisysWhiteListService;
import com.ubisys.drone.modules.base.service.mybatis.UbPendingCertificationService;
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author Jhon Davis
 */
@Slf4j
@RestController
@Api(description = "Ubisys无人机待认证管理接口")
@RequestMapping("/ubisysDrone/ubisysPendingCertification")
@Transactional
public class UbisysPendingCertificationController extends DroneBaseController<UbisysPendingCertification, Integer> {

    @Autowired
    private UbisysPendingCertificationService ubisysPendingCertificationService;

    @Autowired
    private UbPendingCertificationService ubPendingCertificationService;

    @Autowired
    private UbisysWhiteListService ubisysWhiteListService;

    @Autowired
    private UbisysDroneInfoService ubisysDroneInfoService;

    @Override
    public UbisysPendingCertificationService getService() {
        return ubisysPendingCertificationService;
    }

    /**
     * 查询待认证无人机登记信息
     *
     * @return
     * @author Jhon Davis
     * 192.168.1.36:8000/ubisysDrone/ubisysPendingCertification/queryPendingCertification
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/queryPendingCertification", method = RequestMethod.POST)
    public AjaxResponse queryPendingCertification(@ModelAttribute PageVo pageVo, HttpServletRequest request) {
        try {
            /* 前端获取查询数据 */
            String serialNumber = request.getParameter("serialNumber");
            EntityWrapper<UbisysPendingCertification> entityWrapper = new EntityWrapper<UbisysPendingCertification>();
            if (StringUtils.isNotEmpty(serialNumber)) {
                entityWrapper.eq("serial_number", serialNumber);
            }
            Page<UbisysPendingCertification> page = new Page<UbisysPendingCertification>(1, 10);
            Page<UbisysPendingCertification> upcPage = ubPendingCertificationService.selectPage(page, entityWrapper);
            AjaxResponse response = AjaxResponse.success();
            List<UbisysPendingCertification> list = upcPage.getRecords();
            response.setRows(list);
            response.setTotal((int) list.size());
            log.info(response.toString());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResponse.failed("未知错误");
        }
    }

    /**
     * 认证成功
     *
     * @return
     * @author Jhon Davis
     * @date 2018-12-25
     * 192.168.1.36:8000/ubisysDrone/ubisysPendingCertification/successfulCertification
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/successfulCertification", method = RequestMethod.POST)
    public AjaxResponse successfulCertification(@RequestParam String info) {
        try {
            if (StringUtils.isBlank(info)) {
                return AjaxResponse.failed("滚");
            }
            UbisysPendingCertification ubisysPendingCertification = JsonUtils.jsonToPojo(info, UbisysPendingCertification.class);
            Integer id = ubisysPendingCertification.getId();
            Date registrationTime = ubisysPendingCertification.getRegistrationTime();//登记时间
            String serialNumber = ubisysPendingCertification.getSerialNumber();//序列号
            String chipNumber = ubisysPendingCertification.getChipNumber();//芯片号
            Date startTime = ubisysPendingCertification.getStartTime();//有效开始时间
            Date endTime = ubisysPendingCertification.getStartTime();//有效截止时间
            Date createTime = ubisysPendingCertification.getCreateTime();
            String createBy = ubisysPendingCertification.getCreateBy();
            String ubDroneUseWay = ubisysPendingCertification.getUbDroneUseWay();//用途
            //String ubDroneStatus = request.getParameter("ubDroneStatus");//状态
            String vendorName = ubisysPendingCertification.getVendorName();//厂商名称
            Integer droneInfoId = ubisysPendingCertification.getDroneInfoId();
            if (droneInfoId.toString().equals("") || droneInfoId == null) {
                return AjaxResponse.failed("droneInfoId不能为空");
            }
                /* 统一时间 */
            Date date = new Date();
                /* 创建实体类 */
            UbisysWhiteList entity = new UbisysWhiteList();
                /* 放入前台获取到的数据 */
            entity.setRegistrationTime(registrationTime);
            entity.setSerialNumber(serialNumber);
            entity.setChipNumber(chipNumber);
            entity.setStartTime(startTime);
            entity.setEndTime(endTime);
            entity.setUbDroneUseWay(ubDroneUseWay);
            entity.setVendorName(vendorName);
            entity.setUpdateBy("admin");
            entity.setCreateBy(createBy);
            entity.setDroneInfoId(droneInfoId);
                 /* 放入时间 */

            entity.setCreateTime(date);
            entity.setUpdateTime(date);
                /* 存入数据库 */
            UbisysWhiteList ubisysWhiteList = ubisysWhiteListService.save(entity);


            if (ubisysWhiteList != null) {

                UbisysDroneInfo ubisysDroneInfo = new UbisysDroneInfo();
                ubisysDroneInfo.setId(droneInfoId);
                ubisysDroneInfo.setVendorName(vendorName);
                ubisysDroneInfo.setUbDroneUseWay(ubDroneUseWay);
                ubisysDroneInfo.setSerialNumber(serialNumber);
                ubisysDroneInfo.setEndTime(endTime);
                ubisysDroneInfo.setStartTime(startTime);
                ubisysDroneInfo.setChipNumber(chipNumber);
                ubisysDroneInfo.setCreateTime(createTime);
                ubisysDroneInfo.setCreateBy(createBy);
                ubisysDroneInfo.setUbDroneStatus(2);
                ubisysDroneInfo.setUpdateBy("admin");
                ubisysDroneInfo.setUpdateTime(date);
                UbisysDroneInfo upUbisysDroneInfo = ubisysDroneInfoService.update(ubisysDroneInfo);
                if (upUbisysDroneInfo != null) {
                    UbisysPendingCertification deup = new UbisysPendingCertification();
                    deup.setId(id);
                    ubisysPendingCertificationService.delete(deup);
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
     * 认证失败
     *
     * @return
     * @author Jhon Davis
     * @date 2018-12-25
     * 192.168.1.36:8000/ubisysDrone/ubisysPendingCertification/failureCertification
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/failureCertification", method = RequestMethod.POST)
    public AjaxResponse failureCertification(@RequestParam String info) {
        try {
            JSONArray jsonArray = JSON.parseArray(info);
            List<UbisysPendingCertification> ll = JSONObject.parseArray(jsonArray.toJSONString(), UbisysPendingCertification.class);
            for (UbisysPendingCertification ubisysPendingCertification : ll) {
                Integer id = ubisysPendingCertification.getId();
                Date registrationTime = ubisysPendingCertification.getRegistrationTime();//登记时间
                String serialNumber = ubisysPendingCertification.getSerialNumber();//序列号
                String chipNumber = ubisysPendingCertification.getChipNumber();//芯片号
                Date startTime = ubisysPendingCertification.getStartTime();//有效开始时间
                Date endTime = ubisysPendingCertification.getStartTime();//有效截止时间
                Date createTime = ubisysPendingCertification.getCreateTime();
                String createBy = ubisysPendingCertification.getCreateBy();
                String ubDroneUseWay = ubisysPendingCertification.getUbDroneUseWay();//用途
                //String ubDroneStatus = request.getParameter("ubDroneStatus");//状态
                String vendorName = ubisysPendingCertification.getVendorName();//厂商名称
                Integer droneInfoId = ubisysPendingCertification.getDroneInfoId();
                Date date = new Date();
                if (StringUtils.isEmpty(droneInfoId.toString())) {
                    return AjaxResponse.failed("droneInfoId不能为空");
                }
                UbisysDroneInfo ubisysDroneInfo = new UbisysDroneInfo();
                ubisysDroneInfo.setId(droneInfoId);
                //状态：认证失败
                ubisysDroneInfo.setUbDroneStatus(3);
                ubisysDroneInfo.setRegistrationTime(registrationTime);
                ubisysDroneInfo.setVendorName(vendorName);
                ubisysDroneInfo.setUbDroneUseWay(ubDroneUseWay);
                ubisysDroneInfo.setSerialNumber(serialNumber);
                ubisysDroneInfo.setEndTime(endTime);
                ubisysDroneInfo.setStartTime(startTime);
                ubisysDroneInfo.setChipNumber(chipNumber);
                ubisysDroneInfo.setCreateTime(createTime);
                ubisysDroneInfo.setCreateBy(createBy);
                ubisysDroneInfo.setUpdateBy("admin");
                ubisysDroneInfo.setUpdateTime(date);

                UbisysDroneInfo upUbisysDroneInfo = ubisysDroneInfoService.update(ubisysDroneInfo);
                if (upUbisysDroneInfo != null) {
                    UbisysPendingCertification deup = new UbisysPendingCertification();
                    deup.setId(id);
                    ubisysPendingCertificationService.delete(deup);
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


}
