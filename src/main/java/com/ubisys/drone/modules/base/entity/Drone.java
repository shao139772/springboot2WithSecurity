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
 * @author miaomiao
 */
@Data
@Entity
@Table(name = "lz_drone")
@TableName("lz_drone")
public class Drone extends DroneBaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 暂时保留，无人机是否处于被攻击状态  1  被攻击  2未被攻击
     */
    private Integer attacking;
    /**
     * 是否能攻击，bool  1 能打击   2 不能打击
     */
    @TableField("can_attack")
    private Integer canAttack;
    /**
     * 是否可以被接管, bool   1  可以接管  2  不可以接管
     */
    @Column(name = "can_takeover")
    private Integer canTakeover;
    @Column(name = "created_time")
    private Date createdTime;
    @Column(name = "deleted_time")
    private Date deletedTime;
    /**
     * 图片地址
     */
    @Column(name = "image")
    private String image;
    /**
     * 无人机名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 无人机的状态，字符串类型，取值：attacking/danger
     */
    @Column(name = "state")
    private String state;
    /**
     * 此无人机在whitelist中, bool   1 是  2 否
     */
    @Column(name = "whitelisted")
    private Integer whitelisted;
    /**
     * 无人机id
     */
    @Column(name = "droneid")
    private String droneid;

    /**
     * 备注
     */
    @Column(name = "memo")
    private String memo;


    /**
     * 打击时间
     */
    @Column(name = "attack_time")
    private Date attackTime;


    /**
     * 侦测到的频率
     */
    @TableField(value = "detected_freq_khz")
    @Column(name = "detected_freq_khz")
    private Long detectedFreqKhz;

    /**
     * 侦测到的时间
     */
    @Column(name = "detected_time")
    private Date detectedTime;


    /**
     * 经度
     */
    @Column(name = "lng")
    private Double lng;

    /**
     * 维度
     */
    @Column(name = "lat")
    private Double lat;


    /**
     * 无人机持续时间
     */
    @Column(name = "ttl")
    private long ttl;
}