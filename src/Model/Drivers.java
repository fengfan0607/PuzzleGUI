package Model;

import java.util.Arrays;
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

	public void setDriver(int i, Driver d) {
		allDrivers[i] = d;
		setChanged();
		notifyObservers();
	}

	public Driver getDriver(int i) {
		return allDrivers[i];
	}

	public Drivers() {
		super();
		// TODO Auto-generated constructor stub
	}

}
