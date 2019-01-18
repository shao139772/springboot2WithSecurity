package com.ubisys.drone.modules.base.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
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
 * @since 2018-11-13
 */
@TableName("lz_whitelist")
public class LzWhitelist extends Model<LzWhitelist> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 无人机id
     */
    @TableField("drone_id")
    private String droneId;
    /**
     * 无人机类型
     */
    private String dronetype;
    /**
     * 是否属于白名单  1：是  2否
     */
    @TableField("is_white")
    private String isWhite;
    /**
     * 是否与历正同步  1 否     2   是
     */
    @TableField("is_lz")
    private String isLz;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDroneId() {
        return droneId;
    }

    public void setDroneId(String droneId) {
        this.droneId = droneId;
    }

    public String getDronetype() {
        return dronetype;
    }

    public void setDronetype(String dronetype) {
        this.dronetype = dronetype;
    }

    public String getIsWhite() {
        return isWhite;
    }

    public void setIsWhite(String isWhite) {
        this.isWhite = isWhite;
    }

    public String getIsLz() {
        return isLz;
    }

    public void setIsLz(String isLz) {
        this.isLz = isLz;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "LzWhitelist{" +
        ", id=" + id +
        ", droneId=" + droneId +
        ", dronetype=" + dronetype +
        ", isWhite=" + isWhite +
        ", isLz=" + isLz +
        "}";
    }
}
