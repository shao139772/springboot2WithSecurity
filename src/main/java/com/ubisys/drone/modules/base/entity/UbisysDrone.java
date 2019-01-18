package com.ubisys.drone.modules.base.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ubisys.drone.basetemplate.DroneBaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Jhon Davis
 * 历史报警信息
 */
@Data
@Entity
@Table(name = "lz_drone_history")
@TableName("lz_drone_history")
public class UbisysDrone extends DroneBaseEntity {

    private static final long serialVersionUID = 1L;

    //起始时间
    @Column(name = "created_time")
    @TableField("created_time")
    private Date createdTime;

    //结束时间
    @TableField(value = "deleted_time")
    @Column(name = "deleted_time")
    private Date deletedTime;

    /**
     * 序列号
     */
    @TableField(value = "serial_number")
    @Column(name = "serial_number")
    private String serialNumber;

    /**
     * 厂家型号名称
     */
    @TableField(value = "name")
    @Column(name = "name")
    private String name;

    /**
     * 芯片号id
     */
    @TableField(value = "droneid")
    @Column(name = "droneid")
    private String droneid;

    /**
     * 侦测到的频率
     */
    @TableField(value = "detected_freq_khz")
    @Column(name = "detected_freq_khz")
    private Long detectedFreqKhz;

    /**
     * 经度
     */
    @TableField(value = "lng")
    @Column(name = "lng")
    private Double lng;

    /**
     * 维度
     */
    @TableField(value = "lat")
    @Column(name = "lat")
    private Double lat;

    /**
     * 维度
     */
    @TableField(value = "ttl")
    @Column(name = "ttl")
    private Double ttl;

    private Integer lzdroneId;

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getDeletedTime() {
        return deletedTime;
    }

    public void setDeletedTime(Date deletedTime) {
        this.deletedTime = deletedTime;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDroneid() {
        return droneid;
    }

    public void setDroneid(String droneid) {
        this.droneid = droneid;
    }

    public Long getDetectedFreqKhz() {
        return detectedFreqKhz;
    }

    public void setDetectedFreqKhz(Long detectedFreqKhz) {
        this.detectedFreqKhz = detectedFreqKhz;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getTtl() {
        return ttl;
    }

    public void setTtl(Double ttl) {
        this.ttl = ttl;
    }

    public Integer getLzdroneId() {
        return lzdroneId;
    }

    public void setLzdroneId(Integer lzdroneId) {
        this.lzdroneId = lzdroneId;
    }

    @Override
    public String toString() {
        return "UbisysDrone{" +
                "createdTime=" + createdTime +
                ", deletedTime=" + deletedTime +
                ", serialNumber='" + serialNumber + '\'' +
                ", name='" + name + '\'' +
                ", droneid='" + droneid + '\'' +
                ", detectedFreqKhz=" + detectedFreqKhz +
                ", lng=" + lng +
                ", lat=" + lat +
                ", ttl=" + ttl +
                ", lzdroneId=" + lzdroneId +
                '}';
    }
}