package com.ubisys.drone.modules.base.service.mybatis;

import com.baomidou.mybatisplus.service.IService;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.common.vo.PageVo;
import com.ubisys.drone.modules.base.entity.Drone;
import com.ubisys.drone.modules.base.model.DroneThirdFloor;

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
public interface IDroneService extends IService<Drone> {
    /**
     * 批量插入 无人机事件
     *
     * @param ll 无人机事件列表
     * @return
     */
    AjaxResponse insertDroneByLz(List<DroneThirdFloor> ll);


    /**
     * 无人机打击后，更新数据库中的状态  打击时间
     *
     * @param droneid 无人机序列码
     * @param cancelS fasle  为打击  ，true  为取消打击
     * @return
     */
    AjaxResponse updateAttackDrone(String droneid, Boolean cancelS);


    /**
     * 无人机消失
     * 为该事件  增加消失时间，并使用消失时间-创建时间   计算该无人机事件的持续时长
     *
     * @param drone
     * @return
     */
    int updateDroneTOEnd(Drone drone);


    /**
     * 分页获取无人机事件当天
     *
     * @param page
     * @return
     */
    AjaxResponse findDroneEvents(PageVo page);


    /**
     * 为无人机事件添加备注
     *
     * @param id
     * @param memo
     * @return
     */
    AjaxResponse addEventMemo(String id, String memo);


    /**
     * 站点开启自动打击后，将数据库中所有正在发现的无更新数据库中的状态  打击时间
     *
     * @return
     */
    AjaxResponse updateAutoAttackDrone();

    /**
     * 无人机白名单操作
     *
     * @param droneid 无人机id
     * @param name    机型
     * @param opr     操作 1 添加  2 删除
     * @return
     */
    AjaxResponse handleWhiteList(String droneid, String name, String opr);
}
