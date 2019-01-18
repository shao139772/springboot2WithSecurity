package com.ubisys.drone.modules.base.controller;


import com.baomidou.mybatisplus.mapper.Condition;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.common.utils.LZUtils;
import com.ubisys.drone.modules.base.entity.LzWhitelist;
import com.ubisys.drone.modules.base.service.mybatis.ILzWhitelistService;
import io.swagger.annotations.Api;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cw
 * @since 2018-11-01
 */
@Api(description = "O(∩_∩)O哈哈~管理接口")
@Controller
@RequestMapping("/drone/lzWhitelist")
public class LzWhitelistController {

    @Autowired
    private ILzWhitelistService lzWhitelistService;

    @Autowired
    private LZUtils lzUtils;

    /**
     * 查询无人机白名单
     * @return
     */
    @RequestMapping(value = "/lzWhitelist", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse getLzWhiteList(HttpServletRequest request){
        String pageNum = request.getParameter("page");//获取页数
        String pageSize = request.getParameter("limit");//获取条数
        return lzWhitelistService.selectListByPage(pageNum, pageSize);//查询集合
    }

    /**
     * 添加/修改无人机白名单（无人机已识别）
     * @return
     */
    @RequestMapping(value = "/addLzWhite", method = RequestMethod.POST)
    @ResponseBody
    public boolean getAddLzWhite(HttpServletRequest request){
        boolean flag = false;//执行结果
        //暂时不与历正同步，才去定数同步的方式轮询数据库数据
        String droneId = request.getParameter("droneId");//获取ID
        String droneType = request.getParameter("droneType");//获取类型
        String isWhite = request.getParameter("isWhite");//获取是否是白名单
        if(isWhite.equals("true")){//是白名单
            isWhite = "1";
        }else if(isWhite.equals("false")){//不属于白名单
            isWhite = "2";
        }else{//未传值，或传值有误
            isWhite = "";
        }
        //根据ID查询白名单表中是否有该条数据，无数据为新增，有数据为修改
        List<LzWhitelist> lzWhitelistList= lzWhitelistService.selectList(Condition.create().eq("drone_id", droneId));
        LzWhitelist LzWhitelist = new LzWhitelist();
        LzWhitelist.setDroneId(droneId);
        LzWhitelist.setDronetype(droneType);
        LzWhitelist.setIsWhite(isWhite);
        LzWhitelist.setIsLz("1");//默认不去同步
        if (lzWhitelistList.size() != 0){//有相关的数据，修改
            flag = lzWhitelistService.update(LzWhitelist, Condition.create().eq("drone_id", droneId));
        }else if (lzWhitelistList.size() == 0){//无相关数据，新增
            flag = lzWhitelistService.insert(LzWhitelist);//添加白名单
        }
        return flag;
    }
}

