package Model;

import java.util.Observable;

import common.Data;

public class Drivers extends Observable implements Data {

	private Driver[] allDrivers;

	public Driver[] getAllDrivers() {
		return allDrivers;
	}

	public void setAllDrivers(Driver[] allDrivers) {
		this.allDrivers = allDrivers;
	}

}
