package com.ubisys.drone.modules.base.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ubisys.drone.basetemplate.DroneBaseController;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.common.utils.DateUtils;
import com.ubisys.drone.common.utils.SecurityUtil;
import com.ubisys.drone.common.vo.PageVo;
import com.ubisys.drone.modules.base.entity.UbisysDroneInfo;
import com.ubisys.drone.modules.base.entity.UbisysPendingCertification;
import com.ubisys.drone.modules.base.service.UbisysDroneInfoService;
import com.ubisys.drone.modules.base.service.UbisysPendingCertificationService;
import com.ubisys.drone.modules.base.service.mybatis.UbDroneInfoService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author Jhon Davis
 */
@Slf4j
@RestController
@Api(description = "Ubisys无人机信息登记管理接口")
@RequestMapping("/ubisysDrone/droneReg")
@Transactional
public class UbisysDroneInfoController extends DroneBaseController<UbisysDroneInfo, Integer> {

    @Autowired
    private UbisysDroneInfoService ubisysDroneInfoService;

    @Autowired
    private UbDroneInfoService ubDroneInfoService;

    @Override
    public UbisysDroneInfoService getService() {
        return ubisysDroneInfoService;
    }

    @Autowired
    private UbisysPendingCertificationService ubisysPendingCertificationService;
    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 新增无人机信息
     *
     * @return
     * @author Jhon Davis
     * @date 2018-12-21
     * 192.168.1.36:8000/ubisysDrone/droneReg/addDroneInfo
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/addDroneInfo", method = RequestMethod.POST)
    public AjaxResponse addDroneInfo(HttpServletRequest request) {
        try {
            /* 从前端获取数据 */
            //String registrationTime = request.getParameter("registrationTime");//登记日期
            String serialNumber = request.getParameter("serialNumber");//序列号
            String chipNumber = request.getParameter("chipNumber");//芯片号
            String startTime = request.getParameter("startTime");//有效开始时间
            String endTime = request.getParameter("endTime");//有效截止时间
            String ubDroneUseWay = request.getParameter("ubDroneUseWay");//用途
            String vendorName = request.getParameter("vendorName");//厂商名称
            /* 统一时间 */
            Date date = new Date();
            /* 创建实体类 */
            UbisysDroneInfo entity = new UbisysDroneInfo();
            /* 放入前台获取到的数据 */
            entity.setRegistrationTime(DateUtils.getDate());
            entity.setSerialNumber(serialNumber);
            entity.setChipNumber(chipNumber);
            entity.setStartTime(DateUtils.str2Date(startTime, DateUtils.date_sdf));
            entity.setEndTime(DateUtils.str2Date(endTime, DateUtils.date_sdf));
            entity.setUbDroneStatus(0);
            entity.setUbDroneUseWay(ubDroneUseWay);
            entity.setVendorName(vendorName);
            entity.setCreateBy(securityUtil.getCurrUser().toString());
            entity.setUpdateBy(securityUtil.getCurrUser().toString());
            /* 放入时间 */
            entity.setCreateTime(date);
            entity.setUpdateTime(date);
            /* 存入数据库 */
            UbisysDroneInfo ubisysDroneInfo = ubisysDroneInfoService.save(entity);
            boolean flag = false;
            if (ubisysDroneInfo != null) {
                flag = true;
                return AjaxResponse.success(flag);
            }
            return AjaxResponse.success(flag);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return AjaxResponse.failed("未知错误");
        }
    }

