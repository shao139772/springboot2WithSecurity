package com.ubisys.drone.modules.base.model;

import java.io.Serializable;


public class PeerDirectionsTemporary implements Serializable {
	private Integer direction;
	private String group;

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return "PeerDirectionsTemporary{" +
				"direction=" + direction +
				", group='" + group + '\'' +
				'}';
	}
}
