package com.ubisys.drone.modules.base.model;

import java.io.Serializable;
import java.util.List;


public class SensorConfigTemporary implements Serializable {

	/**
	 * 暂时保留，创建时间
	 */
    /*@JSONField(name = "attacking")
    private boolean attacking;*/
	private String build_time;
	/**
	 * 暂时保留
	 */
    /*@JSONField(name = "can_attack")
    private boolean canAttack;*/
	private float df_deflection;
	/**
	 * 暂时保留
	 */
    /*@JSONField(name = "attacking")
    private boolean attacking;*/
	private String engine_ip;
	/**
	 * 暂时保留
	 */
    /*@JSONField(name = "can_attack")
    private boolean canAttack;*/
	private Integer engine_port;
	/**
	 * 暂时保留，站点名称
	 */
    /*@JSONField(name = "attacking")
    private boolean attacking;*/
	private List<SensorConfigGeoTemporary> geo_location;

	public String getBuild_time() {
		return build_time;
	}

	public void setBuild_time(String build_time) {
		this.build_time = build_time;
	}

	public float getDf_deflection() {
		return df_deflection;
	}

	public void setDf_deflection(float df_deflection) {
		this.df_deflection = df_deflection;
	}

	public String getEngine_ip() {
		return engine_ip;
	}

	public void setEngine_ip(String engine_ip) {
		this.engine_ip = engine_ip;
	}

	public Integer getEngine_port() {
		return engine_port;
	}

	public void setEngine_port(Integer engine_port) {
		this.engine_port = engine_port;
	}

	public List<SensorConfigGeoTemporary> getGeo_location() {
		return geo_location;
	}

	public void setGeo_location(List<SensorConfigGeoTemporary> geo_location) {
		this.geo_location = geo_location;
	}

	@Override
	public String toString() {
		return "SensorConfigTemporary{" +
				"build_time=" + build_time +
				", df_deflection='" + df_deflection + '\'' +
				", engine_ip=" + engine_ip +
				", engine_port='" + engine_port + '\'' +
				", geo_location=" + geo_location +
				'}';
	}
}
