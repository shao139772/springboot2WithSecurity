package com.ubisys.drone.common.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.ubisys.drone.modules.base.entity.Drone;
import com.ubisys.drone.modules.base.model.DroneThirdFloor;
import com.ubisys.drone.modules.base.model.SeenSensorsTemporary;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * @Auther: cw
 * @Date: 2018/11/6 15:44
 * @Description: 用于接收历正返回的数据后，转换为自有pojo
 */
public class DroneConvertUtil {

    private static Logger logger
            = LoggerFactory.getLogger(DroneConvertUtil.class);

    public static Drone convert(DroneThirdFloor droneThirdFloor) {
        Drone drone = new Drone();
        BeanUtil.copyProperties(droneThirdFloor, drone);
        logger.info(droneThirdFloor.toString());
        drone.setAttacking(droneThirdFloor.isAttacking() == true ? 1 : 2);
        drone.setCanAttack(droneThirdFloor.isCan_attack() == true ? 1 : 2);
        drone.setCanTakeover(droneThirdFloor.isCan_attack() == true ? 1 : 2);
        drone.setWhitelisted(droneThirdFloor.isWhitelisted() == true ? 1 : 2);
        String dates = droneThirdFloor.getCreatedTime().replace("T", " ").substring(0, 19);
        drone.setCreatedTime(DateUtil.parse(dates, "yyyy-MM-dd HH:mm:ss"));
        //获取发现频率
        List<SeenSensorsTemporary> ll = droneThirdFloor.getSeen_sensor();
        if (CollectionUtils.isNotEmpty(ll)) {
            drone.setDetectedFreqKhz(Long.valueOf(ll.get(0).getDetected_freq_khz()));
        }
        //如果已经被打击，需要给予默认值(被打击的状态)
        if (drone.getAttacking() == 1) {
            drone.setAttackTime(new Date());
        }
        drone.setDroneid(droneThirdFloor.getId());
        return drone;
    }


    public static void main(String[] args) {
        String dates = "2018-11-06T15:55:05.168169765+08:00";
        String datess = dates.replace("T", " ").substring(0, 19);
        System.out.println(DateUtil.parse(datess, "yyyy-MM-dd HH:mm:ss"));
    }


}
