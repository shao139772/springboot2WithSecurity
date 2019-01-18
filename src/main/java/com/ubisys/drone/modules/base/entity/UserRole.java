package com.ubisys.drone.modules.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ubisys.drone.basetemplate.DroneBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <p>Title: 用户----角色</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/17 17:59
 */
@Data
@Entity
@Table(name = "sys_user_role")
@TableName("sys_user_role")
public class UserRole extends DroneBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户唯一id")
    private Integer userId;

    @ApiModelProperty(value = "角色唯一id")
    private Integer roleId;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "角色名")
    private String roleName;
}
