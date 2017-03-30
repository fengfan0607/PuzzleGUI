package Controller;

import java.util.Observable;
import java.util.Observer;

import Model.Driver;
import Model.Drivers;
import Model.Point;
import View.pointsView;
import common.Data;

public class calculation implements Observer, Data {

	Point point;

	public calculation(Point point) {
		// TODO Auto-generated constructor stub
		// this.drivers = drivers;
		this.point = point;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (o instanceof Drivers) {
			this.Cal((Drivers) o, point);
		}

	}

	public static String calForSingleDriver(Driver d, Point p) {
		int preWork = 0;
		int preDayOff = 0;
		int earlyAfterLateShift = 0;
		int concecutiveLate = 0;
		int longrest = 0;
		int deviationTargeLate = 0;
		int[] prePlan = d.getPrePlan();
		int[] postPlan = d.getPostPlan();
		int consecutive4late = 0;
		int totalNightShift = 0;
		int longrest3 = 0;
		int longrest4 = 0;
		for (int j = 0; j < numOfShifts; j++) {
			// prefer work
			if (prePlan[j] == 4 && postPlan[j] != 0) {
				preWork++;
			}
			// prefer off
			if (j < numOfShifts - 1) {
				if (j % 2 == 0 && prePlan[j] == 2 && postPlan[j] + postPlan[j + 1] == 0) {
					preDayOff++;
				}
			}

			// early after late
			if (j < numOfShifts - 1) {
				if (j % 2 == 1 && postPlan[j] == 1 && postPlan[j + 1] == 1) {
					earlyAfterLateShift++;
				}
			}
			// consecutive late shift
			if (j < numOfShifts - 7) {
				if (j % 2 == 1 && postPlan[j] + postPlan[j + 2] + postPlan[j + 4] + postPlan[j + 6] >= 4) {
					consecutive4late++;
				}
			}
			// total night shift
			if (j % 2 == 1 && postPlan[j] != 0) {
				totalNightShift++;
			}
			// longrest
			if (j % 2 == 0 && j < numOfShifts - 5) {
				int s1 = 0;
				for (int k = 0; k < 6; k++) {
					s1 += postPlan[j + k];
				}
				if (s1 == 0) {
					longrest3++;
				}
			}
			if (j % 2 == 0 && j < numOfShifts - 7) {
				int s2 = 0;
				for (int k = 0; k < 8; k++) {
					s2 += postPlan[j + k];
				}
				if (s2 == 0) {
					longrest4++;
				}
			}
		}

		if ((longrest3 - longrest4) > 0) {
			longrest += (longrest3 - longrest4);
		}

		if (totalNightShift != 4) {
			deviationTargeLate++;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("long rest number:" + longrest + "\n");
		sb.append("prefer shift:" + preWork + "\n");
		sb.append("early follwed by late:" + earlyAfterLateShift + "\n");
		sb.append("day off preference:" + preDayOff + "\n");
		sb.append("deviation Targe Late shift:" + deviationTargeLate + "\n");
		sb.append("consecutive 4 late shift:" + concecutiveLate + "\n");
		return sb.toString();
	}

	public static void Cal(Drivers drivers, Point p) {
		int preWork = 0;
		int preDayOff = 0;
		int earlyAfterLateShift = 0;
		int concecutiveLate = 0;
		int longrest = 0;
		int deviationTargeLate = 0;
		int assignedShift = 0;
		int unassignedShift = 0;

		for (int i = 0; i < numOfDrivers; i++) {
			Driver d = drivers.getDriver(i);
			int[] prePlan = d.getPrePlan();
			int[] postPlan = d.getPostPlan();
			int consecutive4late = 0;
			int totalNightShift = 0;
			int longrest3 = 0;
			int longrest4 = 0;
			for (int j = 0; j < numOfShifts; j++) {
				// prefer work
				if (postPlan[j] != 0) {
					assignedShift++;
				}
				if (prePlan[j] == 4 && postPlan[j] != 0) {
					preWork++;
				}
				// prefer off
				if (j < numOfShifts - 1) {
					if (j % 2 == 0 && prePlan[j] == 2 && postPlan[j] + postPlan[j + 1] == 0) {
						preDayOff++;
					}
				}

				// early after late
				if (j < numOfShifts - 1) {
					if (j % 2 == 1 && postPlan[j] == 1 && postPlan[j + 1] == 1) {
						earlyAfterLateShift++;
					}
				}
				// consecutive late shift
				if (j < numOfShifts - 7) {
					if (j % 2 == 1 && postPlan[j] + postPlan[j + 2] + postPlan[j + 4] + postPlan[j + 6] >= 4) {
						consecutive4late++;
					}
				}
				// total night shift
				if (j % 2 == 1 && postPlan[j] != 0) {
					totalNightShift++;
				}
				// longrest
				if (j % 2 == 0 && j < numOfShifts - 5) {
					int s1 = 0;
					for (int k = 0; k < 6; k++) {
						s1 += postPlan[j + k];
					}
					if (s1 == 0) {
						longrest3++;
					}
				}
				if (j % 2 == 0 && j < numOfShifts - 7) {
					int s2 = 0;
					for (int k = 0; k < 8; k++) {
						s2 += postPlan[j + k];
					}
					if (s2 == 0) {
						longrest4++;
					}
				}
			}

			if ((longrest3 - longrest4) > 0) {
				longrest += (longrest3 - longrest4);
			}

			if (totalNightShift != 4) {
				deviationTargeLate++;
			}
		}
		unassignedShift = (numOfLines * numOfShifts) - assignedShift;
		StringBuilder sb = new StringBuilder();

		sb.append("long rest number:" + longrest + "\n");
		sb.append("prefer shift:" + preWork + "\n");
		sb.append("early follwed by late:" + earlyAfterLateShift + "\n");
		sb.append("day off preference:" + preDayOff + "\n");
		sb.append("deviation Targe Late shift:" + deviationTargeLate + "\n");
		sb.append("consecutive 4 late shift:" + concecutiveLate + "\n");
		sb.append("unassigned shift:" + unassignedShift + "\n");
		System.err.println("start cal" + "\n" + sb.toString());
		p.setConsevutiveLateShifts(concecutiveLate);
		p.setDayOffPreference(preDayOff);
		p.setShiftPreference(preWork);
		p.setDeviationTargeLateShift(deviationTargeLate);
		p.setLongRest(longrest);
		p.setUnassignedShifts(unassignedShift);
		p.setEarlyAfterLateShift(earlyAfterLateShift);
		p.setTotalPoints(w5* concecutiveLate + w2*preDayOff + w1*preWork + w6*deviationTargeLate + w3*unassignedShift
				+ w4*earlyAfterLateShift + w7*longrest);
		// return p;
	}

}
