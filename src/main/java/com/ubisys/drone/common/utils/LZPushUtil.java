package com.ubisys.drone.common.utils;

import com.alibaba.fastjson.JSON;
import com.ubisys.drone.common.constant.WebSocketConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: 瑞鹰无人机</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/21 14:24
 */
@Component
@Slf4j
public class LZPushUtil {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public void pushDroneToFront() {
        log.info("   准备向前端推送无人机      ");
     /*   List<Drone> drones = new ArrayList<Drone>();
        Set<Object> keys = stringRedisTemplate.opsForHash().keys(DroneConstant.LZ_DRONE);
        for (Iterator<Object> iterator = keys.iterator(); iterator.hasNext(); ) {
            Object temp = stringRedisTemplate.opsForHash().get(DroneConstant.LZ_DRONE, String.valueOf(iterator.next()));
            drones.add(JSON.parseObject(String.valueOf(temp), Drone.class));
        }*/
        Map<String, Object> webmap = new HashMap<String, Object>();
        webmap.put("status", "0");
        webmap.put("msg", "alarm drone");
        webmap.put("code", WebSocketConstant.FIND_DRONE);
     //   webmap.put("data", drones);
        //向前端推送
        WebSocketUserUtils.sendDroneToCust(JSON.toJSONString(webmap));

    }


}
