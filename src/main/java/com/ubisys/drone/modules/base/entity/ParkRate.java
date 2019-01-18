package com.ubisys.drone.modules.base.entity;

import com.ubisys.drone.basetemplate.DroneBaseEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author miaomiao
 */
@Data
@Entity
@Table(name = "t_park_rate")
@TableName("t_park_rate")
public class ParkRate extends DroneBaseEntity {

    private static final long serialVersionUID = 1L;

}