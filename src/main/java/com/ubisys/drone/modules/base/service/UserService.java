package com.ubisys.drone.modules.base.service;


import com.ubisys.drone.basetemplate.DroneBaseService;
import com.ubisys.drone.common.vo.SearchVo;
import com.ubisys.drone.modules.base.entity.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <p>Title: 用户接口</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/17 17:59
 */
@CacheConfig(cacheNames = "user")
public interface UserService extends DroneBaseService<User, Integer> {

    /**
     * 通过用户名获取用户
     *
     * @param username
     * @return
     */
    @Cacheable(key = "#username")
    User findByUsername(String username);

    /**
     * 通过手机获取用户
     *
     * @param mobile
     * @return
     */
    User findByMobile(String mobile);

    /**
     * 通过邮件和状态获取用户
     *
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * 多条件分页获取用户
     *
     * @param user
     * @param searchVo
     * @param pageable
     * @return
     */
    Page<User> findByCondition(User user, SearchVo searchVo, Pageable pageable);

    /**
     * 通过部门id获取
     *
     * @param departmentId
     * @return
     */
    List<User> findByDepartmentId(Integer departmentId);
}
