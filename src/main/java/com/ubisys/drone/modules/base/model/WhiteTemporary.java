package com.ubisys.drone.modules.base.model;

import java.io.Serializable;


public class WhiteTemporary implements Serializable {
	private String dronetype;
	private String id;

	public String getDronetype() {
		return dronetype;
	}

	public void setDronetype(String dronetype) {
		this.dronetype = dronetype;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "WhiteTemporary{" +
				"dronetype='" + dronetype + '\'' +
				", id='" + id + '\'' +
				'}';
	}
}
