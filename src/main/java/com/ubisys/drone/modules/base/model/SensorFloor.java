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

public class SensorFloor implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 站点主键
     */
    @JSONField(name = "id")
    private String id;
    /**
     * 暂时保留，站点名称
     */
    /*@JSONField(name = "attacking")
    private boolean attacking;*/
    private String name;
    /**
     * 暂时保留，站点状态
     */
    /*@JSONField(name = "can_attack")
    private boolean canAttack;*/
    private String state;
    /**
     * 暂时保留，站点是否存活
     */
    /*@JSONField(name = "can_takeover")
    private String canTakeover;*/
    private Integer ttl;

    private List<SensorConfigTemporary> config;
    //private DirectionsTemporary directions;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    public List<SensorConfigTemporary> getConfig() {
        return config;
    }

    public void setConfig(List<SensorConfigTemporary> config) {
        this.config = config;
    }



    @Override
    public String toString() {
        return "SensorFloor{" +
                "id='" + id + '\'' +
                ", name=" + name +
                ", state='" + state + '\'' +
                ", ttl=" + ttl +
                ", config=" + config +
                '}';
    }
}
