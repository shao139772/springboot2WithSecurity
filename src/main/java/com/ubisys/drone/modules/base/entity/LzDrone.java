package com.ubisys.drone.modules.base.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author cw
 * @since 2018-11-06
 */
@TableName("lz_drone")
public class LzDrone extends Model<LzDrone> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 暂时保留，无人机是否处于被攻击状态
     */
    private Integer attacking;
    /**
     * 是否能攻击，bool
     */
    @TableField("can_attack")
    private Integer canAttack;
    /**
     * 是否可以被接管, bool
     */
    @TableField("can_takeover")
    private Integer canTakeover;
    @TableField("created_time")
    private Date createdTime;
    @TableField("deleted_time")
    private Date deletedTime;
    /**
     * 图片地址
     */
    private String image;
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
     * 无人机id
     */
    private String droneid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getDroneid() {
        return droneid;
    }

    public void setDroneid(String droneid) {
        this.droneid = droneid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "LzDrone{" +
        ", id=" + id +
        ", attacking=" + attacking +
        ", canAttack=" + canAttack +
        ", canTakeover=" + canTakeover +
        ", createdTime=" + createdTime +
        ", deletedTime=" + deletedTime +
        ", image=" + image +
        ", name=" + name +
        ", state=" + state +
        ", whitelisted=" + whitelisted +
        ", droneid=" + droneid +
        "}";
    }
}
