package com.ubisys.drone.modules.base.controller.scaffold;

import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.common.utils.IpInfoUtil;
import com.ubisys.drone.common.utils.JHSMSUtil;
import com.ubisys.drone.modules.base.service.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * <p>Title: 通用接口</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/24 15:12
 */
@Slf4j
@RestController
@Api(description = "通用的相关接口")
@RequestMapping("/drone/common")
@Transactional
public class CommonController {

    @Autowired
    private IpInfoUtil ipInfoUtil;

    @Autowired
    private JHSMSUtil jhsmsUtil;
    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "/noAuth", method = RequestMethod.GET)
    @ApiOperation(value = "未授权，需要登录")
    public AjaxResponse needLogin() {
        return AjaxResponse.build(401, "对不起，您无权访问！");
    }


    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ApiOperation(value = "获取IP及天气相关信息")
    public AjaxResponse upload(HttpServletRequest request) {

        String result = ipInfoUtil.getIpWeatherInfo(ipInfoUtil.getIpAddr(request));
        return AjaxResponse.success(result);
    }


    /**
     * 发送验证码 重置密码
     * 发送成功后，存入redis
     *
     * @param mobliephone 要重置的手机号
     * @return
     */
    @RequestMapping(value = "/sendSmsResetPass", method = RequestMethod.POST)
    @ApiOperation(value = "测试发送短信验证码")
    public AjaxResponse sendSmsResetPass(@ApiParam("mobliephone") @RequestParam String mobliephone) {
        log.info(" sendSmsResetPass mobliephone " + mobliephone);
        try {
            return jhsmsUtil.sendSms("126423", "15801151812");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return AjaxResponse.failed(" 发送验证码失败鸟！ ");
        }
    }


    /**
     * 通过手机号验证码重置密码
     *
     * @param mobliephone
     * @param code
     * @return
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "通过手机号验证码重置密码")
    public AjaxResponse resetPassword(@ApiParam("手机号") @RequestParam String mobliephone, @ApiParam("验证码") @RequestParam String code) {
        log.info(" resetPassword mobliephone " + mobliephone);
        try {
            return commonService.resetPassword(mobliephone, code);
        } catch (Exception e) {
            log.error("重置密码失败鸟！");
            e.printStackTrace();
            return AjaxResponse.failed(" 重置密码失败鸟！ ");
        }
    }


}