    /**
     * 修改无人机登记信息
     *
     * @return
     * @author Jhon Davis
     * @date 2018-12-21
     * 192.168.1.36:8000/ubisysDrone/droneReg/updDroneInfo
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/updDroneInfo", method = RequestMethod.POST)
    public AjaxResponse updDroneInfo(HttpServletRequest request) {
        try {
            /* 前端获取数据 */
            String id = request.getParameter("id");
            String serialNumber = request.getParameter("serialNumber");//序列号
            String chipNumber = request.getParameter("chipNumber");//芯片号
            String startTime = request.getParameter("startTime");//有效开始时间
            String endTime = request.getParameter("endTime");//有效截止时间
            String ubDroneUseWay = request.getParameter("ubDroneUseWay");//用途
            String ubDroneStatus = request.getParameter("ubDroneStatus");//状态
            String createBy = request.getParameter("createBy");
            String createTime = request.getParameter("createTime");
            String registrationTime = request.getParameter("registrationTime");
            if (ubDroneStatus == null || ubDroneStatus.equals("")) {
                return AjaxResponse.failed("ubDroneStatus不能为空");
            }
            String vendorName = request.getParameter("vendorName");//厂商名称
             /* 统一时间 */
            Date date = new Date();
            /* 创建实体类 */
            UbisysDroneInfo entity = new UbisysDroneInfo();
            /* 放入前台获取到的数据 */
            entity.setId(Integer.valueOf(id));
            entity.setRegistrationTime(DateUtils.str2Date(registrationTime, DateUtils.date_sdf));
            entity.setSerialNumber(serialNumber);
            entity.setChipNumber(chipNumber);
            entity.setStartTime(DateUtils.str2Date(startTime, DateUtils.date_sdf));
            entity.setEndTime(DateUtils.str2Date(endTime, DateUtils.date_sdf));
            entity.setUbDroneStatus(Integer.valueOf(ubDroneStatus));
            entity.setUbDroneUseWay(ubDroneUseWay);
            entity.setVendorName(vendorName);
            entity.setCreateBy(createBy);
            entity.setUpdateBy(securityUtil.getCurrUser().getUsername().toString());
            /* 放入时间 */
            entity.setCreateTime(DateUtils.str2Date(createTime, DateUtils.date_sdf));
            entity.setUpdateTime(date);
            /* 存入数据库 */

