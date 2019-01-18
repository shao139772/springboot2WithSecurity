package com.ubisys.drone.modules.base.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ubisys.drone.basetemplate.DroneBaseEntity;
import com.ubisys.drone.modules.base.entity.LzDrone;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author miaomiao
 */
public class DroneHistory {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    /**
     * 打击时间
     */
    private Date attackTime;

    /**
     * 暂时保留，无人机是否处于被攻击状态  1  被攻击  2未被攻击
     */
    private Integer attacking;

    /**
     * 是否能攻击，bool  1 能打击   2 不能打击
     */
    private Integer canAttack;

    /**
     * 是否可以被接管, bool   1  可以接管  2  不可以接管
     */
    private Integer canTakeover;

    private Date createdTime;

    private Date deletedTime;

    /**
     * 侦测到的频率
     */
    private Integer detectedFreqKhz;

    /**
     * 侦测到的时间
     */
    private Date detectedTime;

    /**
     * 无人机id
     */
    private String droneid;

    /**
     * 图片地址
     */
    private String image;

    /**
     * 维度
     */
    private Double lat;

    /**
     * 经度
     */
    private Double lng;

    /**
     * 备注
     */
    private String memo;

    /**
     * 无人机名称
     */
    private String name;

    /**
     * 无人机的状态，字符串类型，取值：attacking/danger
     */
    private String state;

    /**
     * 此无人机在whitelist中, bool   1 是  2 否
     */
    private Integer whitelisted;

    /**
     * 无人机持续时间
     */
    private Long ttl;

    /**
     * 日期（临时字段）
     */
    private String time;

    /**
     * 打击数量（临时字段）
     */
    private String count;

    /**
     * 持续时间（临时字段）
     */
    private String duration;

    /**
     * 5.8HZ数量（临时字段）
     */
    private String fiveHZ;

    /**
     * 2.4HZ数量（临时字段）
     */
    private String twoHZ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getAttackTime() {
        return attackTime;
    }

    public void setAttackTime(Date attackTime) {
        this.attackTime = attackTime;
    }

    public Integer getAttacking() {
        return attacking;
    }

    public void setAttacking(Integer attacking) {
        this.attacking = attacking;
    }

    public Integer getCanAttack() {
        return canAttack;
    }

    public void setCanAttack(Integer canAttack) {
        this.canAttack = canAttack;
    }

    public Integer getCanTakeover() {
        return canTakeover;
    }

    public void setCanTakeover(Integer canTakeover) {
        this.canTakeover = canTakeover;
    }

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

    public Integer getDetectedFreqKhz() {
        return detectedFreqKhz;
    }

    public void setDetectedFreqKhz(Integer detectedFreqKhz) {
        this.detectedFreqKhz = detectedFreqKhz;
    }

    public Date getDetectedTime() {
        return detectedTime;
    }

    public void setDetectedTime(Date detectedTime) {
        this.detectedTime = detectedTime;
    }

    public String getDroneid() {
        return droneid;
    }

    public void setDroneid(String droneid) {
        this.droneid = droneid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getWhitelisted() {
        return whitelisted;
    }

    public void setWhitelisted(Integer whitelisted) {
        this.whitelisted = whitelisted;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getFiveHZ() {
        return fiveHZ;
    }

    public void setFiveHZ(String fiveHZ) {
        this.fiveHZ = fiveHZ;
    }

    public String getTwoHZ() {
        return twoHZ;
    }

    public void setTwoHZ(String twoHZ) {
        this.twoHZ = twoHZ;
    }
}