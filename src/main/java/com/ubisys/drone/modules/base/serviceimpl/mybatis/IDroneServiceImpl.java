package com.ubisys.drone.modules.base.serviceimpl.mybatis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ubisys.drone.common.constant.DroneConstant;
import com.ubisys.drone.common.utils.*;
import com.ubisys.drone.common.vo.PageVo;
import com.ubisys.drone.modules.base.dao.mapper.DroneMapper;
import com.ubisys.drone.modules.base.entity.Drone;
import com.ubisys.drone.modules.base.model.DroneThirdFloor;
import com.ubisys.drone.modules.base.service.mybatis.IDroneService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
public class IDroneServiceImpl extends ServiceImpl<DroneMapper, Drone> implements IDroneService {

    @Autowired
    private DroneMapper droneMapper;


    @Autowired
    private LZPushUtil lzPushUtil;

    @Autowired
    private LZUtils lzUtils;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 批量插入 无人机事件
     *
     * @param ll 无人机事件列表
     * @return
     */
    @Transactional
    @Override
    public AjaxResponse insertDroneByLz(List<DroneThirdFloor> ll) {
        List<Drone> drones = new ArrayList<Drone>();
        for (int i = 0; i < ll.size(); i++) {
            //目前  directions peer_directions  暂时不用，不作处理
            DroneThirdFloor droneThirdFloor = ll.get(i);
            log.info(" 源drone为 " + droneThirdFloor.toString());
            Drone drone = DroneConvertUtil.convert(droneThirdFloor);
            log.info("  转换过后drone为 " + drone.toString());


            //更改  对比源   只要: 黑白名单  和 是否正在被打击是否为同一个
            //  int count = droneMapper.selectCount(Condition.create().eq("droneid", drone.getDroneid()).eq("created_time", drone.getCreatedTime()));

            //是否为同一个
            Boolean sameOne = false;
            Object temp = stringRedisTemplate.opsForHash().get(DroneConstant.LZ_DRONE, drone.getDroneid());
            //redis 中不存在，则为首次发现
            if (temp == null) {
                log.info(" 缓存中 并不存在  droneid 为 " + drone.getDroneid() + " 的无人机，为首次发现 ");
                //缓存中存在,则需要比较创建时间是否为同一个
            } else {
                Drone redisDrone = JSON.parseObject(String.valueOf(temp), Drone.class);
                log.info(" redisDrone " + redisDrone);
                //创建时间相同，为同一个无人机事件
                if (DateUtils.date2Str(drone.getCreatedTime(), DateUtils.datetimeFormat).equals(DateUtils.date2Str(redisDrone.getCreatedTime(), DateUtils.datetimeFormat))) {
                    log.info(" 无人机id相同，创建时间相同，为同一个无人机事件 ");
                    sameOne = true;
                }
            }
            if (sameOne) {
                log.info(" 此事件已经获取~~~~");
            } else {
                //获取站点的自动打击状态，如果，已经开启了自动打击，则需要将能被打击的无人机变成被打击状态
                Boolean atuoattack = lzUtils.getAtuoattackFromRedis();
                if (atuoattack) {
                    //能被打击  并且 处于非白名单中
                    if (drone.getCanAttack() == 1 && drone.getWhitelisted() == 2) {
                        drone.setAttackTime(new Date());
                    }
                }
                drones.add(drone);
            }
        }

        if (CollectionUtils.isEmpty(drones)) {
            //没有新的事件，但是无人机的数量发生了变化，也要告知前端
            Long nativeCount = stringRedisTemplate.opsForHash().size(DroneConstant.LZ_DRONE);
           log.info(" 被侦测到的无人机数量为 "+ ll.size());
            log.info(" 本地的无人机数量为 "+nativeCount);
            if (nativeCount - ll.size() > 0) {
                log.info("  侦测到的无人机数量发生了变化！想前端推送！ ");
                lzPushUtil.pushDroneToFront();
            }
            return AjaxResponse.success(" 木有事件~~~~ ");
        }
        Boolean result = insertBatch(drones);
        //存入redis
        for (int i = 0; i < drones.size(); i++) {
            /*stringRedisTemplate.opsForHash().*/
            //存入redis
            stringRedisTemplate.opsForHash().put(DroneConstant.LZ_DRONE, drones.get(i).getDroneid(), JSON.toJSONString(drones.get(i)));
        }
        //  新发现无人机  向前端页面退送
        lzPushUtil.pushDroneToFront();
        if (result) {
            return AjaxResponse.success();
        }
        return AjaxResponse.failed(" 批量处理无人机信息错误！ ");
    }


