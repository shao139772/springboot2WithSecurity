package com.ubisys.drone.modules.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ubisys.drone.basetemplate.DroneBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author miaomiao
 */
@Data
@Entity
@Table(name = "lz_sensor")
@TableName("lz_sensor")
public class Sensor extends DroneBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "站点名称")
    @Column(name = "name")
    @TableField("name")
    private String name;


    @ApiModelProperty(value = "站点编号")
    @Column(name = "code")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "站点ip")
    @Column(name = "ip")
    private String ip;


    @ApiModelProperty(value = "站点经度")
    @Column(name = "lng")
    private Double lng;


    @ApiModelProperty(value = "站点维度")
    @Column(name = "lat")
    private Double lat;

    @ApiModelProperty(value = "生产厂家")
    @Column(name = "supplier")
    private String supplier;

    @ApiModelProperty(value = "组网时间")
    @Column(name = "networkingTime")
    private Date networkingTime;

    @ApiModelProperty(value = "负责人")
    @Column(name = "person")
    private String person;

    @ApiModelProperty(value = "温度")
    @Column(name = "temperature")
    private String temperature;

    @ApiModelProperty(value = "电源状态")
    @Column(name = "powerState")
    private Integer powerState;

    @ApiModelProperty(value = "网络状态")
    @Column(name = "networkState")
    private Integer networkState;

    @ApiModelProperty(value = "备注")
    @Column(name = "remarks")
    private String remarks;


    @Override
    public String toString() {
        return "LzSensor{" +
                ", code=" + code +
                ", name=" + name +
                "}";
    }
}