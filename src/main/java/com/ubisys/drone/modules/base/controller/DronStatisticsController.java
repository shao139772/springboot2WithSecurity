package com.ubisys.drone.modules.base.controller;

import com.ubisys.drone.common.constant.DroneConstant;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.modules.base.entity.DronStatistics;
import com.ubisys.drone.modules.base.entity.Duration;
import com.ubisys.drone.modules.base.service.mybatis.DronStatisticsService;
import com.ubisys.drone.modules.base.service.mybatis.DurationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Jhon Davis
 */
@Api(description = "统计echarts图")
@Controller
@Slf4j
@RequestMapping("/drone/dronStatistics")
public class DronStatisticsController {

    @Autowired
    private DronStatisticsService dronStatisticsService;
    @Autowired
    private DurationService durationService;

    /**
     * 无人机品牌分布
     *
     * @return 192.168.1.36:8000/drone/dronStatistics/findDronValue
     */
    @RequestMapping(value = "/findDronValue", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    @ApiOperation(value = "无人机品牌分布")
    public AjaxResponse findDronValue() {
        try {
            List<DronStatistics> list = dronStatisticsService.findDronValue();
            return AjaxResponse.success(list);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return AjaxResponse.failed("未知错误");
        }

    }


    /**
     * 入侵时长分布
     *
     * @return 192.168.1.36:8000/drone/dronStatistics/findDuration
     */
    @RequestMapping(value = "/findDuration", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    @ApiOperation(value = "入侵时长分布")
    public AjaxResponse findDuration() {

        try {
            List<Duration> list = durationService.findDuration();
            /*String o1 = "0:00~4:00";
            String o2 = "4:00~8:00";
            String o3 = "8:00~12:00";
            String o4 = "12:00~16:00";
            String o5 = "16:00~20:00";
            String o6 = "20:00~24:00";*/
            String o1 = "0~20";
            String o2 = "20~40";
            String o3 = "40~60";
            String o4 = "60~80";
            String o5 = "80~100";
            String o6 = "100~120";
            String o7 = "大于120";
           //List<Duration> durationList = new ArrayList<>();
            Map<Integer, Duration> map = new HashMap<Integer, Duration>();
            Map<Integer, Duration> dmap = new HashMap<Integer, Duration>();
            Duration d2 = new Duration();
            for (Duration duration : list) {

                if (duration.getSerialNum().equals(o1)) {
                    Duration d1 = new Duration();
                    d1.setOrderBy(0);
                    d1.setQuantity(duration.getQuantity());
                    d1.setSerialNum(duration.getSerialNum());
                    map.put(0,d1);
                }
                if (duration.getSerialNum().equals(o2)) {
                    Duration d1 = new Duration();
                    d1.setOrderBy(1);
                    d1.setQuantity(duration.getQuantity());
                    d1.setSerialNum(duration.getSerialNum());
                    map.put(1,d1);
                }
                if (duration.getSerialNum().equals(o3)) {
                    Duration d1 = new Duration();
                    d1.setOrderBy(2);
                    d1.setQuantity(duration.getQuantity());
                    d1.setSerialNum(duration.getSerialNum());
                    //durationList.add(d1);
                    map.put(2,d1);
                }
                if (duration.getSerialNum().equals(o4)) {
                    Duration d1 = new Duration();
                    d1.setOrderBy(3);
                    d1.setQuantity(duration.getQuantity());
                    d1.setSerialNum(duration.getSerialNum());
                    map.put(3,d1);
                }

                if (duration.getSerialNum().equals(o5)) {
                    Duration d1 = new Duration();
                    d1.setOrderBy(4);
                    d1.setQuantity(duration.getQuantity());
                    d1.setSerialNum(duration.getSerialNum());
                    map.put(4,d1);
                }
                if (duration.getSerialNum().equals(o6)) {
                    Duration d1 = new Duration();
                    d1.setOrderBy(5);
                    d1.setQuantity(duration.getQuantity());
                    d1.setSerialNum(duration.getSerialNum());
                    map.put(5,d1);
                }
                if (duration.getSerialNum().equals(o7)) {
                    Duration d1 = new Duration();
                    d1.setOrderBy(6);
                    d1.setQuantity(duration.getQuantity());
                    d1.setSerialNum(duration.getSerialNum());
                    map.put(6,d1);
                }
                log.info("map---------------------" + map);

            }


            if (map.containsKey(0)){
            }else{
                Duration d1 = new Duration();
                d1.setOrderBy(0);
                d1.setQuantity(0);
                d1.setSerialNum(o1);
                map.put(0,d1);
            }

            if (map.containsKey(1)){
            }else{
                Duration d1 = new Duration();
                d1.setOrderBy(1);
                d1.setQuantity(0);
                d1.setSerialNum(o2);
                map.put(1,d1);
            }

            if (map.containsKey(2)){
            }else{
                Duration d1 = new Duration();
                d1.setOrderBy(2);
                d1.setQuantity(0);
                d1.setSerialNum(o3);
                map.put(2,d1);
            }

            if (map.containsKey(3)){
            }else{
                Duration d1 = new Duration();
                d1.setOrderBy(3);
                d1.setQuantity(0);
                d1.setSerialNum(o4);
                map.put(3,d1);
            }

            if (map.containsKey(4)){
            }else{
                Duration d1 = new Duration();
                d1.setOrderBy(4);
                d1.setQuantity(0);
                d1.setSerialNum(o5);
                map.put(4,d1);
            }

            if (map.containsKey(5)){
            }else{
                Duration d1 = new Duration();
                d1.setOrderBy(5);
                d1.setQuantity(0);
                d1.setSerialNum(o6);
                map.put(5,d1);
            }

            if (map.containsKey(6)){
            }else{
                Duration d1 = new Duration();
                d1.setOrderBy(6);
                d1.setQuantity(0);
                d1.setSerialNum(o7);
                map.put(6,d1);
            }
            AjaxResponse response = AjaxResponse.success();
            response.setRows(map);
            response.setTotal((int) map.size());
            log.info(response.toString());
            return response;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return AjaxResponse.failed("未知错误");
        }
    }

    /**
     * 时间分布统计
     *
     * @return 192.168.1.36:8000/drone/dronStatistics/findCreatTime
     */
    @RequestMapping(value = "/findCreatTime", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    @ApiOperation(value = "时间分布统计")
    public AjaxResponse findCreatTime() {
        try {
            List<Duration> list = durationService.findCreatTime();
            String o1 = "0:00~4:00";
            String o2 = "4:00~8:00";
            String o3 = "8:00~12:00";
            String o4 = "12:00~16:00";
            String o5 = "16:00~20:00";
            String o6 = "20:00~24:00";
            Map<Integer, Duration> map = new HashMap<Integer, Duration>();
            Map<Integer, Duration> dmap = new HashMap<Integer, Duration>();
            Duration d2 = new Duration();
            for (Duration duration : list) {

                if (duration.getSerialNum().equals(o1)) {
                    Duration d1 = new Duration();
                    d1.setOrderBy(0);
                    d1.setQuantity(duration.getQuantity());
                    d1.setSerialNum(duration.getSerialNum());
                    map.put(0,d1);
                }
                if (duration.getSerialNum().equals(o2)) {
                    Duration d1 = new Duration();
                    d1.setOrderBy(1);
                    d1.setQuantity(duration.getQuantity());
                    d1.setSerialNum(duration.getSerialNum());
                    map.put(1,d1);
                }
                if (duration.getSerialNum().equals(o3)) {
                    Duration d1 = new Duration();
                    d1.setOrderBy(2);
                    d1.setQuantity(duration.getQuantity());
                    d1.setSerialNum(duration.getSerialNum());
                    //durationList.add(d1);
                    map.put(2,d1);
                }
                if (duration.getSerialNum().equals(o4)) {
                    Duration d1 = new Duration();
                    d1.setOrderBy(3);
                    d1.setQuantity(duration.getQuantity());
                    d1.setSerialNum(duration.getSerialNum());
                    map.put(3,d1);
                }

                if (duration.getSerialNum().equals(o5)) {
                    Duration d1 = new Duration();
                    d1.setOrderBy(4);
                    d1.setQuantity(duration.getQuantity());
                    d1.setSerialNum(duration.getSerialNum());
                    map.put(4,d1);
                }
                if (duration.getSerialNum().equals(o6)) {
                    Duration d1 = new Duration();
                    d1.setOrderBy(5);
                    d1.setQuantity(duration.getQuantity());
                    d1.setSerialNum(duration.getSerialNum());
                    map.put(5,d1);
                }
                log.info("map---------------------" + map);

            }


            if (map.containsKey(0)){
            }else{
                Duration d1 = new Duration();
                d1.setOrderBy(0);
                d1.setQuantity(0);
                d1.setSerialNum(o1);
                map.put(0,d1);
            }

            if (map.containsKey(1)){
            }else{
                Duration d1 = new Duration();
                d1.setOrderBy(1);
                d1.setQuantity(0);
                d1.setSerialNum(o2);
                map.put(1,d1);
            }

            if (map.containsKey(2)){
            }else{
                Duration d1 = new Duration();
                d1.setOrderBy(2);
                d1.setQuantity(0);
                d1.setSerialNum(o3);
                map.put(2,d1);
            }

            if (map.containsKey(3)){
            }else{
                Duration d1 = new Duration();
                d1.setOrderBy(3);
                d1.setQuantity(0);
                d1.setSerialNum(o4);
                map.put(3,d1);
            }

            if (map.containsKey(4)){
            }else{
                Duration d1 = new Duration();
                d1.setOrderBy(4);
                d1.setQuantity(0);
                d1.setSerialNum(o5);
                map.put(4,d1);
            }

            if (map.containsKey(5)){
            }else{
                Duration d1 = new Duration();
                d1.setOrderBy(5);
                d1.setQuantity(0);
                d1.setSerialNum(o6);
                map.put(5,d1);
            }
            AjaxResponse response = AjaxResponse.success();
            response.setRows(map);
            response.setTotal((int) map.size());
            log.info(response.toString());
            return response;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return AjaxResponse.failed("未知错误");
        }

    }


    //统计分布
    @RequestMapping(value = "/findStaticCount", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse findStaticCount() {
        log.info("  统计分布    findStaticCount ");
        try {
            return dronStatisticsService.findStaticCount();
        } catch (Exception e) {
            log.error(DroneConstant.ERR_MSG, e);
            e.printStackTrace();
            return AjaxResponse.failed(DroneConstant.ERR_MSG);
        }
    }

    /**
     * 频率统计
     *
     * @return 192.168.1.36:8000/drone/dronStatistics/findFreq
     */
    @RequestMapping(value = "/findFreq", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public AjaxResponse findFreq() {
        try {
            List<Duration> list = durationService.findFreq();
            return AjaxResponse.success(list);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return AjaxResponse.failed("未知错误");
        }

    }

}
