package com.ubisys.drone.modules.base.dao;

import com.ubisys.drone.basetemplate.DroneBaseDao;
import com.ubisys.drone.modules.base.entity.UserRole;

import java.util.List;

/**
 * O(∩_∩)O哈哈~用户角色数据处理层
 * @author miaomiao
 */
public interface UserRoleDao extends DroneBaseDao<UserRole, Integer> {

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
