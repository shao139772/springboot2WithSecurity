package com.ubisys.drone.modules.base.serviceimpl;


import com.ubisys.drone.common.constant.DroneConstant;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.modules.base.entity.User;
import com.ubisys.drone.modules.base.service.CommonService;
import com.ubisys.drone.modules.base.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2019/1/7 17:03
 */
@Service
@Slf4j
public class CommonServiceImpl implements CommonService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserService userService;



    /**
     * 通过手机号和验证码重置密码
     * @param mobliephone 手机号
     * @param code  验证码
     * @return
     */
    @Override
    public AjaxResponse resetPassword(String mobliephone, String code) {
        log.info(" resetPassword mobliephone "+mobliephone+" code "+code);

        String rediscode = stringRedisTemplate.opsForValue().get(DroneConstant.SMS + mobliephone);
        if(StringUtils.isBlank(rediscode)){
            return AjaxResponse.build(501,"验证码已过期，请重新发送！");
        }
        User user = userService.findByMobile(mobliephone);
        if(user==null){
            return AjaxResponse.build(502,"未找到号码为"+mobliephone+"的用户！");
        }
        String newEncryptPass = new BCryptPasswordEncoder().encode(DroneConstant.DEFAULT_PASS);
        user.setPassword(newEncryptPass);
        userService.update(user);
        return AjaxResponse.success();
    }
}
