package com.ubisys.drone.modules.base.entity;

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
 * @since 2018-11-01
 */
@TableName("lz_sensor_config")
public class LzSensorConfig extends Model<LzSensorConfig> {

    private static final long serialVersionUID = 1L;

    private String id;
    @TableField("build_time")
    private String buildTime;
    @TableField("df_deflection")
    private Float dfDeflection;
    /**
     * IP address of paga engine
     */
    @TableField("engine_ip")
    private String engineIp;
    /**
     * TCP Port of paga engine
     */
    @TableField("engine_port")
    private String enginePort;
    /**
     * git hash value
     */
    @TableField("git_hash")
    private String gitHash;
    /**
     * group sensor belongs to
     */
    private String group;
    /**
     * url of mqtt broker
     */
    @TableField("mqtt_server")
    private String mqttServer;
    /**
     * Name of a sensor
     */
    private String name;
    /**
     * ttl
     */
    private Integer ttl;
    /**
     * version
     */
    private String version;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    public Float getDfDeflection() {
        return dfDeflection;
    }

    public void setDfDeflection(Float dfDeflection) {
        this.dfDeflection = dfDeflection;
    }

    public String getEngineIp() {
        return engineIp;
    }

    public void setEngineIp(String engineIp) {
        this.engineIp = engineIp;
    }

    public String getEnginePort() {
        return enginePort;
    }

    public void setEnginePort(String enginePort) {
        this.enginePort = enginePort;
    }

    public String getGitHash() {
        return gitHash;
    }

    public void setGitHash(String gitHash) {
        this.gitHash = gitHash;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getMqttServer() {
        return mqttServer;
    }

    public void setMqttServer(String mqttServer) {
        this.mqttServer = mqttServer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "LzSensorConfig{" +
        ", id=" + id +
        ", buildTime=" + buildTime +
        ", dfDeflection=" + dfDeflection +
        ", engineIp=" + engineIp +
        ", enginePort=" + enginePort +
        ", gitHash=" + gitHash +
        ", group=" + group +
        ", mqttServer=" + mqttServer +
        ", name=" + name +
        ", ttl=" + ttl +
        ", version=" + version +
        "}";
    }
}
