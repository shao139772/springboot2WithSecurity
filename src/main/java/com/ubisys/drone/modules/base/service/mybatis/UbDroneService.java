package com.ubisys.drone.modules.base.service.mybatis;

import com.baomidou.mybatisplus.service.IService;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.modules.base.entity.UbisysDrone;
import com.ubisys.drone.modules.base.entity.UbisysDroneInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: mybatisplus版drone</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: Jhon Davis
 * @Date: 2018/12/19 19:04
 */
public interface UbDroneService extends IService<UbisysDrone> {
    //List<UbisysDrone> allAlarmInformation();
    AjaxResponse findDeviceFaultList(Integer pageNum, Integer pageSize,String serialNumber, String time);
    //List<UbisysDrone> findDeviceFaultList(String serialNumber);
}
