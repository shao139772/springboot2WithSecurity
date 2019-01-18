package com.ubisys.drone.modules.base.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author cw
 * @since 2018-11-01
 */
@TableName("lz_sensor")
public class LzSensor extends Model<LzSensor> {

    private static final long serialVersionUID = 1L;

    /**
     * 传感器id
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 状态
     */
    private String state;
    /**
     * 生存时间
     */
    private Integer ttl;


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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "LzSensor{" +
        ", id=" + id +
        ", name=" + name +
        ", state=" + state +
        ", ttl=" + ttl +
        "}";
    }
}
