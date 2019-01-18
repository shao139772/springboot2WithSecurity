package com.ubisys.drone.modules.base.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ubisys.drone.modules.base.entity.Role;
import com.ubisys.drone.modules.base.entity.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * O(∩_∩)O哈哈~用户角色mapper
 *
 * @author miaomiao
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 通过用户id获取
     *
     * @param userId
     * @return
     */
    List<Role> findByUserId(@Param("userId") Integer userId);

    /**
     * 通过用户id获取用户角色关联的部门数据
     *
     * @param userId
     * @return
     */
    List<String> findDepIdsByUserId(@Param("userId") Integer userId);
}
