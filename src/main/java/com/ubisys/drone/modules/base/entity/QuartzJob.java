package com.ubisys.drone.modules.base.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.ubisys.drone.basetemplate.DroneBaseEntity;
import com.ubisys.drone.common.constant.DroneConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 定时任务实体类
 */
@Data
@Entity
@Table(name = "t_quartz_job")
@TableName("t_quartz_job")
public class QuartzJob extends DroneBaseEntity {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty(value = "任务类名")
    private String jobClassName;

    @ApiModelProperty(value = "cron表达式")
    private String cronExpression;

    @ApiModelProperty(value = "参数")
    private String parameter;

    @ApiModelProperty(value = "备注")
    private String description;

    @ApiModelProperty(value = "状态 0正常 -1停止")
    private Integer status = DroneConstant.STATUS_NORMAL;
}
