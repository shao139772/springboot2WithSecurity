package com.ubisys.drone.modules.base.service;

import com.ubisys.drone.common.utils.AjaxResponse;

/**
 * <p>Title: 通用service</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2019/1/7 17:01
 */
public interface CommonService {


    /**
     * 通过手机号和验证码重置密码
     * @param mobliephone 手机号
     * @param code  验证码
     * @return
     */
    AjaxResponse resetPassword(String mobliephone, String code);
}
