package com.ubisys.drone.modules.base.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.ubisys.drone.basetemplate.DroneBaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author miaomiao
 */
@Data
@Entity
@Table(name = "lz_test")
@TableName("lz_test")
public class Test extends DroneBaseEntity {

    private static final long serialVersionUID = 1L;



    @Column
    private String heihei;
}