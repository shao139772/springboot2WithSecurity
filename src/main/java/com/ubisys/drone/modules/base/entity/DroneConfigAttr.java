package com.ubisys.drone.modules.base.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.ubisys.drone.basetemplate.DroneBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author miaomiao
 */
@Data
@Entity
@Table(name = "sys_drone_config_attr")
@TableName("sys_drone_config_attr")
public class DroneConfigAttr extends DroneBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性名")
    @Column(name = "attrkey")
    private String attrkey;
    @ApiModelProperty(value = "属性值")
    @Column(name = "attrvalue")
    private String attrvalue;
    @ApiModelProperty(value = "配置code")
    @Column(name = "conf_code")
    private String confCode;
}