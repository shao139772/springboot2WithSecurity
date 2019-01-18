package com.ubisys.drone.modules.base.entity;

import com.ubisys.drone.basetemplate.DroneBaseEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Jhon Davis
 */
@Data
@Entity
@Table(name = "dron_statistics")
@TableName("dron_statistics")
public class DronStatistics extends DroneBaseEntity {

    private static final long serialVersionUID = 1L;

    private String dronName;

    private  Integer dronValue;

    public String getDronName() {
        return dronName;
    }

    public void setDronName(String dronName) {
        this.dronName = dronName;
    }

    public Integer getDronValue() {
        return dronValue;
    }

    public void setDronValue(Integer dronValue) {
        this.dronValue = dronValue;
    }

    @Override
    public String toString() {
        return "DronStatistics{" +
                "dronName='" + dronName + '\'' +
                ", dronValue=" + dronValue +
                '}';
    }
}