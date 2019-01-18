package com.ubisys.drone.modules.base.model;

import java.io.Serializable;
import java.util.List;


public class SensorConfigGeoTemporary implements Serializable {

	/**
	 * 纬度
	 */
    /*@JSONField(name = "attacking")
    private boolean attacking;*/
	private float lat;
	/**
	 * 经度
	 */
    /*@JSONField(name = "can_attack")
    private boolean canAttack;*/
	private float lng;

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLng() {
		return lng;
	}

	public void setLng(float lng) {
		this.lng = lng;
	}

	@Override
	public String toString() {
		return "SensorConfigGeoTemporary{" +
				"lng=" + lng +
				", lat='" + lat + '\'' +
				'}';
	}
}
