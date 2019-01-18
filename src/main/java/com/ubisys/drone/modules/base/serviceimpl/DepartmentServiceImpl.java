package com.ubisys.drone.modules.base.serviceimpl;

import com.ubisys.drone.common.utils.SecurityUtil;
import com.ubisys.drone.modules.base.dao.DepartmentDao;
import com.ubisys.drone.modules.base.entity.Department;
import com.ubisys.drone.modules.base.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Slf4j
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    public DepartmentDao getRepository() {
        return this.departmentDao;
    }

    @Override
    public List<Department> findByParentIdOrderBySortOrder(Integer parentId, Boolean openDataFilter) {

        // 数据权限
        List<String> depIds = securityUtil.getDeparmentIds();
        if (depIds != null && depIds.size() > 0 && openDataFilter) {
            return departmentDao.findByParentIdAndIdInOrderBySortOrder(parentId, depIds);
        }
        return departmentDao.findByParentIdOrderBySortOrder(parentId);
    }

    @Override
    public List<Department> findByParentIdAndStatusOrderBySortOrder(Integer parentId, Integer status) {

        return departmentDao.findByParentIdAndStatusOrderBySortOrder(parentId, status);
    }

    @Override
    public List<Department> findByTitleLikeOrderBySortOrder(String title, Boolean openDataFilter) {

        // 数据权限
        List<String> depIds = securityUtil.getDeparmentIds();
        if (depIds != null && depIds.size() > 0 && openDataFilter) {
            return departmentDao.findByTitleLikeAndIdInOrderBySortOrder(title, depIds);
        }
        return departmentDao.findByTitleLikeOrderBySortOrder(title);
    }
}