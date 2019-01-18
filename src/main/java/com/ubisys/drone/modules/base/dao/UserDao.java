package com.ubisys.drone.modules.base.dao;

import com.ubisys.drone.basetemplate.DroneBaseDao;
import com.ubisys.drone.modules.base.entity.User;
import java.util.List;



/**
 * O(∩_∩)O哈哈~用户数据处理层
 * @author miaomiao
 */
public interface UserDao extends DroneBaseDao<User,Integer> {

    /**
     * 通过用户名获取用户
     * @param username
     * @return
     */
    List<User> findByUsername(String username);

    /**
     * 通过手机获取用户
     * @param mobile
     * @return
     */
    List<User> findByMobile(String mobile);

    /**
     * 通过邮件获取用户
     * @param email
     * @return
     */
    List<User> findByEmail(String email);

    /**
     * 通过部门id获取
     * @param departmentId
     * @return
     */
    List<User> findByDepartmentId(Integer departmentId);
}
