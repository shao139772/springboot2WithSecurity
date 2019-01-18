package com.ubisys.drone.modules.base.service;


import com.ubisys.drone.basetemplate.DroneBaseService;
import com.ubisys.drone.modules.base.entity.UserRole;

import java.util.List;

/**
 * <p>Title: 用户角色接口</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/17 17:59
 */
public interface UserRoleService extends DroneBaseService<UserRole, Integer> {

    /**
     * 通过roleId查找
     *
     * @param roleId
     * @return
     */
    List<UserRole> findByRoleId(Integer roleId);

    /**
     * 删除用户角色
     *
     * @param userId
     */
    void deleteByUserId(Integer userId);
}
