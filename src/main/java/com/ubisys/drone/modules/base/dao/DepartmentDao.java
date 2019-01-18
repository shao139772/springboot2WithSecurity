package com.ubisys.drone.modules.base.dao;

import com.ubisys.drone.basetemplate.DroneBaseDao;
import com.ubisys.drone.modules.base.entity.Department;

import java.util.List;


/**
 * O(∩_∩)O哈哈~部门数据处理层
 * @author miaomiao
 */
public interface DepartmentDao extends DroneBaseDao<Department, Integer> {

    /**
     * 通过父id获取 升序
     *
     * @param parentId
     * @return
     */
    List<Department> findByParentIdOrderBySortOrder(Integer parentId);

    /**
     * 通过父id获取 升序 数据权限
     *
     * @param parentId
     * @param departmentIds
     * @return
     */
    List<Department> findByParentIdAndIdInOrderBySortOrder(Integer parentId, List<String> departmentIds);

    /**
     * 通过父id和状态获取 升序
     *
     * @param parentId
     * @param status
     * @return
     */
    List<Department> findByParentIdAndStatusOrderBySortOrder(Integer parentId, Integer status);

    /**
     * 部门名模糊搜索 升序
     *
     * @param title
     * @return
     */
    List<Department> findByTitleLikeOrderBySortOrder(String title);

    /**
     * 部门名模糊搜索 升序 数据权限
     *
     * @param title
     * @param departmentIds
     * @return
     */
    List<Department> findByTitleLikeAndIdInOrderBySortOrder(String title, List<String> departmentIds);
}