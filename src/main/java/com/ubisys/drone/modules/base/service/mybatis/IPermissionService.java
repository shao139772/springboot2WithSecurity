package com.ubisys.drone.modules.base.service.mybatis;

import com.baomidou.mybatisplus.service.IService;
import com.ubisys.drone.modules.base.entity.Permission;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;


/**
 * <p>Title: 权限接口</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/17 17:59
 */
@CacheConfig(cacheNames = "userPermission")
public interface IPermissionService extends IService<Permission> {

    /**
     * 通过用户id获取
     *
     * @param userId
     * @return
     */
    @Cacheable(key = "#userId")
    List<Permission> findByUserId(Integer userId);
}
