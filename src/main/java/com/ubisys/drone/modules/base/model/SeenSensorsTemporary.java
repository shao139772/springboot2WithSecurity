package com.ubisys.drone.modules.base.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/19 18:48
 */
public class SeenSensorsTemporary implements Serializable {

    @JSONField(name = "detected_freq_khz")
    private String detected_freq_khz;
    @JSONField(name = "detected_time")
    private String detected_time;


    public String getDetected_freq_khz() {
        return detected_freq_khz;
    }


    public void setDetected_freq_khz(String detected_freq_khz) {
        this.detected_freq_khz = detected_freq_khz;
    }

    public String getDetected_time() {
        return detected_time;
    }

    public void setDetected_time(String detected_time) {
        this.detected_time = detected_time;
    }


    @Override
    public String toString() {
        return "SeenSensorsTemporary{" +
                "detected_freq_khz='" + detected_freq_khz + '\'' +
                ", detected_time='" + detected_time + '\'' +
                '}';
    }
}
