package com.ubisys.drone.modules.base.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ubisys.drone.basetemplate.DroneBaseController;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.common.utils.DateUtils;
import com.ubisys.drone.common.vo.PageVo;
import com.ubisys.drone.modules.base.entity.UbisysDrone;
import com.ubisys.drone.modules.base.entity.UbisysDroneInfo;
import com.ubisys.drone.modules.base.entity.UbisysPendingCertification;
import com.ubisys.drone.modules.base.service.UbisysDroneInfoService;
import com.ubisys.drone.modules.base.service.UbisysPendingCertificationService;
import com.ubisys.drone.modules.base.service.mybatis.UbDroneInfoService;
import com.ubisys.drone.modules.base.service.mybatis.UbDroneService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * @author Jhon Davis
 */
@Slf4j
@RestController
@Api(description = "历史信息报警")
@RequestMapping("/ubisysDroneHistory/alarm")
@Transactional
public class UbisysDroneController {

    @Autowired
    private UbDroneService ubDroneService;


    /**
     * 查询报警信息
     *
     * @return
     * @author Jhon Davis
     * @date 2018-12-27
     * 192.168.1.36:8000/ubisysDroneHistory/alarm/queryAlarmList
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/queryAlarmList", method = RequestMethod.POST)
    public AjaxResponse queryDroneList(HttpServletRequest request, @ModelAttribute PageVo page) {
        try {

            String serialNumber = request.getParameter("serialNumber");
            String pageNum = request.getParameter("page");
            String pageSize = request.getParameter("limit");
            String time = request.getParameter("time");

            log.info(serialNumber + "serialNumber" + pageNum + "pageNum" + pageSize + "pageSize");
            return ubDroneService.findDeviceFaultList(page.getPageNumber(), page.getPageSize(), serialNumber, time);



























            /*if(StringUtils.isNotEmpty(pageNum0)){
                Integer pageNum = Integer.valueOf(pageNum0);
               // List<UbisysDrone> list =ubDroneService.findDeviceFaultList(serialNumber);
                Page<UbisysDrone> page = new Page<UbisysDrone>(pageNum, 10);
               *//* log.info("list.toString()"+list.toString());
                log.info("page.toString()"+page.toString());
                log.info("String.valueOf((Wrapper<UbisysDrone>) list)"+String.valueOf((Wrapper<UbisysDrone>) list));*//*
                //Page<UbisysDrone> result = ubDroneService.selectPage(page, (Wrapper<UbisysDrone>) list);
                Page<UbisysDrone> result = ubDroneService.selectPage(page);
                log.info(result.toString());
                AjaxResponse response = AjaxResponse.success();
                response.setTotal((int) result.getTotal());
                response.setRows(result.getRecords());
                //总页码数量
                response.setObj((int)result.getPages());
                return response;
            }else{
                Integer pageNum = 1;
                //List<UbisysDrone> list =ubDroneService.findDeviceFaultList(serialNumber);
                Page<UbisysDrone> page = new Page<UbisysDrone>(pageNum, 10);
                //Page<UbisysDrone> result = ubDroneService.selectPage(page, (Wrapper<UbisysDrone>) list);
                Page<UbisysDrone> result = ubDroneService.selectPage(page);
                log.info(result.toString());
                AjaxResponse response = AjaxResponse.success();
                response.setTotal((int) result.getTotal());
                response.setRows(result.getRecords());
                //总页码数量
                response.setObj((int)result.getPages());
                return response;
            }*/
            //




            /*// 分页处理
            JSONArray pageArray = new JSONArray();
            for (int i = 1; i <= result.getPages(); i++) {
                pageArray.add(i);
                data.put("pageArray", pageArray);
            }
            data.put("isHasNextPage", page.hasNext());
            data.put("isHasPreviousPage", page.hasPrevious());*/

        } catch (Exception e) {
            e.printStackTrace();

            return AjaxResponse.failed("未知错误");
        }
    }

    /**
     * 删除报警信息
     *
     * @return
     * @author Jhon Davis
     * @date 2018-12-27
     * /ubisysDroneHistory/alarm/delAlarmList
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/delAlarmList", method = RequestMethod.POST)
    public AjaxResponse delAlarmList(HttpServletRequest request) {
        try {

            String ids = request.getParameter("ids");
            if (ids.isEmpty()) {
                return AjaxResponse.failed("ids不能为空");
            }
            JSONArray jsonArray = JSON.parseArray(ids);
            List<UbisysDrone> ll = JSONObject.parseArray(jsonArray.toJSONString(), UbisysDrone.class);
            for (UbisysDrone ubisysDrone : ll) {
                Integer id = ubisysDrone.getLzdroneId();
                // 删除过滤条件
                Boolean result = ubDroneService.deleteById(id);
                // Boolean result = ubDroneService.delete(new EntityWrapper<UbisysDrone>());
                if (result) {
                    return AjaxResponse.success();
                }
            }

            return AjaxResponse.failed("删除失败未找到id");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return AjaxResponse.failed("未知错误");
        }
    }

}