            UbisysDroneInfo ubisysDroneInfo = ubisysDroneInfoService.update(entity);
            log.info("ubisysDroneInfo"+ubisysDroneInfo);
            boolean flag = false;
            //ubisysDroneInfoService != null
            if (ubisysDroneInfo != null) {
                flag = true;
                return AjaxResponse.success();
            }
            return AjaxResponse.failed("保存失败");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return AjaxResponse.failed("未知错误");
        }
    }


    /**
     * 查询无人机登记信息
     *
     * @return
     * @author Jhon Davis
     * @date 2018-12-21
     * 192.168.1.36:8000/ubisysDrone/droneReg/queryDroneInfo
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/queryDroneInfo", method = RequestMethod.POST)
    public AjaxResponse queryDroneInfo(@ModelAttribute PageVo pageVo, HttpServletRequest request) {

        try {
            /* 前端获取查询数据 */
            String serialNumber = request.getParameter("serialNumber");
            log.info(" serialNumber " + serialNumber);
            EntityWrapper<UbisysDroneInfo> entityWrapper = new EntityWrapper<UbisysDroneInfo>();
            if (StringUtils.isNotEmpty(serialNumber)) {
                entityWrapper.eq("serial_number", serialNumber);
            }

            Page<UbisysDroneInfo> page = new Page<UbisysDroneInfo>(1, 10);
            Page<UbisysDroneInfo> ubisysDroneInfoPage = ubDroneInfoService.selectPage(page, entityWrapper);
            AjaxResponse response = AjaxResponse.success();
            List<UbisysDroneInfo> list = ubisysDroneInfoPage.getRecords();
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
     * 删除无人机登记信息
     *
     * @return
     * @author Jhon Davis
     * @date 2018-12-24
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/delDroneInfo", method = RequestMethod.POST)
    public AjaxResponse delDroneInfo(HttpServletRequest request) {
        try {
        /* 前端获取数据 */
            String ids = request.getParameter("ids");
            String ubDroneStatus = request.getParameter("ubDroneStatus");
            if(ubDroneStatus.equals("1")||ubDroneStatus.equals("2")){
                return AjaxResponse.failed("滚");
            }
            String[] id = ids.split(",");
            List<UbisysDroneInfo> ubisysDroneInfoList = new ArrayList<UbisysDroneInfo>();
            for (int i = 0; i < id.length; i++) {
                UbisysDroneInfo ubisysDroneInfo = new UbisysDroneInfo();
                ubisysDroneInfo.setId(Integer.parseInt(id[i]));
                ubisysDroneInfoList.add(ubisysDroneInfo);
            }
            Iterable<UbisysDroneInfo> ubisysDroneInfoIterator = ubisysDroneInfoList;
            ubisysDroneInfoService.delete(ubisysDroneInfoIterator);
            return AjaxResponse.success("杨超说随便删，有问题找杨超");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return AjaxResponse.failed("未知错误");
        }
    }


    /**
     * 提交审核白名单
     *
     * @return
     * @author Jhon Davis
     * @date 2018-12-25
     * 192.168.1.36:8000/ubisysDrone/droneReg/submitReview
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/submitReview", method = RequestMethod.POST)
    public AjaxResponse submitReview(HttpServletRequest request) {
        try {
            /* 从前端获取数据 */
            String id = request.getParameter("id");
            if (id == null || id.equals("")) {
                return AjaxResponse.failed("id不能为空");
            }
            String registrationTime = request.getParameter("registrationTime");//登记时间
            String serialNumber = request.getParameter("serialNumber");//序列号
            String chipNumber = request.getParameter("chipNumber");//芯片号
            String startTime = request.getParameter("startTime");//有效开始时间
            String endTime = request.getParameter("endTime");//有效截止时间
            String ubDroneUseWay = request.getParameter("ubDroneUseWay");//用途
            String ubDroneStatus = request.getParameter("ubDroneStatus");//状态
            String vendorName = request.getParameter("vendorName");//厂商名称
            String createBy = request.getParameter("creatBy");
            String createTime = request.getParameter("createTime");
            if(ubDroneStatus.equals("1")||ubDroneStatus.equals("2")){
                return AjaxResponse.failed("滚");
            }
            /* 统一时间 */
            Date date = new Date();
            /* 创建实体类 */
            UbisysPendingCertification entity = new UbisysPendingCertification();
            /* 放入前台获取到的数据 */
            entity.setRegistrationTime(DateUtils.str2Date(registrationTime, DateUtils.date_sdf));
            entity.setSerialNumber(serialNumber);
            entity.setChipNumber(chipNumber);
            entity.setStartTime(DateUtils.str2Date(startTime, DateUtils.date_sdf));
            entity.setEndTime(DateUtils.str2Date(endTime, DateUtils.date_sdf));
            entity.setUbDroneUseWay(ubDroneUseWay);
            entity.setVendorName(vendorName);
            log.info("username  " + securityUtil.getCurrUser().getUsername());
            entity.setUpdateBy(securityUtil.getCurrUser().getUsername());
            entity.setCreateBy(createBy);
            entity.setDroneInfoId(Integer.valueOf(id));
            /* 放入时间 */

            entity.setCreateTime(DateUtils.str2Date(createTime, DateUtils.date_sdf));
            entity.setUpdateTime(date);
            /* 存入数据库 */
            UbisysPendingCertification ubisysPendingCertification = ubisysPendingCertificationService.save(entity);
            //boolean flag = false;
            if (ubisysPendingCertification != null) {
                UbisysDroneInfo ubisysDroneInfo = new UbisysDroneInfo();
                //根据id修改状态   状态为提交审核中
                ubisysDroneInfo.setId(Integer.valueOf(id));
                ubisysDroneInfo.setRegistrationTime(DateUtils.str2Date(registrationTime, DateUtils.date_sdf));
                ubisysDroneInfo.setCreateTime(DateUtils.str2Date(createTime, DateUtils.date_sdf));
                ubisysDroneInfo.setCreateBy(createBy);
                ubisysDroneInfo.setUbDroneStatus(1);
                ubisysDroneInfo.setUpdateBy(securityUtil.getCurrUser().getUsername());
                ubisysDroneInfo.setUpdateTime(date);
                ubisysDroneInfo.setChipNumber(chipNumber);
                ubisysDroneInfo.setStartTime(DateUtils.str2Date(startTime, DateUtils.date_sdf));
                ubisysDroneInfo.setEndTime(DateUtils.str2Date(endTime, DateUtils.date_sdf));
                ubisysDroneInfo.setSerialNumber(serialNumber);
                ubisysDroneInfo.setUbDroneUseWay(ubDroneUseWay);
                ubisysDroneInfo.setVendorName(vendorName);
                UbisysDroneInfo upUbisysDroneInfo = ubisysDroneInfoService.update(ubisysDroneInfo);
                if (upUbisysDroneInfo == null) {
                    return AjaxResponse.failed("修改状态失败");
                }
                return AjaxResponse.success("一切正常");
            }
            return AjaxResponse.failed("存入白名单列表时失败");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return AjaxResponse.failed("未知错误");
        }
    }
}
