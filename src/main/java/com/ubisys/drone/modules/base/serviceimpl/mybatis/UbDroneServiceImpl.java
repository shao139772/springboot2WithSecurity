package com.ubisys.drone.modules.base.serviceimpl.mybatis;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.common.utils.BatisUtils;
import com.ubisys.drone.modules.base.dao.mapper.UbDroneMapper;
import com.ubisys.drone.modules.base.entity.UbisysDrone;
import com.ubisys.drone.modules.base.service.mybatis.UbDroneService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: 无人机接口实现</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: Jhon Davis
 * @Date: 2018/12/17 17:59
 */
@Service
@Slf4j
public class UbDroneServiceImpl extends ServiceImpl<UbDroneMapper, UbisysDrone> implements UbDroneService {

    @Autowired
    private UbDroneMapper ubDroneMapper;

    @Override
    public AjaxResponse findDeviceFaultList(Integer pageNum, Integer pageSize,String serialNumber, String time) {
        Map<String, Object> dfMap = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(serialNumber)) {
            dfMap.put("devicecode", serialNumber);
        }
        if (StringUtils.isNotBlank(time)) {
            dfMap.put("time", time);
        }
        int psize = 10;
        int pnum = 1;

        try {
            psize = pageSize;
            pnum = pageNum;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return AjaxResponse.failed("获取分页参数失败");
        }

        long beginRow = BatisUtils.getStartRow(pnum, psize);
        dfMap.put("startRow", beginRow);
        dfMap.put("pageSize", psize);

        List<UbisysDrone> list = ubDroneMapper.allAlarmInformation(dfMap);
        long count = ubDroneMapper.countAlarmInformation(dfMap);
        //需要在写一个mybatis查询查总条数
        AjaxResponse response = AjaxResponse.success();
        response.setTotal((int) count);
        response.setRows(list);
        return response;
    }


    /*@Override
    public AjaxResponse findDeviceFaultList(String serialNumber) {
        Map<String, Object> dfMap = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(serialNumber)) {
            dfMap.put("devicecode", serialNumber);
        }

        AjaxResponse response = AjaxResponse.success();

        List<UbisysDrone> list = ubDroneMapper.allAlarmInformation(dfMap);

        response.setRows(list);
        response.setTotal(list.size());
        return  response;
    }*/
   /* @Override
    public List<UbisysDrone> allAlarmInformation() {
        return ubDroneMapper.allAlarmInformation();
    }*/
}
