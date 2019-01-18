package com.ubisys.drone.modules.base.serviceimpl.mybatis;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ubisys.drone.modules.base.dao.mapper.PermissionMapper;
import com.ubisys.drone.modules.base.entity.Permission;
import com.ubisys.drone.modules.base.service.mybatis.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Title: 权限接口实现</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/17 17:59
 */
@Service
public class IPermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    ;

    @Override
    public List<Permission> findByUserId(Integer userId) {

        return permissionMapper.findByUserId(userId);
    }
}
