package com.ubisys.drone.modules.base.model;

import java.io.Serializable;


public class DirectionsTemporary implements Serializable {
	private Integer direction;
	private String group;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Integer getDirection() {
		return direction;

	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return "DirectionsTemporary{" +
				"direction=" + direction +
				", group='" + group + '\'' +
				'}';
	}
}
