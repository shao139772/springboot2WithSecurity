package com.ubisys.drone.modules.base.serviceimpl;

import cn.hutool.core.util.StrUtil;
import com.ubisys.drone.modules.base.dao.RoleDao;
import com.ubisys.drone.modules.base.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import com.ubisys.drone.modules.base.entity.Role;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: 角色接口实现</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/17 17:59
 */
@Service
@Slf4j
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public RoleDao getRepository() {
        return roleDao;
    }

    @Override
    public List<Role> findByDefaultRole(Boolean defaultRole) {
        return roleDao.findByDefaultRole(defaultRole);
    }


    /**
     * 分页查询
     * @param pageable  分页条件
     * @param roles 角色对象，查询条件在里面
     * @return
     */
    @Override
    public Page<Role> findByCondition(Pageable pageable, Role roles) {
        log.info("  findByCondition  pageable "+pageable.toString()+" roles "+roles.toString());
        return roleDao.findAll(new Specification<Role>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Path<String> codeField = root.get("code");
                Path<String> nameField = root.get("name");
                Path<String> emailField = root.get("dataType");
                Path<String> departmentIdField = root.get("description");
                Path<Boolean> sexField = root.get("defaultRole");

                List<Predicate> list = new ArrayList<Predicate>();

                //模糊搜素
                if (StrUtil.isNotBlank(roles.getName())) {
                    list.add(cb.like(nameField, '%' + roles.getName() + '%'));
                }

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }



    /**
     * 通过角色名检索角色
     * @return
     * @param name
     */
    @Override
    public Role findByName(String name) {
        return roleDao.findByName(name);
    }
}
