package com.ubisys.drone.modules.base.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.ubisys.drone.basetemplate.DroneBaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Jhon Davis
 */
@Data
@Entity
@Table(name = "duration")
@TableName("duration")
public class Duration  extends DroneBaseEntity implements  Comparable<Duration>{

    private static final long serialVersionUID = 1L;
    private  Integer orderBy;
    private String serialNum;

    private  Integer quantity;

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    public int compareTo(Duration o) {
        return (this.orderBy - o.getOrderBy()) > 0 ? 1 : -1;
    }
}