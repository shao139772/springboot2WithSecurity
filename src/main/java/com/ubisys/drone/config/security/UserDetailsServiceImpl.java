package com.ubisys.drone.config.security;

import cn.hutool.core.util.StrUtil;
import com.ubisys.drone.config.exception.LoginFailLimitException;
import com.ubisys.drone.modules.base.entity.User;
import com.ubisys.drone.modules.base.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * UserDetailsService实现类
 */
@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String flagKey = "loginFailFlag:" + username;
        String value = redisTemplate.opsForValue().get(flagKey);
        Long timeRest = redisTemplate.getExpire(flagKey, TimeUnit.MINUTES);
        //为防止 取出缓存中的数据，手动刷新缓存中的数据
        redisTemplate.delete("user::" + username);
        if (StrUtil.isNotBlank(value)) {
            //超过限制次数
            throw new LoginFailLimitException("登录错误次数超过限制，请" + timeRest + "分钟后再试");
        }
        User user = userService.findByUsername(username);
        log.info(" user    " + username + " 登录成功 " + user);
        return new SecurityUserDetails(user);
    }
}
