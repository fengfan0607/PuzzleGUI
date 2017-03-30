package optimization;

import common.Data;

public class dataRead implements Data {

	public int[][] DriverBoard;
	public int[][] driverNoWork = new int[numOfDrivers][numOfDays];
	public int[][] driverPreferDayOff = new int[numOfDrivers][numOfDays];
	public int[][] driverPreferShift = new int[numOfDrivers][numOfShifts];
	public int[][] dirverOnBus = new int[numOfDrivers][numOfLines];
	public int[][] busMapDriver = new int[numOfDrivers][numOfLines];

	public dataRead(int[][] driverboard, int[][] busline) {
		// TODO Auto-generated constructor stub
		DriverBoard = driverboard;
		this.busMapDriver = busline;
	}

	public void print(int[][] array) {
		for (int i = 0; i < array.length; i++) {
			String string = "";
			for (int j = 0; j < array[0].length; j++) {
				string += array[i][j] + ",";
			}
			System.err.println(string + "\n");
		}
	}

	public int[][] readBusLineForEachDriver() {
		return busMapDriver;
	}

	// if DriverBoard[i][j]==1
	public int[][] readNoWorkDayForEachDriver() {
		for (int i = 0; i < numOfDrivers; i++) {
			String string = "driver" + (char) (65 + i) + " cannot work on day: ";
			for (int j = 0; j < numOfShifts; j++) {
				int day = (int) Math.floor(j / 2);
				if (DriverBoard[i][j] == 1) {
					// no work
					driverNoWork[i][day] = 0;
					if (check) {
						int day2 = (int) (Math.ceil((j + 1.0) / 2));
						string += day2 + ", ";
					}
					j++;
				} else {
					// can work
					driverNoWork[i][day] = 1;
					j++;
				}
			}
			System.err.println(string);
		}
		if (print) {
			print(driverNoWork);
		}
		return driverNoWork;
	}

	// if DriverBoard[i][j]==2
	public int[][] readDayOffPreForEachDriver() {
		for (int i = 0; i < numOfDrivers; i++) {
			String string = "driver" + (char) (65 + i) + " prefer day off on: ";
			for (int j = 0; j < numOfShifts; j++) {
				int day = (int) Math.floor(j / 2);
				if (DriverBoard[i][j] == 2) {
					// prefer day off
					driverPreferDayOff[i][day] = 1;
					if (check) {
						int day2 = (int) (Math.ceil((j + 1.0) / 2));
						string += day2 + ", ";
					}
					j++;
				} else {
					// others
					driverPreferDayOff[i][day] = 0;
					j++;
				}
			}
			System.err.println(string);
		}
		if (print) {
			print(driverPreferDayOff);
		}
		return driverPreferDayOff;
	}

	// if DriverBoard[i][j]==4
	public int[][] readShiftPreferenceForEachDriver() {
		for (int i = 0; i < numOfDrivers; i++) {
			String string = "driver" + (char) (65 + i) + " prefer work on shift: ";
			for (int j = 0; j < numOfShifts; j++) {
				if (DriverBoard[i][j] == 4) {
					// prefer work
					driverPreferShift[i][j] = 1;
					if (check) {
						int day = (int) (Math.ceil((j + 1.0) / 2));
						String s = (j) % 2 == 0 ? "E" : "L";
						String s1 = day + s;
						string += s1 + ", ";
					}
				} else {
					// normal
					driverPreferShift[i][j] = 0;
				}
			}
			System.err.println(string);
		}
		if (print) {
			print(driverPreferShift);
		}
		return driverPreferShift;

	}

}
