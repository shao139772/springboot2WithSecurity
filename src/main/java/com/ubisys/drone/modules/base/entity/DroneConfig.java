package com.ubisys.drone.modules.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ubisys.drone.basetemplate.DroneBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 无人机配置系统
 *
 * @author miaomiao
 */
@Data
@Entity
@Table(name = "sys_drone_config")
@TableName("sys_drone_config")
public class DroneConfig extends DroneBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 配置code
     */
    @ApiModelProperty(name = "配置编码")
    @Column(name = "config_code")
    private String configCode;
    /**
     * 配置名称
     */
    @ApiModelProperty(name = "配置名称")
    @Column(name = "config_name")
    private String configName;


    @Transient
    @TableField(exist = false)
    private List<DroneConfigAttr> attrs;

}