package com.ubisys.drone.modules.base.controller.scaffold;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ubisys.drone.common.constant.DroneConstant;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.common.utils.SecurityUtil;
import com.ubisys.drone.config.security.permission.MySecurityMetadataSource;
import com.ubisys.drone.modules.base.entity.Permission;
import com.ubisys.drone.modules.base.entity.RolePermission;
import com.ubisys.drone.modules.base.entity.User;
import com.ubisys.drone.modules.base.service.PermissionService;
import com.ubisys.drone.modules.base.service.RolePermissionService;
import com.ubisys.drone.modules.base.service.mybatis.IPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;

/**
 * <p>Title: 菜单权限管理接口</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/24 14:19
 */
@Slf4j
@RestController
@Api(description = "菜单/权限管理接口")
@RequestMapping("/drone/permission")
@CacheConfig(cacheNames = "permission")
@Transactional
public class PermissionController {


    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private IPermissionService iPermissionService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private MySecurityMetadataSource mySecurityMetadataSource;

    @RequestMapping(value = "/getMenuList", method = RequestMethod.POST)
    @ApiOperation(value = "获取用户页面菜单数据")
    public AjaxResponse getAllMenuList() {

        List<Permission> list = new ArrayList<>();
        // 读取缓存
        User u = securityUtil.getCurrUser();
        String key = "permission::userMenuList:" + u.getId();
        String v = redisTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(v)) {
            list = new Gson().fromJson(v, new TypeToken<List<Permission>>() {
            }.getType());
            return AjaxResponse.success(list);
        }

        // 用户所有权限 已排序去重
        list = iPermissionService.findByUserId(u.getId());

        List<Permission> menuList = new ArrayList<>();
        //筛选一级页面
        for (Permission p : list) {
            if (DroneConstant.PERMISSION_PAGE.equals(p.getType()) && DroneConstant.LEVEL_ONE.equals(p.getLevel())) {
                menuList.add(p);
            }
        }
        //筛选二级页面
        List<Permission> secondMenuList = new ArrayList<>();
        for (Permission p : list) {
            if (DroneConstant.PERMISSION_PAGE.equals(p.getType()) && DroneConstant.LEVEL_TWO.equals(p.getLevel())) {
                secondMenuList.add(p);
            }
        }
        //筛选二级页面拥有的按钮权限
        List<Permission> buttonPermissions = new ArrayList<>();
        for (Permission p : list) {
            if (DroneConstant.PERMISSION_OPERATION.equals(p.getType()) && DroneConstant.LEVEL_THREE.equals(p.getLevel())) {
                buttonPermissions.add(p);
            }
        }

        //匹配二级页面拥有权限
        for (Permission p : secondMenuList) {
            List<String> permTypes = new ArrayList<>();
            for (Permission pe : buttonPermissions) {
                if (p.getId().equals(pe.getParentId())) {
                    permTypes.add(pe.getButtonType());
                }
            }
            p.setPermTypes(permTypes);
        }
        //匹配一级页面拥有二级页面
        for (Permission p : menuList) {
            List<Permission> secondMenu = new ArrayList<>();
            for (Permission pe : secondMenuList) {
                if (p.getId().equals(pe.getParentId())) {
                    secondMenu.add(pe);
                }
            }
            p.setChildren(secondMenu);
        }

