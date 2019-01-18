package com.ubisys.drone.modules.base.controller;

import com.ubisys.drone.common.utils.LZUtils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: gxt
 * @Date: 2018/11/12 14:28
 * @Description:
 */
@RequestMapping(value = "/lz")
@Controller
public class LZController {

    @Autowired
    private LZUtils lzUtils;

    /**
     * 无人机自动攻击开启/关闭
     * @return
     */
    @RequestMapping(value = "/isAutomaticAttack", method = RequestMethod.POST)
    @ResponseBody
    private String getIsAutomaticAttack(HttpServletRequest request){
        Boolean autoAttack = Boolean.valueOf(request.getParameter("autoAttack"));//获取自动攻击是否开启
        String req = lzUtils.autoAttack(autoAttack);
        return req;
    }

    /**
     * 打击无人机
     * @return
     */
    @RequestMapping(value = "/combatUAV", method = RequestMethod.POST)
    @ResponseBody
    private String getCombatUAV(HttpServletRequest request){
        String  droneid = request.getParameter("droneid");//获取无人机ID
        String req = lzUtils.attackDrones(droneid, true, false);//无人机ID，是否打击、默认false
        return req;
    }





}
