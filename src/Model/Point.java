package Model;

import java.util.Observable;

import common.Data;

public class Point extends Observable implements Data {
	public int getShiftPreference() {
		return shiftPreference;
	}

	public void setShiftPreference(int shiftPreference) {
		this.shiftPreference = shiftPreference;
	}

	public int getLongRest() {
		return longRest;
	}

	public void setLongRest(int longRest) {
		this.longRest = longRest;
	}

	public int getUnassignedShifts() {
		return unassignedShifts;
	}

	public void setUnassignedShifts(int unassignedShifts) {
		this.unassignedShifts = unassignedShifts;
	}

	public int getEarlyAfterLateShift() {
		return earlyAfterLateShift;
	}

	public void setEarlyAfterLateShift(int earlyAfterLateShift) {
		this.earlyAfterLateShift = earlyAfterLateShift;
	}

	public int getConsevutiveLateShifts() {
		return consevutiveLateShifts;
	}

	public void setConsevutiveLateShifts(int consevutiveLateShifts) {
		this.consevutiveLateShifts = consevutiveLateShifts;
	}

	public int getDeviationTargeLateShift() {
		return deviationTargeLateShift;
	}

	public void setDeviationTargeLateShift(int deviationTargeLateShift) {
		this.deviationTargeLateShift = deviationTargeLateShift;
	}

	private int shiftPreference;
	private int longRest;
	private int unassignedShifts;
	private int earlyAfterLateShift;
	private int consevutiveLateShifts;
	private int deviationTargeLateShift;
}
