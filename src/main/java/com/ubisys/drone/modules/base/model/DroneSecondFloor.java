package com.ubisys.drone.modules.base.model;

import java.io.Serializable;
import java.util.List;


public class DroneSecondFloor implements Serializable {
	//private DroneThirdFloor drone;//信息
	private List<DroneThirdFloor> DroneThirdFloorList;

	public List<DroneThirdFloor> getDroneThirdFloorList() {
		return DroneThirdFloorList;
	}

	public void setDroneThirdFloorList(List<DroneThirdFloor> droneThirdFloorList) {
		DroneThirdFloorList = droneThirdFloorList;
	}

	@Override
	public String toString() {
		return "DroneSecondFloor{" +
				"DroneThirdFloorList=" + DroneThirdFloorList +
				'}';
	}
}
