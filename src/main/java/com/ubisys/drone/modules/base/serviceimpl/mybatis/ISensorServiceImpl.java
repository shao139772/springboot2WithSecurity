package com.ubisys.drone.modules.base.serviceimpl.mybatis;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.common.utils.DroneConvertUtil;
import com.ubisys.drone.modules.base.dao.mapper.SensorMapper;
import com.ubisys.drone.modules.base.entity.Drone;
import com.ubisys.drone.modules.base.entity.Sensor;
import com.ubisys.drone.modules.base.model.DroneThirdFloor;
import com.ubisys.drone.modules.base.model.SensorFloor;
import com.ubisys.drone.modules.base.service.mybatis.ISensorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>Title: 无人机接口实现</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/17 17:59
 */
@Service
@Slf4j
public class ISensorServiceImpl extends ServiceImpl<SensorMapper, Sensor> implements ISensorService {

    @Autowired
    private SensorMapper sensorMapper;

    @Override
    public AjaxResponse updSensorByLz(List<SensorFloor> ll) {
        List<Sensor> sensorsList = new ArrayList<Sensor>();
        Date date = new Date();
        for (int i = 0; i < ll.size(); i++) {
            //目前  directions peer_directions  暂时不用，不作处理
            SensorFloor sensorFloor = ll.get(i);
            log.info(" 源Sensor为 " + sensorFloor.toString());
            Sensor sensor = new Sensor();


            List<Sensor> sensorList = sensorMapper.selectList(Condition.create().eq("code", sensorFloor.getId()));
            if (sensorList.size() > 0) {
                sensor.setId(sensorList.get(0).getId());
                sensor.setCode(sensorFloor.getId());
                sensor.setLat((double) (sensorFloor.getConfig().get(0).getGeo_location().get(0).getLat()));
                sensor.setLng((double)(sensorFloor.getConfig().get(0).getGeo_location().get(0).getLng()));
                sensor.setUpdateBy("admin");
                sensor.setUpdateTime(date);
                sensorsList.add(sensor);
            } else {
                log.info(" 该站点未在平台注册~~~~");
            }
        }
        if (CollectionUtils.isEmpty(sensorsList)) {
            return AjaxResponse.success(" 木有站点~~~~ ");
        }
        Boolean result = updateBatchById(sensorsList);
        if (result) {
            return AjaxResponse.success();
        }
        return AjaxResponse.failed(" 批量处理站点信息错误！ ");
    }

    @Override
    public AjaxResponse updSensorByLz(String infos) {
        String[] info = infos.split(",");
        int networkState = 0;
        String temperature = null;
        if(info.length > 3){//在线
            networkState = 1;
            temperature = infos.substring(infos.indexOf("temperature") + 11, infos.indexOf("temperature") + 16).replace(":", "");
        }
        //找出全部站点
        List<Sensor> sensorList = sensorMapper.selectList(null);
        for (Sensor sensor : sensorList){
            sensor.setTemperature(temperature);
            sensor.setNetworkState(networkState);
        }
        Boolean result = updateBatchById(sensorList);
        if (result) {
            return AjaxResponse.success();
        }
        return AjaxResponse.failed(" 批量处理站点信息错误！ ");
    }
}
