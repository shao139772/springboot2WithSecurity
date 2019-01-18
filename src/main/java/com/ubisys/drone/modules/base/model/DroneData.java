package com.ubisys.drone.modules.base.model;

import java.io.Serializable;


public class DroneData implements Serializable {
	//private List<DroneSecondFloor> DroneSecondFloorList;
	private DroneSecondFloor data;//信息

	public DroneSecondFloor getData() {
		return data;
	}

	public void setData(DroneSecondFloor data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "DroneData{" +
				"data=" + data +
				'}';
	}
}
