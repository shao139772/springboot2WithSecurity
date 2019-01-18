package com.ubisys.drone.modules.base.serviceimpl.mybatis;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.common.utils.DroneConvertUtil;
import com.ubisys.drone.modules.base.dao.mapper.LzDroneMapper;
import com.ubisys.drone.modules.base.entity.Drone;
import com.ubisys.drone.modules.base.entity.LzDrone;
import com.ubisys.drone.modules.base.model.DroneThirdFloor;
import com.ubisys.drone.modules.base.service.mybatis.ILzDroneService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cw
 * @since 2018-11-06
 */
@Slf4j
@Service
public class LzDroneServiceImpl extends ServiceImpl<LzDroneMapper, LzDrone> implements ILzDroneService {


    @Resource
    private LzDroneMapper lzDroneMapper;

    @Override
    public AjaxResponse selectListByPage(String pageNum, String pageSize) {
        Map<String, Object> result = new HashMap<String, Object>();
        Page<LzDrone> page = new Page<LzDrone>(Integer.valueOf(pageNum), Integer.valueOf(pageSize));
        List<LzDrone> lsWhiteList = lzDroneMapper.selectPage(page, Condition.create());
        Integer count = lzDroneMapper.selectCount(Condition.create());
        AjaxResponse ajaxResponse = AjaxResponse.success(lsWhiteList);
        ajaxResponse.setTotal(count);
        return ajaxResponse;
    }


    /**
     * 批量插入 无人机事件
     *
     * @param ll 无人机事件列表
     * @return
     */
    @Transactional
    @Override
    public AjaxResponse insertDroneByLz(List<DroneThirdFloor> ll) {
       /* List<LzDrone> drones = new ArrayList<LzDrone>();
        for (int i = 0; i < ll.size(); i++) {
            //目前  directions peer_directions  暂时不用，不作处理
            DroneThirdFloor droneThirdFloor = ll.get(i);
            log.info(" 源drone为 " + droneThirdFloor.toString());
            Drone drone = DroneConvertUtil.convert(droneThirdFloor);
            log.info("  转换过后drone为 " + drone.toString());

            int count = lzDroneMapper.selectCount(Condition.create().eq("droneid", drone.getDroneid()).eq("created_time", drone.getCreatedTime()));
            if (count > 0) {
                log.info(" 此事件已经获取~~~~");
            } else {
                drones.add(drone);
            }
        }
        if (CollectionUtils.isEmpty(drones)) {
            return AjaxResponse.success(" 木有事件~~~~ ");
        }
        Boolean result = insertBatch(drones);
        if (result) {
            return AjaxResponse.success();
        }*/
        return AjaxResponse.failed(" 批量处理无人机信息错误！ ");
    }

}
