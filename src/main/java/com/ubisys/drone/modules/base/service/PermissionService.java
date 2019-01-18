package com.ubisys.drone.modules.base.service;


import com.ubisys.drone.basetemplate.DroneBaseService;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.modules.base.entity.Permission;
import com.ubisys.drone.modules.base.model.ZTreeModel;

import java.util.List;

/**
 * <p>Title: 权限接口</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/17 17:59
 */
public interface PermissionService extends DroneBaseService<Permission, Integer> {

    /**
     * 通过层级查找
     * 默认升序
     *
     * @param level
     * @return
     */
    List<Permission> findByLevelOrderBySortOrder(Integer level);




    /**
     * 通过层级查找  ztree  使用
     * 默认升序
     *
     * @param level  层级
     * @return
     */
    List<ZTreeModel> findByLevelOrderBySortOrderForZtree(Integer level);



    /**
     * 通过parendId查找  ztree  使用
     *
     * @param parentId
     * @return
     */
    List<ZTreeModel> findByParentIdOrderBySortOrderForZtree(Integer parentId);

    /**
     * 通过parendId查找
     *
     * @param parentId
     * @return
     */
    List<Permission> findByParentIdOrderBySortOrder(Integer parentId);

    /**
     * 通过类型和状态获取
     *
     * @param type
     * @param status
     * @return
     */
    List<Permission> findByTypeAndStatusOrderBySortOrder(Integer type, Integer status);

    /**
     * 通过名称获取
     *
     * @param title
     * @return
     */
    List<Permission> findByTitle(String title);

    /**
     * 模糊搜索
     *
     * @param title
     * @return
     */
    List<Permission> findByTitleLikeOrderBySortOrder(String title);


    /**
     * 获取系统的菜单树， 优先从redis  中取
     *
     * @return
     */
    AjaxResponse getPermissionTree();


    /**
     * 清除redis  中的缓存菜单
     * @return
     */
    AjaxResponse delPermissionTree();
}