package com.ubisys.drone.modules.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ubisys.drone.basetemplate.DroneBaseEntity;
import com.ubisys.drone.common.constant.DroneConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;

/**
 * 部门
 */
@Data
@Entity
@Table(name = "sys_department")
@TableName("sys_department")
public class Department extends DroneBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门名称")
    private String title;

    @ApiModelProperty(value = "父id")
    private Integer parentId;

    @ApiModelProperty(value = "是否为父节点(含子节点) 默认false")
    private Boolean isParent = false;

    @ApiModelProperty(value = "排序值")
    @Column(precision = 10, scale = 2)
    private BigDecimal sortOrder;

    @ApiModelProperty(value = "是否启用 0启用 -1禁用")
    private Integer status = DroneConstant.STATUS_NORMAL;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "父节点名称")
    private String parentTitle;
}