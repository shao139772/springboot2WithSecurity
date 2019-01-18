package com.ubisys.drone.modules.base.service;


import com.ubisys.drone.basetemplate.DroneBaseService;
import com.ubisys.drone.modules.base.entity.RolePermission;

import java.util.List;

/**
 * <p>Title: 角色权限接口</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/17 17:59
 */
public interface RolePermissionService extends DroneBaseService<RolePermission,Integer> {

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