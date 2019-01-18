package com.ubisys.drone.modules.base.service;


import com.ubisys.drone.basetemplate.DroneBaseService;
import com.ubisys.drone.modules.base.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <p>Title: 角色接口</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/17 17:59
 */
public interface RoleService extends DroneBaseService<Role, Integer> {

    /**
     * 获取默认角色
     *
     * @param defaultRole
     * @return
     */
    List<Role> findByDefaultRole(Boolean defaultRole);


    /**
     * 分页查询
     * @param pageable  分页条件
     * @param roles 角色对象，查询条件在里面
     * @return
     */
    Page<Role> findByCondition(Pageable pageable, Role roles);


    /**
     * 通过角色名检索角色
     * @return
     * @param name
     */
    Role findByName(String name);
}
