package optimization;

import java.awt.Color;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JMenu;

import Model.Driver;
import Model.Drivers;
import View.driverView;
import View.lineView;
import View.myImageIcon;
import common.Data;
import ilog.cplex.IloCplex;
import ilog.cplex.IloCplex.IntParam;

public class algorithm_new implements Data {

	public int[][] driverNoWork = new int[numOfDrivers][numOfDays];
	public int[][] driverPreferDayOff = new int[numOfDrivers][numOfDays];
	public int[][] driverPreferShift = new int[numOfDrivers][numOfShifts];
	public int[][] driverOnBus = new int[numOfDrivers][numOfLines];
	public dataRead DI;
	// public dataOutput DO;
	public LPModel model;
	public int[][][] solution;

	public algorithm_new(int[][] driverBoard, int[][] busMapLines) {
		// TODO Auto-generated constructor stub
		DI = new dataRead(driverBoard, busMapLines);
		// DO = new dataOutput();
		model = new LPModel();
		solution = new int[numOfDrivers][numOfLines][numOfShifts];
		init();
	}

	public int[][] optimization() {
		IloCplex cplex = model.BuildModel(driverNoWork, driverPreferDayOff, driverPreferShift, driverOnBus);
		int[][][] solution = model.solve(cplex);
		return wrapData(solution);
	}

	public int[][] wrapData(int[][][] solution) {
		int[][] postPlanSolution = new int[numOfDrivers][numOfShifts];
		for (int i = 0; i < numOfDrivers; i++) {
			for (int s = 0; s < numOfShifts; s++) {
				for (int j = 0; j < numOfLines; j++) {
					if (solution[i][j][s] != 0) {
						postPlanSolution[i][s] = j + 1;
						break;
					}
				}
			}
		}
		return postPlanSolution;
	}

	public void drawing(driverView dView, lineView lView, Drivers drivers) {
		for (int i = 0; i < numOfDrivers; i++) {
			Driver driver = drivers.getDriver(i);
			int[] postPlan = driver.getPostPlan();
			for (int s = 0; s < numOfShifts; s++) {
				if (postPlan[s] != 0) {
					int lineRow = postPlan[s] - 1;
					int lineCol = s + 1;
					myImageIcon lineIcon = (myImageIcon) lView.getTable().getValueAt(lineRow, lineCol);
					lView.getTable().setValueAt(new myImageIcon(), lineRow, lineCol);
					// Image image = lineIcon.getImage();
					JLabel driverLabel = (JLabel) dView.getTable().getValueAt(i, s + 1);
					Color color = driverLabel.getBackground();
					JLabel newDriverLabel = new JLabel(lineIcon);
					newDriverLabel.setBackground(color);
					dView.getTable().setValueAt(newDriverLabel, i, s + 1);
				}
			}
		}
	}
	
	public void resetDrawing(driverView dView, lineView lView, Drivers drivers) {
		for (int i = 0; i < numOfDrivers; i++) {
			Driver driver = drivers.getDriver(i);
			int[] postPlan = driver.getPostPlan();
			for (int s = 0; s < numOfShifts; s++) {
//				if (postPlan[s] != 0) {
//					int lineRow = postPlan[s] - 1;
//					int lineCol = s + 1;
//					myImageIcon lineIcon = (myImageIcon) lView.getTable().getValueAt(lineRow, lineCol);
//					lView.getTable().setValueAt(new myImageIcon(), lineRow, lineCol);
//					// Image image = lineIcon.getImage();
//					JLabel driverLabel = (JLabel) dView.getTable().getValueAt(i, s + 1);
//					Color color = driverLabel.getBackground();
//					JLabel newDriverLabel = new JLabel(lineIcon);
//					newDriverLabel.setBackground(color);
//					dView.getTable().setValueAt(newDriverLabel, i, s + 1);
//				}
			}
		}
	}

	public void init() {
		driverNoWork = DI.readNoWorkDayForEachDriver();
		driverPreferDayOff = DI.readDayOffPreForEachDriver();
		driverPreferShift = DI.readShiftPreferenceForEachDriver();
		driverOnBus = DI.readBusLineForEachDriver();
	}

}
