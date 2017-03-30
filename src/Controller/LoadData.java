package Controller;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTable;

import Components.JLabelRenderer;
import Model.Driver;
import Model.Drivers;
import common.Data;

public class LoadData implements Data {

	public static void loadData(JTable table, Drivers drivers, int set) {
		int DriverBoard[][] = null;
		int busMapDriver[][] = null;
		if (set == 1) {
			DriverBoard = DriverBoard1;
			busMapDriver = busMapDriver1;
		} else {
			DriverBoard = DriverBoard2;
			busMapDriver = busMapDriver2;
		}
		for (int i = 0; i < numOfDrivers; i++) {
			for (int j = 0; j < numOfShifts; j++) {
				Color color = null;
				int index = DriverBoard[i][j];
				if (index == 1) {
					color = Color.BLACK;
				} else if (index == 2) {
					color = Color.GRAY;
				} else if (index == 4) {
					color = Color.YELLOW;
				} else {
					color = Color.white;
				}
				setColor(i, j + 1, color, table);
				updateDriver(i, j, index, drivers);
			}
		}

		for (int i = 0; i < numOfDrivers; i++) {
			for (int j = 0; j < numOfLines; j++) {
				if (busMapDriver[i][j] != 0) {
					drivers.getDriver(i).setDriveLines(j);
				}
			}
		}
	}

	public static void setColor(int row, int col, Color color, JTable table) {
		JLabel label = new JLabel();
		label.setOpaque(true);
		label.setBackground(color);
		table.getColumnModel().getColumn(col).setCellRenderer(new JLabelRenderer());
		table.setValueAt(label, row, col);
	}

	public static void updateDriver(int row, int col, int value, Drivers drivers) {
		Driver d = drivers.getDriver(row);
		drivers.setDriver(row, updatePrePlan(d, col, row, value));
		// System.err.println(drivers.getDriver(row).toString());
	}

	public static Driver updatePrePlan(Driver d, int column, int row, int value) {
		d.setPrePlanAtPosition(column, value);
		return d;
	}
}
