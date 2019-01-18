package com.ubisys.drone.modules.base.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
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
@TableName("lz_drone_peer_directions")
public class LzDronePeerDirections extends Model<LzDronePeerDirections> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String direction;
    /**
     * 对应的sensor
     */
    private String group;
    private String port;
    /**
     * 无人机id
     */
    private String droneId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDroneId() {
        return droneId;
    }

    public void setDroneId(String droneId) {
        this.droneId = droneId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "LzDronePeerDirections{" +
        ", id=" + id +
        ", direction=" + direction +
        ", group=" + group +
        ", port=" + port +
        ", droneId=" + droneId +
        "}";
    }
}
