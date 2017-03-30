package Model;

import java.util.Observable;

import common.Data;

public class Point extends Observable implements Data {
	public int getShiftPreference() {
		return shiftPreference;
	}

	public void setShiftPreference(int shiftPreference) {
		this.shiftPreference = shiftPreference;
		setChanged();
		notifyObservers();
	}

	public int getLongRest() {
		return longRest;
	}

	public void setLongRest(int longRest) {
		this.longRest = longRest;
		setChanged();
		notifyObservers();
	}

	public int getUnassignedShifts() {
		return unassignedShifts;
	}

	public void setUnassignedShifts(int unassignedShifts) {
		this.unassignedShifts = unassignedShifts;
		setChanged();
		notifyObservers();
	}

	public int getEarlyAfterLateShift() {
		return earlyAfterLateShift;

	}

	public void setEarlyAfterLateShift(int earlyAfterLateShift) {
		this.earlyAfterLateShift = earlyAfterLateShift;
		setChanged();
		notifyObservers();
	}

	public int getConsevutiveLateShifts() {
		return consevutiveLateShifts;
	}

	public void setConsevutiveLateShifts(int consevutiveLateShifts) {
		this.consevutiveLateShifts = consevutiveLateShifts;
		setChanged();
		notifyObservers();
	}

	public int getDeviationTargeLateShift() {
		return deviationTargeLateShift;
	}

	public void setDeviationTargeLateShift(int deviationTargeLateShift) {
		this.deviationTargeLateShift = deviationTargeLateShift;
		setChanged();
		notifyObservers();
	}

	private int shiftPreference;
	private int longRest;
	private int unassignedShifts;
	private int earlyAfterLateShift;
	private int consevutiveLateShifts;
	private int deviationTargeLateShift;
	private int dayOffPreference;
	private int totalPoints;
	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
		setChanged();
		notifyObservers();
	}

	public int getDayOffPreference() {
		return dayOffPreference;
	}

	public void setDayOffPreference(int dayOffPreference) {
		this.dayOffPreference = dayOffPreference;
	}
}
