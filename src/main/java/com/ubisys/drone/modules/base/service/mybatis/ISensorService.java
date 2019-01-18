package com.ubisys.drone.modules.base.service.mybatis;

import com.baomidou.mybatisplus.service.IService;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.modules.base.entity.Sensor;
import com.ubisys.drone.modules.base.model.SensorFloor;

import java.util.List;

/**
 * <p>Title: mybatisplus版drone</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/19 19:04
 */
public interface ISensorService extends IService<Sensor> {

    /**
     * 批量修改 站点基础信息
     *
     * @param ll 站点信息列表
     * @return
     */
    AjaxResponse updSensorByLz(List<SensorFloor> ll);

    /**
     * 批量修改 站点其他信息
     *
     * @param info 信息
     * @return
     */
    AjaxResponse updSensorByLz(String infos);
}
