package com.ubisys.drone.modules.base.controller.scaffold;

import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.common.utils.PageUtil;
import com.ubisys.drone.common.utils.SecurityUtil;
import com.ubisys.drone.common.vo.PageVo;
import com.ubisys.drone.modules.base.entity.Role;
import com.ubisys.drone.modules.base.entity.RolePermission;
import com.ubisys.drone.modules.base.entity.User;
import com.ubisys.drone.modules.base.entity.UserRole;
import com.ubisys.drone.modules.base.service.RoleDepartmentService;
import com.ubisys.drone.modules.base.service.RolePermissionService;
import com.ubisys.drone.modules.base.service.RoleService;
import com.ubisys.drone.modules.base.service.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>Title: 角色管理</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/24 14:03
 */
@Slf4j
@RestController
@Api(description = "角色管理接口")
@RequestMapping("/drone/role")
@Transactional
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private RoleDepartmentService roleDepartmentService;

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Autowired
    private SecurityUtil securityUtil;

    @RequestMapping(value = "/getAllList", method = RequestMethod.POST)
    @ApiOperation(value = "获取全部角色")
    public AjaxResponse roleGetAll() {

        List<Role> list = roleService.getAll();
        return AjaxResponse.success(list);
    }

    @RequestMapping(value = "/getAllByPage", method = RequestMethod.POST)
    @ApiOperation(value = "分页获取角色")
    public AjaxResponse getRoleByPage(@ModelAttribute PageVo page, @ModelAttribute Role roles) {


        log.info(" RoleController  getRoleByPage   page  " + page.toString() + " roles " + roles.toString());

        Page<Role> list = roleService.findByCondition(PageUtil.initPage(page), roles);
        for (Role role : list.getContent()) {
            // 角色拥有权限
            List<RolePermission> permissions = rolePermissionService.findByRoleId(role.getId());
            role.setPermissions(permissions);
            // 角色拥有数据权限
            // List<RoleDepartment> departments = roleDepartmentService.findByRoleId(role.getId());
            //role.setDepartments(departments);
        }
        AjaxResponse response = AjaxResponse.success();
        response.setTotal((int) list.getTotalElements());
        response.setRows(list.getContent());
        return response;
    }

    @RequestMapping(value = "/setDefault", method = RequestMethod.POST)
    @ApiOperation(value = "设置或取消默认角色")
    public AjaxResponse setDefault(@RequestParam String id,
                                   @RequestParam Boolean isDefault) {

        Role role = roleService.get(Integer.valueOf(id));
        if (role == null) {
            return AjaxResponse.failed("角色不存在");
        }
        role.setDefaultRole(isDefault);
        roleService.update(role);
        return AjaxResponse.success("设置成功");
    }

    @RequestMapping(value = "/editRolePerm", method = RequestMethod.POST)
    @ApiOperation(value = "编辑角色分配菜单权限")
    public AjaxResponse editRolePerm(@RequestParam String roleId,
                                     @RequestParam(required = false) String[] permIds) {

        //删除其关联权限
        rolePermissionService.deleteByRoleId(Integer.valueOf(roleId));
        //分配新权限
        for (String permId : permIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(Integer.valueOf(roleId));
            rolePermission.setPermissionId(Integer.valueOf(permId));
            rolePermissionService.save(rolePermission);
        }
        //手动批量删除缓存
        Set<String> keysUser = redisTemplate.keys("user:" + "*");
        redisTemplate.delete(keysUser);
        Set<String> keysUserRole = redisTemplate.keys("userRole:" + "*");
        redisTemplate.delete(keysUserRole);
        Set<String> keysUserPerm = redisTemplate.keys("userPermission:" + "*");
        redisTemplate.delete(keysUserPerm);
        Set<String> keysUserMenu = redisTemplate.keys("permission::userMenuList:*");
        redisTemplate.delete(keysUserMenu);
        return AjaxResponse.success();
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存角色")
    public AjaxResponse save(@ModelAttribute Role role) {
        Role U = roleService.findByName(role.getName());
        if (U != null) {
            return AjaxResponse.failed("角色名称不能重复！");
        }
        User user = securityUtil.getCurrUser();
        role.setCreateBy(user.getUsername());
        role.setCreateTime(new Date());
        Role r = roleService.save(role);
        return AjaxResponse.success(r);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "更新角色")
    public AjaxResponse edit(@ModelAttribute Role entity) {
        Role U = roleService.findByName(entity.getName());
        if (U != null) {
            return AjaxResponse.failed("角色名称不能重复！");
        }
        User user = securityUtil.getCurrUser();
        entity.setUpdateBy(user.getUsername());
        entity.setUpdateTime(new Date());
        Role r = roleService.update(entity);
        //手动批量删除缓存
        Set<String> keysUser = redisTemplate.keys("user:" + "*");
        redisTemplate.delete(keysUser);
        Set<String> keysUserRole = redisTemplate.keys("userRole:" + "*");
        redisTemplate.delete(keysUserRole);
        return AjaxResponse.success(r);
    }

    @RequestMapping(value = "/delAllByIds/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过ids删除角色")
    public AjaxResponse delByIds(@PathVariable String[] ids) {

        for (String id : ids) {
            List<UserRole> list = userRoleService.findByRoleId(Integer.valueOf(id));
            if (list != null && list.size() > 0) {
                return AjaxResponse.failed("删除失败，包含正被用户使用关联的角色");
            }
        }
        for (String id : ids) {
            roleService.delete(Integer.valueOf(id));
            //删除关联菜单权限
            rolePermissionService.deleteByRoleId(Integer.valueOf(id));
        }
        return AjaxResponse.success("批量通过id删除数据成功");
    }


}
