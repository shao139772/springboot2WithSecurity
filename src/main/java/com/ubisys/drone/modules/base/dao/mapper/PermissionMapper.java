package com.ubisys.drone.modules.base.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ubisys.drone.modules.base.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * O(∩_∩)O哈哈~权限mapper
 *
 * @author miaomiao
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 通过用户id获取
     *
     * @param userId
     * @return
     */
    List<Permission> findByUserId(@Param("userId") Integer userId);
}
