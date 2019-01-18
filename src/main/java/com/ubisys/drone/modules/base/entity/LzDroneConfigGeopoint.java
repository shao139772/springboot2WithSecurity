package com.ubisys.drone.modules.base.entity;

import java.util.Date;
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
@TableName("lz_drone_config_geopoint")
public class LzDroneConfigGeopoint extends Model<LzDroneConfigGeopoint> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * Lantitude
     */
    private Float lat;
    /**
     * Longtitude
     */
    private Float lng;
    private Date datetime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "LzDroneConfigGeopoint{" +
        ", id=" + id +
        ", lat=" + lat +
        ", lng=" + lng +
        ", datetime=" + datetime +
        "}";
    }
}
