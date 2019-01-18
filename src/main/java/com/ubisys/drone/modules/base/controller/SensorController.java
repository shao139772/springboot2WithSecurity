package com.ubisys.drone.modules.base.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ubisys.drone.basetemplate.DroneBaseController;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.common.utils.LZUtils;
import com.ubisys.drone.common.vo.PageVo;
import com.ubisys.drone.common.utils.PageUtil;
import com.ubisys.drone.modules.base.entity.Sensor;
import com.ubisys.drone.modules.base.service.SensorService;
import com.ubisys.drone.modules.base.service.mybatis.ISensorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author miaomiao
 */
@Slf4j
@RestController
@Api(description = "站点管理接口")
@RequestMapping("/drone/sensor")
@Transactional
public class SensorController extends DroneBaseController<Sensor, Integer> {

    @Autowired
    private SensorService sensorService;

    @Override
    public SensorService getService() {
        return sensorService;
    }

    @Autowired
    private ISensorService iSensorService;

    @Autowired
    private LZUtils lzUtils;

    /**
     * 新增站点信息
     *
     * @return
     * @author gxt
     * @date 2018-12-19
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/getAddSensorInfo", method = RequestMethod.POST)
    @ApiOperation(value = "新增站点")
    public AjaxResponse getAddSensorInfo(@ApiParam("站点名称") @RequestParam String name,
                                         @ApiParam("站点编号") @RequestParam String code,
                                         @ApiParam("站点IP") @RequestParam String ip,
                                         @ApiParam("生产厂家") @RequestParam(required = false) String supplier,
                                         @ApiParam("负责人") @RequestParam(required = false) String person,
                                         @ApiParam("备注") @RequestParam(required = false) String remarks) {
        try {
            /* 统一时间 */
            Date date = new Date();
            /* 创建实体类 */
            Sensor entity = new Sensor();
            /* 放入前台获取到的数据 */
            entity.setName(name);
            entity.setCode(code);
            entity.setIp(ip);
            entity.setSupplier(supplier);
            entity.setPerson(person);
            entity.setRemarks(remarks);
            /* 操作人信息（暂时写死） */
            entity.setCreateBy("admin");
            entity.setUpdateBy("admin");
            /* 放入时间 */
            entity.setCreateTime(date);
            entity.setUpdateTime(date);
            entity.setNetworkingTime(date);
            boolean flag = false;
            /* 判断是否存入数据库 */
            List<Sensor> sensorList = iSensorService.selectList(Condition.create().eq("code", code));
            if(sensorList.size() == 0){
                /* 存入数据库 */
                Sensor sensor = sensorService.save(entity);
                if (sensor != null) {
                    flag = true;
                }
            }else{
                return AjaxResponse.failed("该站点已添加");
            }
            return AjaxResponse.success(flag);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return AjaxResponse.failed("未知错误");
        }
    }

    /**
     * 删除站点信息
     *
     * @return
     * @author gxt
     * @date 2018-12-19
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/getDelSensorInfo", method = RequestMethod.POST)
    @ApiOperation(value = "删除站点")
    public AjaxResponse getDelSensorInfo(@ApiParam("站点IDS") @RequestParam String ids) {
        try {
            String[] id = ids.split(",");
            List<Sensor> sensorList = new ArrayList<Sensor>();
            for (int i = 0; i < id.length; i++) {
                Sensor sensor = new Sensor();
                sensor.setId(Integer.parseInt(id[i]));
                sensorList.add(sensor);
            }
            Iterable<Sensor> sensorIterator = sensorList;
            sensorService.delete(sensorIterator);
            return AjaxResponse.success();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return AjaxResponse.failed("未知错误");
        }
    }

    /**
     * 修改站点信息
     *
     * @return
     * @author gxt
     * @date 2018-12-19
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/getUpdSensorInfo", method = RequestMethod.POST)
    @ApiOperation(value = "修改站点")
    public AjaxResponse getUpdSensorInfo(@ApiParam("站点信息") @RequestParam String info) {
        try {
            /* 统一时间 */
            Date date = new Date();
            Sensor entity = JSONObject.parseObject(info, Sensor.class);
            /* 放入时间 */
            entity.setUpdateTime(date);
            Sensor sensor = sensorService.update(entity);
            boolean flag = false;
            if (sensor != null) {
                flag = true;
            }
            return AjaxResponse.success(flag);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return AjaxResponse.failed("未知错误");
        }
    }


    /**
     * 查询站点信息
     *
     * @return
     * @author gxt
     * @date 2018-12-19
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/getQuerySensorInfo", method = RequestMethod.POST)
    @ApiOperation(value = "查询站点")
    public AjaxResponse getQuerySensorInfo(@ApiParam("站点编码") @RequestParam(required = false) String code,
                                           @ApiParam("站点名称") @RequestParam(required = false) String name,
                                           @ApiParam("分页信息") @ModelAttribute PageVo pageVo, HttpServletRequest request) {
        try {
            EntityWrapper<Sensor> entityWrapper = new EntityWrapper<Sensor>();
            if(StringUtils.isNotBlank(code)){
                entityWrapper.eq("code", code);
            }
            if(StringUtils.isNotBlank(name)){
                entityWrapper.eq("name", name);
            }
            AjaxResponse ajaxResponse = new AjaxResponse();
            if(pageVo.getPageNumber() != 0 && pageVo.getPageSize() != 0){
                Page<Sensor> page = new Page<Sensor>(pageVo.getPageNumber(),pageVo.getPageSize());
                Page<Sensor> sensorPage = iSensorService.selectPage(page, entityWrapper);
                ajaxResponse.setTotal((int)sensorPage.getTotal());
                ajaxResponse.setRows(sensorPage.getRecords());
            }else{
                List<Sensor> sensorList = iSensorService.selectList( entityWrapper);
                ajaxResponse.setRows(sensorList);
            }
            ajaxResponse.setCode(200);
            ajaxResponse.setMsg("ok");

            return ajaxResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResponse.failed("未知错误");
        }
    }

    /**
     * 刷新站点状态
     *
     * @return
     * @author gxt
     * @date 2018-12-19
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/getRefreshSensorState", method = RequestMethod.POST)
    @ApiOperation(value = "刷新状态")
    public AjaxResponse getRefreshSensorState() {
        try {
            return lzUtils.querySensor();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResponse.failed("未知错误");
        }
    }
}
