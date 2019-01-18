package com.ubisys.drone.modules.base.serviceimpl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONArray;
import com.ubisys.drone.common.constant.DroneConstant;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.modules.base.dao.PermissionDao;
import com.ubisys.drone.modules.base.entity.Permission;
import com.ubisys.drone.modules.base.model.ZTreeModel;
import com.ubisys.drone.modules.base.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: 权限接口实现</p>
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
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public PermissionDao getRepository() {
        return permissionDao;
    }

    @Override
    public List<Permission> findByLevelOrderBySortOrder(Integer level) {

        return permissionDao.findByLevelOrderBySortOrder(level);
    }


    /**
     * 通过层级查找  ztree  使用
     * 默认升序
     *
     * @param level 层级
     * @return
     */
    @Override
    public List<ZTreeModel> findByLevelOrderBySortOrderForZtree(Integer level) {
        List<ZTreeModel> zTreeModels = new ArrayList<ZTreeModel>();
        List<Permission> list = permissionDao.findByStatusOrderBySortOrder(DroneConstant.STATUS_NORMAL);
        for (Permission permission : list) {
            ZTreeModel treeModel = new ZTreeModel();
            BeanUtil.copyProperties(permission,treeModel);
            treeModel.setName(permission.getTitle());
            treeModel.setpId(permission.getParentId());
            treeModel.setOpen(true);
            treeModel.setZurl(permission.getUrl());
            zTreeModels.add(treeModel);
        }
        return zTreeModels;
    }


    /**
     * 通过parendId查找  ztree  使用
     *
     * @param parentId
     * @return
     */
    @Override
    public List<ZTreeModel> findByParentIdOrderBySortOrderForZtree(Integer parentId) {
        List<ZTreeModel> zTreeModels = new ArrayList<ZTreeModel>();
        List<Permission> list = findByParentIdOrderBySortOrder(parentId);
        for (Permission permission : list) {
            ZTreeModel treeModel = new ZTreeModel();
            treeModel.setName(permission.getTitle());
            treeModel.setId(permission.getId());
            treeModel.setpId(permission.getParentId());
            treeModel.setIcon(permission.getIcon());
            treeModel.setLevel(permission.getLevel());
            treeModel.setOpen(false);
            zTreeModels.add(treeModel);
        }
        return zTreeModels;
    }

    @Override
    public List<Permission> findByParentIdOrderBySortOrder(Integer parentId) {

        return permissionDao.findByParentIdOrderBySortOrder(parentId);
    }

    @Override
    public List<Permission> findByTypeAndStatusOrderBySortOrder(Integer type, Integer status) {

        return permissionDao.findByTypeAndStatusOrderBySortOrder(type, status);
    }

    @Override
    public List<Permission> findByTitle(String title) {

        return permissionDao.findByTitle(title);
    }

    @Override
    public List<Permission> findByTitleLikeOrderBySortOrder(String title) {

        return permissionDao.findByTitleLikeOrderBySortOrder(title);
    }


    /**
     * 获取系统的菜单树， 优先从redis  中取
     *
     * @return
     */
    @Override
    public AjaxResponse getPermissionTree() {
        log.info(" getPermissionTree  ");
        String permissons = stringRedisTemplate.opsForValue().get(DroneConstant.PERMISSON_TREE);
        if (StringUtils.isNotBlank(permissons)
                ) {
            return AjaxResponse.success(JSONArray.parseArray(permissons, ZTreeModel.class));
        }
        log.info(" redis  中未取得菜单信息，准备从DB中获取 ");
        List<ZTreeModel> list = findByLevelOrderBySortOrderForZtree(DroneConstant.LEVEL_ONE);
        //存入redis
        stringRedisTemplate.opsForValue().set(DroneConstant.PERMISSON_TREE, JSONArray.toJSONString(list));
        return AjaxResponse.success(list);
    }


    /**
     * 清除redis  中的缓存菜单
     * @return
     */
    @Override
    public AjaxResponse delPermissionTree() {
        log.info("  清除清除redis  中的缓存数据 ");
        stringRedisTemplate.delete(DroneConstant.PERMISSON_TREE);
        return AjaxResponse.success();
    }
}