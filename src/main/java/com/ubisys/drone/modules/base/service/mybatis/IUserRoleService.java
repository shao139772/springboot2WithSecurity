package com.ubisys.drone.modules.base.service.mybatis;

import com.baomidou.mybatisplus.service.IService;
import com.ubisys.drone.modules.base.entity.Role;
import com.ubisys.drone.modules.base.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;


/**
 * <p>Title: 用户---角色接口</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/17 17:59
 */
@CacheConfig(cacheNames = "userRole")
public interface IUserRoleService extends IService<UserRole> {

    /**
     * 通过用户id获取
     *
     * @param userId
     * @return
     */
    @Cacheable(key = "#userId")
    List<Role> findByUserId(@Param("userId") Integer userId);

    /**
     * 通过用户id获取用户角色关联的部门数据
     *
     * @param userId
     * @return
     */
    @Cacheable(key = "'depIds:'+#userId")
    List<String> findDepIdsByUserId(Integer userId);
}
