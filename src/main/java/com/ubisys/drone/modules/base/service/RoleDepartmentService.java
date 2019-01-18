package com.ubisys.drone.modules.base.service;

import com.ubisys.drone.basetemplate.DroneBaseService;
import com.ubisys.drone.modules.base.entity.RoleDepartment;

import java.util.List;

/**
 * <p>Title: 角色部门接口</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/17 17:59
 */
public interface RoleDepartmentService extends DroneBaseService<RoleDepartment,Integer> {

    /**
     * 通过roleId获取
     * @param roleId
     * @return
     */
    List<RoleDepartment> findByRoleId(Integer roleId);

    /**
     * 通过角色id删除
     * @param roleId
     */
    void deleteByRoleId(Integer roleId);

    /**
     * 通过角色id删除
     * @param departmentId
     */
    void deleteByDepartmentId(Integer departmentId);
}