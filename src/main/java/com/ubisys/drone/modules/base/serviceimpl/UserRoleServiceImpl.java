package com.ubisys.drone.modules.base.serviceimpl;

import com.ubisys.drone.modules.base.dao.UserRoleDao;
import com.ubisys.drone.modules.base.entity.UserRole;
import com.ubisys.drone.modules.base.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>Title: 用户角色接口实现</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/17 17:59
 */
@Slf4j
@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public UserRoleDao getRepository() {
        return userRoleDao;
    }

    @Override
    public List<UserRole> findByRoleId(Integer roleId) {
        return userRoleDao.findByRoleId(roleId);
    }

    @Override
    public void deleteByUserId(Integer userId) {
        userRoleDao.deleteByUserId(userId);
    }
}
