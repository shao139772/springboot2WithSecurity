package com.ubisys.drone.modules.base.dao;


import com.ubisys.drone.basetemplate.DroneBaseDao;
import com.ubisys.drone.modules.base.entity.RoleDepartment;

import java.util.List;
/**
 * O(∩_∩)O哈哈~角色部门数据处理层
 * @author miaomiao
 */
public interface RoleDepartmentDao extends DroneBaseDao<RoleDepartment,Integer> {

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