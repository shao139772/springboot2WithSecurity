package com.ubisys.drone.modules.base.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.ubisys.drone.basetemplate.DroneBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 字典表
 *
 * @author miaomiao
 */
@Data
@Entity
@Table(name = "sys_dict_line")
@TableName("sys_dict_line")
public class DictLine extends DroneBaseEntity {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "字典名称")
    private String title;

    @ApiModelProperty(value = "字典类型")
    private String type;

    @ApiModelProperty(value = "备注")
    private String description;

}