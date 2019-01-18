package com.ubisys.drone.modules.base.controller.scaffold;

import com.ubisys.drone.common.constant.DroneConstant;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.common.utils.PageUtil;
import com.ubisys.drone.common.utils.SecurityUtil;
import com.ubisys.drone.common.vo.PageVo;
import com.ubisys.drone.common.vo.SearchVo;
import com.ubisys.drone.modules.base.entity.Role;
import com.ubisys.drone.modules.base.entity.User;
import com.ubisys.drone.modules.base.entity.UserRole;
import com.ubisys.drone.modules.base.service.DepartmentService;
import com.ubisys.drone.modules.base.service.RoleService;
import com.ubisys.drone.modules.base.service.UserRoleService;
import com.ubisys.drone.modules.base.service.UserService;
import com.ubisys.drone.modules.base.service.mybatis.IUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * <p>Title: 用户管理接口</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/24 10:22
 */
@Slf4j
@RestController
@Api(description = "用户接口")
@RequestMapping("/drone/user")
@CacheConfig(cacheNames = "user")
@Transactional
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private IUserRoleService iUserRoleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SecurityUtil securityUtil;

    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    @ApiOperation(value = "注册用户")
    public AjaxResponse regist(@ModelAttribute User u,
                               @RequestParam String verify,
                               @RequestParam String captchaId) {

        if (StringUtils.isBlank(verify) || StringUtils.isBlank(u.getUsername())
                || StringUtils.isBlank(u.getPassword())) {
            return AjaxResponse.failed("缺少必需表单字段");
        }

        //验证码
        String code = redisTemplate.opsForValue().get(captchaId);
      /*  if (StringUtils.isBlank(code)) {
            return AjaxResponse.failed("验证码已过期，请重新获取");
        }*/

      /*  if (!verify.toLowerCase().equals(code.toLowerCase())) {
            log.error("注册失败，验证码错误：code:" + verify + ",redisCode:" + code.toLowerCase());
            return AjaxResponse.failed("验证码输入错误");
        }*/

        if (userService.findByUsername(u.getUsername()) != null) {
            return AjaxResponse.failed("该用户名已被注册");
        }
        //删除缓存
        redisTemplate.delete("user::" + u.getUsername());

        String encryptPass = new BCryptPasswordEncoder().encode(u.getPassword());
        u.setPassword(encryptPass);
        u.setType(DroneConstant.USER_TYPE_NORMAL);
        User user = userService.save(u);
        if (user == null) {
            return AjaxResponse.failed("注册失败");
        }
        // 默认角色
        List<Role> roleList = roleService.findByDefaultRole(true);
        if (roleList != null && roleList.size() > 0) {
            for (Role role : roleList) {
                UserRole ur = new UserRole();
                ur.setUserId(user.getId());
                ur.setRoleId(role.getId());
                iUserRoleService.insert(ur);
            }
        }

        return AjaxResponse.success(user);
    }

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ApiOperation(value = "获取当前登录用户接口")
    public AjaxResponse getUserInfo() {

        User u = securityUtil.getCurrUser();
        // 清除持久上下文环境 避免后面语句导致持久化
        entityManager.clear();
        u.setPassword(null);
        return AjaxResponse.success(u);
    }

    @RequestMapping(value = "/unlock", method = RequestMethod.POST)
    @ApiOperation(value = "解锁验证密码")
    public AjaxResponse unLock(@RequestParam String password) {

        User u = securityUtil.getCurrUser();
        if (!new BCryptPasswordEncoder().matches(password, u.getPassword())) {
            return AjaxResponse.failed("密码不正确");
        }
        return AjaxResponse.success();
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户自己资料", notes = "用户名密码不会修改 需要username更新缓存")
    @CacheEvict(key = "#u.username")
    public AjaxResponse editOwn(@ModelAttribute User u) {

        User old = securityUtil.getCurrUser();
        u.setUsername(old.getUsername());
        u.setPassword(old.getPassword());
        User user = userService.update(u);
        if (user == null) {
            return AjaxResponse.failed("修改失败");
        }
        return AjaxResponse.success("修改成功");
    }

    /**
     * @param u
     * @param roles
     * @return
     */
    @RequestMapping(value = "/admin/edit", method = RequestMethod.POST)
    @ApiOperation(value = "管理员修改资料", notes = "需要通过id获取原用户信息 需要username更新缓存")
    @CacheEvict(key = "#u.username")
    public AjaxResponse edit(@ModelAttribute User u,
                             @RequestParam(required = false) String[] roles) {

        User old = userService.get(u.getId());
        //若修改了用户名
        if (!old.getUsername().equals(u.getUsername())) {
            //若修改用户名删除原用户名缓存
            redisTemplate.delete("user::" + old.getUsername());
            //判断新用户名是否存在
            if (userService.findByUsername(u.getUsername()) != null) {
                return AjaxResponse.failed("该用户名已被存在");
            }
            //删除缓存
            redisTemplate.delete("user::" + u.getUsername());
        }

        // 若修改了手机和邮箱判断是否唯一
        if (!old.getMobile().equals(u.getMobile()) && userService.findByMobile(u.getMobile()) != null) {
            return AjaxResponse.failed("该手机号已绑定其他账户");
        }
        if (!old.getEmail().equals(u.getEmail()) && userService.findByMobile(u.getEmail()) != null) {
            return AjaxResponse.failed("该邮箱已绑定其他账户");
        }
        User currUser = securityUtil.getCurrUser();
        u.setPassword(old.getPassword());
        u.setUpdateBy(currUser.getUsername());
        u.setUpdateTime(new Date());
        User user = userService.update(u);
        if (user == null) {
            return AjaxResponse.failed("修改失败");
        }
        //删除该用户角色
        userRoleService.deleteByUserId(u.getId());
        if (roles != null && roles.length > 0) {
            //新角色
            for (String roleId : roles) {
                UserRole ur = new UserRole();
                ur.setRoleId(Integer.valueOf(roleId));
                ur.setUserId(u.getId());
                userRoleService.save(ur);
            }
        }
        //手动删除缓存
        redisTemplate.delete("userRole::" + u.getId());
        redisTemplate.delete("userRole::depIds:" + u.getId());
        return AjaxResponse.success("修改成功");
    }

    /**
     * 修改密码
     *
     * @param password 旧密码
     * @param newPass  新密码
     * @return
     */
    @RequestMapping(value = "/modifyPass", method = RequestMethod.POST)
    @ApiOperation(value = "修改密码")
    public AjaxResponse modifyPass(@ApiParam("旧密码") @RequestParam String password,
                                   @ApiParam("新密码") @RequestParam String newPass) {

        User user = securityUtil.getCurrUser();

        if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            return AjaxResponse.failed("旧密码不正确");
        }

        String newEncryptPass = new BCryptPasswordEncoder().encode(newPass);
        user.setPassword(newEncryptPass);
        userService.update(user);

        //手动更新缓存
        redisTemplate.delete("user::" + user.getUsername());

        return AjaxResponse.success("修改密码成功");
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.POST)
    @ApiOperation(value = "多条件分页获取用户列表")
    public AjaxResponse getByCondition(@ModelAttribute User user,
                                       @ModelAttribute SearchVo searchVo,
                                       @ModelAttribute PageVo pageVo) {

        Page<User> page = userService.findByCondition(user, searchVo, PageUtil.initPage(pageVo));
        for (User u : page.getContent()) {
            // 关联角色
            List<Role> lists = iUserRoleService.findByUserId(u.getId());
            u.setRoles(lists);
            // 清除持久上下文环境 避免后面语句导致持久化
            entityManager.clear();
            u.setPassword(null);
        }

        AjaxResponse response = AjaxResponse.success();
        response.setTotal((int) page.getTotalElements());
        response.setRows(page.getContent());
        return response;
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    @ApiOperation(value = "获取全部用户数据")
    public AjaxResponse getByCondition() {

        List<User> list = userService.getAll();
        for (User u : list) {
            // 清除持久上下文环境 避免后面语句导致持久化
            entityManager.clear();
            u.setPassword(null);
        }
        return AjaxResponse.success(list);
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加用户")
    public AjaxResponse regist(@ModelAttribute User u,
                               @RequestParam(required = false) String[] roles) {

        if (StringUtils.isBlank(u.getUsername()) || StringUtils.isBlank(u.getPassword())) {
            return AjaxResponse.failed("缺少必需表单字段");
        }

        if (userService.findByUsername(u.getUsername()) != null) {
            return AjaxResponse.failed("该用户名已被注册");
        }
        //删除缓存
        redisTemplate.delete("user::" + u.getUsername());

        String encryptPass = new BCryptPasswordEncoder().encode(u.getPassword());
        u.setPassword(encryptPass);
        User currUser = securityUtil.getCurrUser();
        u.setCreateBy(currUser.getUsername());
        u.setCreateTime(new Date());
        User user = userService.save(u);
        if (user == null) {
            return AjaxResponse.failed("添加失败");
        }
        if (roles != null && roles.length > 0) {
            //添加角色
            for (String roleId : roles) {
                UserRole ur = new UserRole();
                ur.setUserId(u.getId());
                ur.setRoleId(Integer.valueOf(roleId));
                userRoleService.save(ur);
            }
        }

        return AjaxResponse.success(user);
    }

    @RequestMapping(value = "/admin/disable/{userId}", method = RequestMethod.POST)
    @ApiOperation(value = "后台禁用用户")
    public AjaxResponse disable(@ApiParam("用户唯一id标识") @PathVariable String userId) {

        User user = userService.get(Integer.valueOf(userId));
        if (user == null) {
            return AjaxResponse.failed("通过userId获取用户失败");
        }
        user.setStatus(DroneConstant.USER_STATUS_LOCK);
        userService.update(user);
        //手动更新缓存
        redisTemplate.delete("user::" + user.getUsername());
        return AjaxResponse.success();
    }

    @RequestMapping(value = "/admin/enable/{userId}", method = RequestMethod.POST)
    @ApiOperation(value = "后台启用用户")
    public AjaxResponse enable(@ApiParam("用户唯一id标识") @PathVariable String userId) {

        User user = userService.get(Integer.valueOf(userId));
        if (user == null) {
            return AjaxResponse.failed("通过userId获取用户失败");
        }
        user.setStatus(DroneConstant.USER_STATUS_NORMAL);
        userService.update(user);
        //手动更新缓存
        redisTemplate.delete("user::" + user.getUsername());
        return AjaxResponse.success();
    }

    @RequestMapping(value = "/delByIds/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过ids删除")
    public AjaxResponse delAllByIds(@PathVariable String[] ids) {

        for (String id : ids) {
            User u = userService.get(Integer.valueOf(id));
            //删除缓存
            redisTemplate.delete("user::" + u.getUsername());
            redisTemplate.delete("userRole::" + u.getId());
            redisTemplate.delete("userRole::depIds:" + u.getId());
            redisTemplate.delete("permission::userMenuList:" + u.getId());
            userService.delete(Integer.valueOf(id));
            //删除关联角色
            userRoleService.deleteByUserId(Integer.valueOf(id));
        }
        return AjaxResponse.success("批量通过id删除数据成功");
    }


}
