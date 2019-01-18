package com.ubisys.drone.modules.base.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author hqc
 * @since 2018-11-02
 */

public class DroneThirdFloor implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 无人机主键
     */
    @JSONField(name = "id")
    private String id;
    /**
     * 暂时保留，无人机是否处于被攻击状态
     */
  /*  @JSONField(name = "attacking")
    private boolean attacking;*/
    private boolean attacking;
    /**
     * 是否能攻击，bool
     */
    /*@JSONField(name = "can_attack")
    private boolean canAttack;*/
    private boolean can_attack;
    /**
     * 是否可以被接管, bool
     */
    /*@JSONField(name = "can_takeover")
    private String canTakeover;*/
    private String can_takeover;

    //@JSONField(name = "created_time" ,format = "yyyy-MM-dd HH:mm:ss")
    private String createdTime;
    // @JSONField(name = "deleted_time",format = "yyyy-MM-dd HH:mm:ss")
    private String deletedTime;
    /**
     * 图片地址
     */
    //@JSONField(name = "image")
    private String image;
    /**
     * 无人机名称
     */
    //@JSONField(name = "name")
    private String name;
    /**
     * 无人机的状态，字符串类型，取值：attacking/danger
     */
    //@JSONField(name = "state")
    private String state;
    /**
     * 此无人机在whitelist中, bool
     */
    private boolean whitelisted;

    private List<DirectionsTemporary> directions;
    //private DirectionsTemporary directions;

    private List<PeerDirectionsTemporary> peer_directions;


    //站点
    private List<SeenSensorsTemporary> seen_sensor;
    //private PeerDirectionsTemporary peer_directions;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isCan_attack() {
        return can_attack;
    }

    public void setCan_attack(boolean can_attack) {
        this.can_attack = can_attack;
    }

    public String getCan_takeover() {
        return can_takeover;
    }

    public void setCan_takeover(String can_takeover) {
        this.can_takeover = can_takeover;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getDeletedTime() {
        return deletedTime;
    }

    public void setDeletedTime(String deletedTime) {
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

    public boolean isWhitelisted() {
        return whitelisted;
    }

    public void setWhitelisted(boolean whitelisted) {
        this.whitelisted = whitelisted;
    }

    public List<DirectionsTemporary> getDirections() {
        return directions;
    }

    public void setDirections(List<DirectionsTemporary> directions) {
        this.directions = directions;
    }

    public List<PeerDirectionsTemporary> getPeer_directions() {
        return peer_directions;
    }

    public void setPeer_directions(List<PeerDirectionsTemporary> peer_directions) {
        this.peer_directions = peer_directions;
    }


    public List<SeenSensorsTemporary> getSeen_sensor() {
        return seen_sensor;
    }

    public void setSeen_sensor(List<SeenSensorsTemporary> seen_sensor) {
        this.seen_sensor = seen_sensor;
    }

    @Override
    public String toString() {
        return "DroneThirdFloor{" +
                "id='" + id + '\'' +
                ", attacking=" + attacking +
                ", can_attack=" + can_attack +
                ", can_takeover='" + can_takeover + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", deletedTime='" + deletedTime + '\'' +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", whitelisted=" + whitelisted +
                ", directions=" + directions +
                ", peer_directions=" + peer_directions +
                ", seen_sensor=" + seen_sensor +
                '}';
    }
}
