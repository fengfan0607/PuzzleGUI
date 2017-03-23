package Model;

import java.util.Observable;

import common.Data;

public class Driver extends Observable implements Data {

	private int[] perferDayOff;
	private int[] perferWorkShift;
	private int[] daysOff;
	private String driverName;
	private int[] lines;
	private int[][] plan;

	public int[][] getPlan() {
		return plan;
	}

	public void setPlan(int[][] plan) {
		this.plan = plan;
	}

	public int[] getLines() {
		return lines;
	}

	public void setLines(int[] lines) {
		this.lines = lines;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public int[] getPerferDayOff() {
		return perferDayOff;
	}

	public void setPerferDayOff(int[] perferDayOff) {
		this.perferDayOff = perferDayOff;
	}

	public int[] getPerferWorkShift() {
		return perferWorkShift;
	}

	public void setPerferWorkShift(int[] perferWorkShift) {
		this.perferWorkShift = perferWorkShift;
	}

	public int[] getDaysOff() {
		return daysOff;
	}

	public void setDaysOff(int[] daysOff) {
		this.daysOff = daysOff;
	}

}
