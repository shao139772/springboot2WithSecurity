package com.ubisys.drone.modules.base.controller.scaffold;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ubisys.drone.common.constant.DroneConstant;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.common.utils.SecurityUtil;
import com.ubisys.drone.modules.base.entity.Department;
import com.ubisys.drone.modules.base.entity.User;
import com.ubisys.drone.modules.base.service.DepartmentService;
import com.ubisys.drone.modules.base.service.RoleDepartmentService;
import com.ubisys.drone.modules.base.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * <p>Title: 部门管理</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/24 15:31
 */
@Slf4j
@RestController
@Api(description = "部门管理接口")
@RequestMapping("/drone/department")
@CacheConfig(cacheNames = "department")
@Transactional
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleDepartmentService roleDepartmentService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SecurityUtil securityUtil;

    @RequestMapping(value = "/getByParentId/{parentId}", method = RequestMethod.GET)
    @ApiOperation(value = "通过id获取部门")
    public AjaxResponse getByParentId(@PathVariable String parentId,
                                      @ApiParam("是否开始数据权限过滤") @RequestParam(required = false, defaultValue = "true") Boolean openDataFilter) {

        List<Department> list = new ArrayList<>();
        User u = securityUtil.getCurrUser();
        String key = "department::" + parentId + ":" + u.getId() + "_" + openDataFilter;
        String v = redisTemplate.opsForValue().get(key);
        if (StrUtil.isNotBlank(v)) {
            list = new Gson().fromJson(v, new TypeToken<List<Department>>() {
            }.getType());
            return AjaxResponse.success(list);
        }
        list = departmentService.findByParentIdOrderBySortOrder(Integer.valueOf(parentId), openDataFilter);
        // lambda表达式
        list.forEach(item -> {
            if (!DroneConstant.PARENT_ID.equals(item.getParentId())) {
                Department parent = departmentService.get(Integer.valueOf(item.getParentId()));
                item.setParentTitle(parent.getTitle());
            } else {
                item.setParentTitle("一级部门");
            }
        });
        redisTemplate.opsForValue().set(key, new Gson().toJson(list));
        return AjaxResponse.success(list);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加部门")
    public AjaxResponse add(@ModelAttribute Department department) {

        Department d = departmentService.save(department);
        // 同步该节点缓存
        User u = securityUtil.getCurrUser();
        Set<String> keys = redisTemplate.keys("department::" + department.getParentId() + ":*");
        redisTemplate.delete(keys);
        // 如果不是添加的一级 判断设置上级为父节点标识
        if (!DroneConstant.PARENT_ID.equals(department.getParentId())) {
            Department parent = departmentService.get(Integer.valueOf(department.getParentId()));
            if (parent.getIsParent() == null || !parent.getIsParent()) {
                parent.setIsParent(true);
                departmentService.update(parent);
                // 更新上级节点的缓存
                Set<String> keysParent = redisTemplate.keys("department::" + parent.getParentId() + ":*");
                redisTemplate.delete(keysParent);
            }
        }
        return AjaxResponse.success("添加成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "编辑部门")
    public AjaxResponse edit(@ModelAttribute Department department) {

        Department d = departmentService.update(department);
        // 手动删除所有部门缓存
        Set<String> keys = redisTemplate.keys("department:" + "*");
        redisTemplate.delete(keys);
        // 删除所有用户缓存
        Set<String> keysUser = redisTemplate.keys("user:" + "*");
        redisTemplate.delete(keysUser);
        return AjaxResponse.success("编辑成功");
    }

    @RequestMapping(value = "/delByIds/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过id删除部门")
    public AjaxResponse delByIds(@PathVariable String[] ids) {

        for (String id : ids) {
            List<User> list = userService.findByDepartmentId(Integer.valueOf(id));
            if (list != null && list.size() > 0) {
                return AjaxResponse.failed("删除失败，包含正被用户使用关联的部门");
            }
        }
        for (String id : ids) {
            departmentService.delete(Integer.valueOf(id));
            // 删除关联数据权限
            roleDepartmentService.deleteByDepartmentId(Integer.valueOf(id));
        }
        // 手动删除所有部门缓存
        Set<String> keys = redisTemplate.keys("department:" + "*");
        redisTemplate.delete(keys);
        // 删除数据权限缓存
        Set<String> keysUserRoleData = redisTemplate.keys("userRole::depIds:" + "*");
        redisTemplate.delete(keysUserRoleData);
        return AjaxResponse.success("批量通过id删除数据成功");
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ApiOperation(value = "部门名模糊搜索")
    public AjaxResponse searchByTitle(@RequestParam String title,
                                      @ApiParam("是否开始数据权限过滤") @RequestParam(required = false, defaultValue = "true") Boolean openDataFilter) {

        List<Department> list = departmentService.findByTitleLikeOrderBySortOrder("%" + title + "%", openDataFilter);
        // lambda表达式
        list.forEach(item -> {
            if (!DroneConstant.PARENT_ID.equals(item.getParentId())) {
                Department parent = departmentService.get(Integer.valueOf(item.getParentId()));
                item.setParentTitle(parent.getTitle());
            } else {
                item.setParentTitle("一级部门");
            }
        });
        return AjaxResponse.success(list);
    }
}
