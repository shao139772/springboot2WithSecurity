package com.ubisys.drone.modules.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ubisys.drone.basetemplate.DroneBaseEntity;
import com.ubisys.drone.common.constant.DroneConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * <p>Title: 角色菜单</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/17 17:59
 */
@Data
@Entity
@Table(name = "sys_role")
@TableName("sys_role")
public class Role extends DroneBaseEntity {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "角色code")
    private String code;

    @ApiModelProperty(value = "角色名")
    private String name;

    @ApiModelProperty(value = "是否为注册默认角色")
    private Boolean defaultRole;

    @ApiModelProperty(value = "数据权限类型 0全部默认 1自定义")
    private Integer dataType = DroneConstant.DATA_TYPE_ALL;

    @ApiModelProperty(value = "备注")
    private String description;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "拥有权限")
    private List<RolePermission> permissions;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "拥有数据权限")
    private List<RoleDepartment> departments;

}
