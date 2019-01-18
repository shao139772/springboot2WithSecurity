package com.ubisys.drone.modules.base.dao;

import com.ubisys.drone.basetemplate.DroneBaseDao;
import com.ubisys.drone.modules.base.entity.Role;

import java.util.List;

/**
 * O(∩_∩)O哈哈~角色数据处理层
 * @author miaomiao
 */
public interface RoleDao extends DroneBaseDao<Role, Integer> {

    /**
     * 获取默认角色
     *
     * @param defaultRole
     * @return
     */
    List<Role> findByDefaultRole(Boolean defaultRole);


    /**
     * 通过角色名检索角色
     * @return
     * @param name
     */
    Role findByName(String name);
}