        // 缓存
        redisTemplate.opsForValue().set(key, new Gson().toJson(menuList));
        return AjaxResponse.success(menuList);
    }

    // @RequestMapping(value = "/getAllList", method = RequestMethod.POST)
    //@ApiOperation(value = "获取权限菜单树")
    public AjaxResponse getAllList() {

        //一级
        List<Permission> list = permissionService.findByLevelOrderBySortOrder(DroneConstant.LEVEL_ONE);
        //二级
        for (Permission p1 : list) {
            List<Permission> children1 = permissionService.findByParentIdOrderBySortOrder(p1.getId());
            p1.setChildren(children1);
            //三级
            for (Permission p2 : children1) {
                List<Permission> children2 = permissionService.findByParentIdOrderBySortOrder(p2.getId());
                p2.setChildren(children2);
            }
        }
        return AjaxResponse.success(list);
    }


    @RequestMapping(value = "/getPermissionTree", method = RequestMethod.POST)
    @ApiOperation(value = "获取权限菜单树")
    public AjaxResponse getPermissionTree() {


        log.info(" 获取权限菜单树    ");

       return  permissionService.getPermissionTree();

       /* //一级
        List<Permission> list = permissionService.findByLevelOrderBySortOrder(DroneConstant.LEVEL_ONE);
        //二级
        for (Permission p1 : list) {
            List<Permission> children1 = permissionService.findByParentIdOrderBySortOrder(p1.getId());
            p1.setChildren(children1);
            //三级
            for (Permission p2 : children1) {
                List<Permission> children2 = permissionService.findByParentIdOrderBySortOrder(p2.getId());
                p2.setChildren(children2);
            }
        }
        return AjaxResponse.success(list);*/
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加权限")
    @CacheEvict(key = "'menuList'")
    public AjaxResponse add(@ModelAttribute Permission permission) {

        // 判断拦截请求的操作权限按钮名是否已存在
        if (DroneConstant.PERMISSION_OPERATION.equals(permission.getType())) {
            List<Permission> list = permissionService.findByTitle(permission.getTitle());
            if (list != null && list.size() > 0) {
                return AjaxResponse.failed("名称已存在");
            }
        }
        User currUser = securityUtil.getCurrUser();
        permission.setCreateBy(currUser.getUsername());
        permission.setCreateTime(new Date());
        Permission u = permissionService.save(permission);
        //重新加载权限
        mySecurityMetadataSource.loadResourceDefine();
        //手动删除缓存
        redisTemplate.delete("permission::allList");
        permissionService.delPermissionTree();
        return AjaxResponse.success(u);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "编辑权限")
    public AjaxResponse edit(@ModelAttribute Permission permission) {

        // 判断拦截请求的操作权限按钮名是否已存在
        if (DroneConstant.PERMISSION_OPERATION.equals(permission.getType())) {
            // 若名称修改
            Permission p = permissionService.get(permission.getId());
            if (!p.getTitle().equals(permission.getTitle())) {
                List<Permission> list = permissionService.findByTitle(permission.getTitle());
                if (list != null && list.size() > 0) {
                    return AjaxResponse.failed("名称已存在");
                }
            }
        }
        User currUser = securityUtil.getCurrUser();
        permission.setUpdateBy(currUser.getUsername());
        permission.setUpdateTime(new Date());
        Permission u = permissionService.update(permission);
        //重新加载权限
        mySecurityMetadataSource.loadResourceDefine();
        //手动批量删除缓存
        Set<String> keys = redisTemplate.keys("userPermission:" + "*");
        redisTemplate.delete(keys);
        Set<String> keysUser = redisTemplate.keys("user:" + "*");
        redisTemplate.delete(keysUser);
        Set<String> keysUserMenu = redisTemplate.keys("permission::userMenuList:*");
        redisTemplate.delete(keysUserMenu);
        redisTemplate.delete("permission::allList");
        permissionService.delPermissionTree();
        return AjaxResponse.success(u);
    }

    @RequestMapping(value = "/delByIds/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过id删除")
    @CacheEvict(key = "'menuList'")
    public AjaxResponse delByIds(@PathVariable String[] ids) {

        for (String id : ids) {
            List<RolePermission> list = rolePermissionService.findByPermissionId(Integer.valueOf(id));
            if (list != null && list.size() > 0) {
                return AjaxResponse.failed("删除失败，包含正被角色使用关联的菜单或权限");
            }
        }
        for (String id : ids) {
            permissionService.delete(Integer.valueOf(id));
        }
        //重新加载权限
        mySecurityMetadataSource.loadResourceDefine();
        //手动删除缓存
        redisTemplate.delete("permission::allList");
        permissionService.delPermissionTree();
        return AjaxResponse.success("批量通过id删除数据成功");
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "通过标题搜索菜单")
    public AjaxResponse searchPermissionList(@RequestParam String title) {

        List<Permission> list = permissionService.findByTitleLikeOrderBySortOrder("%" + title + "%");
        return AjaxResponse.success(list);
    }


}
