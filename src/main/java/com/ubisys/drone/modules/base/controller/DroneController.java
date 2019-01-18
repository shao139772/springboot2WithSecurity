package com.ubisys.drone.modules.base.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ubisys.drone.basetemplate.DroneBaseController;
import com.ubisys.drone.common.constant.DroneConstant;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.common.utils.LZUtils;
import com.ubisys.drone.common.vo.PageVo;
import com.ubisys.drone.modules.base.entity.Drone;
import com.ubisys.drone.modules.base.model.SenorAtuoAttack;
import com.ubisys.drone.modules.base.service.DroneService;
import com.ubisys.drone.modules.base.service.mybatis.IDroneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * @author miaomiao
 */
@Slf4j
@RestController
@Api(description = "O(∩_∩)O哈哈~无人机管理接口")
@RequestMapping("/drone/ry")
@Transactional
public class DroneController extends DroneBaseController<Drone, Integer> {

    @Autowired
    private DroneService droneService;
    @Autowired
    private LZUtils lzUtils;


    @Autowired
    private IDroneService iDroneService;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public DroneService getService() {
        return droneService;
    }


    /**
     * 获取正在被侦测到的无人机
     *
     * @return
     */
    @RequestMapping(value = "/findDrones", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    @ApiOperation(value = "获取正在被侦测到的无人机")
    public AjaxResponse findDrones() {

        log.info("  findDrones  获取正在被侦测到的无人机 ");
        List<Drone> ll = new ArrayList<Drone>();
        Set<Object> keys = stringRedisTemplate.opsForHash().keys(DroneConstant.LZ_DRONE);
        for (Iterator<Object> iterator = keys.iterator(); iterator.hasNext(); ) {
            Object temp = stringRedisTemplate.opsForHash().get(DroneConstant.LZ_DRONE, String.valueOf(iterator.next()));
            ll.add(JSON.parseObject(String.valueOf(temp), Drone.class));
        }

        return AjaxResponse.success(ll);
    }


    /**
     * 无人机自动攻击开启/关闭
     * autoAttack  开启 ：true  ,    关闭 ：false
     * 开启自动打击后，
     *
     * @return
     */
    @RequestMapping(value = "/isAutomaticAttack", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    @ApiOperation(value = "无人机自动攻击开启/关闭")
    public AjaxResponse getIsAutomaticAttack(HttpServletRequest request, @ApiParam(name = "autoAttack", value = "autoAttack  开启:true,关闭:false", required = true) @RequestParam String autoAttack) {
        log.info(" isAutomaticAttack autoAttack " + autoAttack);
        Boolean attack = Boolean.valueOf(autoAttack);
        try {
            String req = lzUtils.autoAttack(Boolean.valueOf(autoAttack));
            //开启无人机自动打击的结果
            log.info(" 开启无人机自动打击的结果 " + req);
            //看命令是否成功下发，看有无error返回值
            if (lzUtils.isSuccess(req)) {
                if (attack) {
                    return iDroneService.updateAutoAttackDrone();
                }

                // iDroneService.update();
            }
            return AjaxResponse.success(req);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(DroneConstant.ERR_MSG, e);
        }
        return AjaxResponse.failed(DroneConstant.ERR_MSG);
    }

    /**
     * 打击无人机
     * droneid  无人机id
     * cancel    true 攻击还是取消  false
     *
     * @return
     */
    @RequestMapping(value = "/combatUAV", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    @ApiOperation(value = "打击无人机")
    public AjaxResponse getCombatUAV(HttpServletRequest request, @ApiParam(name = "droneid", value = "无人机破解码", required = true) @RequestParam String droneid, @ApiParam(name = "cancel", value = "攻击false   取消true", required = true) @RequestParam String cancel) {
        Boolean cancelS = Boolean.valueOf(cancel);//获取打击命令
        log.info(" combatUAV  droneid:{}. cancel:{}. ", new Object[]{droneid, cancelS});
        try {
            //如果是无人机打击命令，需要查看当前自动打击的状态，如果自动打击已开启，则不再进行打击
            if (cancelS) {
                lzUtils.cheackAutoState();
                String temp = stringRedisTemplate.opsForValue().get(DroneConstant.AUTO_ATTACK);
                if (StringUtils.isBlank(temp)) {
                    return AjaxResponse.failed(DroneConstant.ERR_MSG);
                }
                SenorAtuoAttack senorAtuoAttack = JSONObject.parseObject(temp, SenorAtuoAttack.class);
                if (senorAtuoAttack.getAutoAttack()) {
                    return AjaxResponse.build(501, "自动打击已开启，无法取消打击！");
                }
            }
            String req = lzUtils.attackDrones(droneid, cancelS, false);//无人机ID，是否打击、默认false
            log.info(" 打击无人机结果  " + req);
            //命令成功
            if (lzUtils.isSuccess(req)) {
                return iDroneService.updateAttackDrone(droneid, cancelS);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(DroneConstant.ERR_MSG, e);
        }
        return AjaxResponse.failed(DroneConstant.ERR_MSG);
    }


    /**
     * 分页获取无人机事件  当天
     *
     * @param page 分页对象
     * @return
     */
    @RequestMapping(value = "/findDroneEvents", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    @ApiOperation(value = " 分页获取无人机事件  当天")
    public AjaxResponse findDroneEvents(@ModelAttribute PageVo page) {
        log.info("  findDroneEvents  ");
        try {
            return iDroneService.findDroneEvents(page);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(DroneConstant.ERR_MSG, e);
        }
        return AjaxResponse.failed(DroneConstant.ERR_MSG);
    }


    /**
     * 无人机报警历史事件添加备注
     *
     * @param memo
     * @param id
     * @return
     */
    @RequestMapping(value = "/addEventMemo", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    @ApiOperation(value = " 无人机报警历史事件添加备注 ")
    public AjaxResponse addEventMemo(@ApiParam(name = "memo", value = "备注信息", required = true) @RequestParam String memo, @ApiParam(name = "id", value = "无人机id", required = true) @RequestParam String id) {
        log.info("  addEventMemo  memo:{}. id:{}. ", new String[]{memo, id});
        if (StringUtils.isBlank(id) || StringUtils.isBlank(memo)) {
            return AjaxResponse.failed(" 请求参数不能为空！ ");
        }
        try {
            return iDroneService.addEventMemo(id, memo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(DroneConstant.ERR_MSG, e);
        }
        return AjaxResponse.failed(DroneConstant.ERR_MSG);
    }


    /**
     * 获取站点的自动攻击状态
     *
     * @return
     */
    @RequestMapping(value = "/findAutoAttack", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    @ApiOperation(value = " 获取站点的自动攻击状态")
    public AjaxResponse findAutoAttack() {
        lzUtils.cheackAutoState();
        String temp = stringRedisTemplate.opsForValue().get(DroneConstant.AUTO_ATTACK);
        if (StringUtils.isBlank(temp)) {
            return AjaxResponse.failed(DroneConstant.ERR_MSG);
        }
        return AjaxResponse.success(JSONObject.parseObject(temp, SenorAtuoAttack.class));
    }


    @RequestMapping(value = "/handleWhiteList", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    @ApiOperation(value = " 白名单操作 opr: 1 为添加  2 为删除  ")
    public AjaxResponse handleWhiteList(@ApiParam(name = "droneid", value = "无人机id", required = true) @RequestParam(value = "droneid") String droneid, @ApiParam(name = "name", value = "无人机机型") @RequestParam(value = "name", required = false) String name, @ApiParam(name = "opr", value = "操作：1 添加，  2 删除", required = true) @RequestParam(value = "opr") String opr) {
        log.info(" handleWhiteList  droneid:{}. opr:{}. name:{}. ", new String[]{droneid, opr, name});
        //添加白名单
        if (DroneConstant.DRONE_WHITE_ADD.equals(opr)) {
            if (StringUtils.isBlank(name)) {
                return AjaxResponse.build(502, "添加白名单时，需要机型！");
            }
        }
        if (!DroneConstant.DRONE_WHITE_DEL.equals(opr) && !DroneConstant.DRONE_WHITE_ADD.equals(opr)) {
            return AjaxResponse.build(503, "非法的操作符!!！");
        }
        return iDroneService.handleWhiteList(droneid, name, opr);
    }
}
