package com.ubisys.drone.modules.base.service.mybatis;

import com.baomidou.mybatisplus.service.IService;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.modules.base.entity.LzDrone;
import com.ubisys.drone.modules.base.model.DroneThirdFloor;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author cw
 * @since 2018-11-06
 */
public interface ILzDroneService extends IService<LzDrone> {

    AjaxResponse selectListByPage(String pageNum, String pageSize);

    /**
     * 批量插入 无人机事件
     *
     * @param ll 无人机事件列表
     * @return
     */
    AjaxResponse insertDroneByLz(List<DroneThirdFloor> ll);

}