    /**
     * 无人机打击后，更新数据库中的状态  打击时间
     *
     * @param droneid 无人机序列码
     * @param cancelS fasle  为打击  ，true  为取消打击
     * @return
     */
    @Transactional
    @Override
    public AjaxResponse updateAttackDrone(String droneid, Boolean cancelS) {


        Map<String, Object> param = new HashMap<String, Object>();
        param.put("droneid", droneid);
        List<Drone> ll = droneMapper.selectList(Condition.create().eq("droneid", droneid).isNull("deleted_time"));


        for (int i = 0; i < ll.size(); i++) {
            Drone drone = ll.get(i);
            log.info("drone  " + drone.toString());
            //cancelS  false为打击
            if (!cancelS) {
                drone.setAttackTime(new Date());
            } else {
                drone.setAttackTime(null);
            }
            log.info("drone   " + drone.toString());
            int j = droneMapper.updateById(drone);
            //更新成功，更新缓存
            if (j == 1) {
                stringRedisTemplate.opsForHash().put(DroneConstant.LZ_DRONE, drone.getDroneid(), JSON.toJSONString(drone));
            }
        }

        return AjaxResponse.success();
    }


    /**
     * 无人机消失
     * 为该事件  增加消失时间，并使用消失时间-创建时间   计算该无人机事件的持续时长
     *
     * @param drone
     * @return
     */
    @Override
    public int updateDroneTOEnd(Drone drone) {

        drone.setDeletedTime(new Date());
        int ttl = DateUtils.dateDiff('s', drone.getCreatedTime(), drone.getDeletedTime());
        drone.setTtl(ttl);
        return droneMapper.updateById(drone);
    }


    /**
     * 分页获取无人机事件当天
     *
     * @param page
     * @return
     */
    @Override
    public AjaxResponse findDroneEvents(PageVo page) {


        Pageable pageable = PageUtil.initPage(page);


        Page<Drone> mpage = new Page<Drone>(pageable.getPageNumber(), pageable.getPageSize());
        List<String> ll = new ArrayList<String>();
        ll.add("id");
        selectPage(mpage, Condition.create().isNotNull("deleted_time").orderDesc(ll));
        return AjaxResponse.success(mpage);
    }


    /**
     * 为无人机事件添加备注
     *
     * @param id
     * @param memo
     * @return
     */
    @Override
    public AjaxResponse addEventMemo(String id, String memo) {
        Drone drone = new Drone();
        drone.setId(Integer.valueOf(id));
        drone.setMemo(memo);
        int j = droneMapper.update(drone, Condition.create().eq("id", id));
        if (j == 1) {
            return AjaxResponse.success();
        }
        return AjaxResponse.failed();
    }


    /**
     * 站点开启自动打击后，将数据库中所有正在发现的无更新数据库中的状态  打击时间
     *
     * @return
     */
    @Override
    public AjaxResponse updateAutoAttackDrone() {
        List<Drone> ll = droneMapper.selectList(Condition.create().isNull("deleted_time"));


        for (int i = 0; i < ll.size(); i++) {
            Drone drone = ll.get(i);
            log.info("drone  " + drone.toString());
            drone.setAttackTime(new Date());
            int j = droneMapper.updateById(drone);
            //更新成功，更新缓存
            if (j == 1) {
                stringRedisTemplate.opsForHash().put(DroneConstant.LZ_DRONE, drone.getDroneid(), JSON.toJSONString(drone));
            }
        }

        //  无人机自动打击后  向前端页面退送
        lzPushUtil.pushDroneToFront();


        return AjaxResponse.success();
    }


    /**
     * 无人机白名单操作   操作完成之后  需要向前端推送
     * 添加白名单时，需要机型 无人机id    只对下一次有效，当前如果已经被打击，还是会被打击
     * 删除白名单时，仅需无人机id  即可      移除白名单后  需要关注自动打击的转态，如果开启自动打击，该无人机自动变为被打击
     *
     * @param droneid 无人机id
     * @param name    机型
     * @param opr     操作 1 添加  2 删除
     * @return
     */
    @Override
    public AjaxResponse handleWhiteList(String droneid, String name, String opr) {
        Object temp = stringRedisTemplate.opsForHash().get(DroneConstant.LZ_DRONE, droneid);
        Drone drone = JSON.parseObject(String.valueOf(temp), Drone.class);
        if (DroneConstant.DRONE_WHITE_ADD.equals(opr)) {
            log.info(" 添加白名单 droneid:{}. name:{}. ", new String[]{droneid, name});
            if (drone.getWhitelisted() == 1) {
                return AjaxResponse.build(503, "该飞机已经是白名单飞机");
            }
            String req = lzUtils.addWhitelist(droneid, name);
            if (lzUtils.isSuccess(req)) {
                drone.setWhitelisted(1);
            }
        } else {
            log.info(" 移除白名单 droneid:{} " + droneid);
            if (drone.getWhitelisted() == 2) {
                return AjaxResponse.build(503, "该飞机已经是黑名单飞机");
            }
            String req = lzUtils.deleteWhitelist(droneid);
            if (lzUtils.isSuccess(req)) {
                //如果开启了自动打击
                if (lzUtils.getAtuoattackFromRedis()) {
                    drone.setAttackTime(new Date());
                }
                drone.setWhitelisted(2);
            }
        }
        //更新数据库，更新缓存
        if (updateById(drone)) {
            stringRedisTemplate.opsForHash().put(DroneConstant.LZ_DRONE, droneid, JSONObject.toJSONString(drone));
            lzPushUtil.pushDroneToFront();
            return AjaxResponse.success();
        }
        return AjaxResponse.failed(DroneConstant.ERR_MSG);
    }
}
