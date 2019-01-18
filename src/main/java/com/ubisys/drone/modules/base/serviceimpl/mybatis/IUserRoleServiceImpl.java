package com.ubisys.drone.modules.base.serviceimpl.mybatis;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ubisys.drone.modules.base.dao.mapper.UserRoleMapper;
import com.ubisys.drone.modules.base.entity.Role;
import com.ubisys.drone.modules.base.entity.UserRole;
import com.ubisys.drone.modules.base.service.mybatis.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Title: 部门接口实现</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/17 17:59
 */
@Service
public class IUserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<Role> findByUserId(Integer userId) {

        return userRoleMapper.findByUserId(userId);
    }

    @Override
    public List<String> findDepIdsByUserId(Integer userId) {

        return userRoleMapper.findDepIdsByUserId(userId);
    }
}
