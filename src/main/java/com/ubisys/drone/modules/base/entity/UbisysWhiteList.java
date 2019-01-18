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
@Table(name = "ub_white_list")
@TableName("ub_white_list")
public class UbisysWhiteList extends DroneBaseEntity {

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

    @ApiModelProperty(value = "推送状态状态")
    @Column(name = "push_status")
    @TableField("push_status")
    private Integer pushStatus;

    @ApiModelProperty(value = "厂商名称")
    @Column(name = "vendor_name")
    @TableField("vendor_name")
    private String vendorName;

    @ApiModelProperty(value = "droneInfoId")
    @Column(name = "drone_info_id")
    @TableField("drone_info_id")
    private Integer droneInfoId;

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getChipNumber() {
        return chipNumber;
    }

    public void setChipNumber(String chipNumber) {
        this.chipNumber = chipNumber;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getUbDroneUseWay() {
        return ubDroneUseWay;
    }

    public void setUbDroneUseWay(String ubDroneUseWay) {
        this.ubDroneUseWay = ubDroneUseWay;
    }

    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Integer getDroneInfoId() {
        return droneInfoId;
    }

    public void setDroneInfoId(Integer droneInfoId) {
        this.droneInfoId = droneInfoId;
    }

    @Override
    public String toString() {
        return "UbisysWhiteList{" +
                "registrationTime=" + registrationTime +
                ", serialNumber='" + serialNumber + '\'' +
                ", chipNumber='" + chipNumber + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", ubDroneUseWay='" + ubDroneUseWay + '\'' +
                ", pushStatus=" + pushStatus +
                ", vendorName='" + vendorName + '\'' +
                ", droneInfoId=" + droneInfoId +
                '}';
    }
}