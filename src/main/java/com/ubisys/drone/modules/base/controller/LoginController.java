package com.ubisys.drone.modules.base.controller;

import com.ubisys.drone.common.constant.DroneConstant;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.common.utils.WebSocketUserUtils;
import com.ubisys.drone.common.webSocket.WsDemoStarter;
import com.ubisys.drone.modules.base.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.tio.core.Tio;
import org.tio.websocket.common.WsResponse;

import java.util.Map;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/21 10:03
 */
@Controller
@RequestMapping(value = "/drone/ry")
@Slf4j
public class LoginController {


    @RequestMapping(value = "/testWebSocket")
    @ResponseBody
    @CrossOrigin
    public AjaxResponse webSocketTest() {
        log.info("测试webSocket");
        WsResponse wsResponse = WsResponse.fromText("{\"msg\" ： \"dejijljl\"}",
                "utf-8");
        Map<String, String> map = WebSocketUserUtils.map;// 查找全部连接用户
        for (Map.Entry<String, String> entry : map.entrySet()) {
            // 用户-->机构-->车场（二期工作）
            Tio.sendToToken(WsDemoStarter.getServerGroupContext(), entry.getKey(), wsResponse);// 将警告信息发送至页面
        }
        return AjaxResponse.success();
    }


    /**
     * 用户登录临时版，  不访问数据库   密码为     zkrt666  即可
     *
     * @param request
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public AjaxResponse getUserLogin(HttpServletRequest request, @RequestParam String username, @RequestParam String password) {
        try {
            if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
                return AjaxResponse.failed("用户名密码不能为空！");
            }
            log.info("有人请求登录");
            log.info("登录用户名：" + username + "，登录密码：" + DigestUtils.md5DigestAsHex(password.getBytes("UTF-8")));

            if (!password.equals("zkrt666")) {
                return AjaxResponse.failed("密码不正确！");
            }


            User user = new User();
            user.setUsername("admin");
            user.setNickName("管理员");
            return AjaxResponse.success(user);

        /* //   List<SysUser> userList = sysUserService.selectByUserIdAndPassWord(userLoginName);//根据用户登录名查询该用户是否存在
            *//* 判断用户是否存在，及其登录其他信息的检验 *//*
            if (userList.size() != 0) {//用户集合不等于0证明有用户的存在
                //用户存在去除用户信息，根据数据库设计统一登录用户名存在只存在一个因此该集合中不会出现第二个数据，所以在这里取get(0)
                SysUser user = userList.get(0);
                *//* 校验用户登录密码与数据库中是否一致 *//*
                if (user.getPassword().equals(DigestUtils.md5DigestAsHex(userLoginPassWord.getBytes("UTF-8")))) {//一致返回登陆成功
                    return JsonResulte.ok("登陆成功");
                } else {//不一致返回密码错误
                    return JsonResulte.error(202, "密码错误请重新输入密码！");
                }
            } else {//用户集合等于0证明无该用户存在
                return AjaxResponse.failed("用户不存在，请向上级人员申请账号！");
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            log.error(DroneConstant.ERR_MSG, e);
            return AjaxResponse.failed();
        }
    }


}
