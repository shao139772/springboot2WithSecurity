package com.ubisys.drone.modules.base.dao;


import com.ubisys.drone.basetemplate.DroneBaseDao;
import com.ubisys.drone.modules.base.entity.RolePermission;

import java.util.List;

/**
 * O(∩_∩)O哈哈~角色权限数据处理层
 * @author miaomiao
 */
public interface RolePermissionDao extends DroneBaseDao<RolePermission,Integer> {

    /**
     * 通过permissionId获取
     * @param permissionId
     * @return
     */
    List<RolePermission> findByPermissionId(Integer permissionId);

    /**
     * 通过roleId获取
     * @param roleId
     */
    List<RolePermission> findByRoleId(Integer roleId);

    /**
     * 通过roleId删除
     * @param roleId
     */
    void deleteByRoleId(Integer roleId);
}