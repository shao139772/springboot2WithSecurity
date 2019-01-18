package com.ubisys.drone.modules.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.ubisys.drone.basetemplate.DroneBaseEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Jhon Davis
 */
@Data
@Entity
@Table(name = "ub_drone_info")
@TableName("ub_drone_info")
public class UbisysDroneInfo extends DroneBaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登记日期")
    @Column(name = "registration_time")
    @TableField("registration_time")
    private Date registrationTime;


    @ApiModelProperty(value = "序列号")
    @Column(name = "serial_number")
    @TableField("serial_number")
    private String serialNumber;

    @ApiModelProperty(value = "芯片号")
    @Column(name = "chip_number")
    @TableField("chip_number")
    private String chipNumber;


    @ApiModelProperty(value = "有效开始时间")
    @Column(name = "start_time")
    @TableField("start_time")
    private Date startTime;

    @ApiModelProperty(value = "有效截止时间")
    @Column(name = "end_time")
    @TableField("end_time")
    private Date endTime;

    @ApiModelProperty(value = "用途")
    @Column(name = "ub_drone_use_way")
    @TableField("ub_drone_use_way")
    private String ubDroneUseWay;

    //0 新增(未提交)      1提交审核中      2成功     3驳回     4踢出白名单
    @ApiModelProperty(value = "状态")
    @Column(name = "ub_drone_status")
    @TableField("ub_drone_status")
    private Integer ubDroneStatus;

    @ApiModelProperty(value = "厂商名称")
    @Column(name = "vendor_name")
    @TableField("vendor_name")
    private String vendorName;

    @Override
    public String toString() {
        return "UbisysDroneInfo{" +
                "registrationTime=" + registrationTime +
                ", serialNumber='" + serialNumber + '\'' +
                ", chipNumber='" + chipNumber + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", ubDroneUseWay='" + ubDroneUseWay + '\'' +
                ", ubDroneStatus=" + ubDroneStatus +
                ", vendorName='" + vendorName + '\'' +
                '}';
    }
}