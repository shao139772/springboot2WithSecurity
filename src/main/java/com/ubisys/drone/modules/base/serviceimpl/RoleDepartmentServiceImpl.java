package com.ubisys.drone.modules.base.serviceimpl;

import com.ubisys.drone.modules.base.dao.RoleDepartmentDao;
import com.ubisys.drone.modules.base.entity.RoleDepartment;
import com.ubisys.drone.modules.base.service.RoleDepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>Title: 角色部门接口实现</p>
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
public class RoleDepartmentServiceImpl implements RoleDepartmentService {

    @Autowired
    private RoleDepartmentDao roleDepartmentDao;

    @Override
    public RoleDepartmentDao getRepository() {
        return roleDepartmentDao;
    }

    @Override
    public List<RoleDepartment> findByRoleId(Integer roleId) {

        return roleDepartmentDao.findByRoleId(roleId);
    }

    @Override
    public void deleteByRoleId(Integer roleId) {

        roleDepartmentDao.deleteByRoleId(roleId);
    }

    @Override
    public void deleteByDepartmentId(Integer departmentId) {

        roleDepartmentDao.deleteByDepartmentId(departmentId);
    }
}