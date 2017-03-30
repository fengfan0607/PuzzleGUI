package optimization;

import common.Data;
import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;
import ilog.cplex.IloCplex.CplexEndedException;

public class LPModel implements Data {
	IloNumVar C1;
	IloNumVar C2;
	IloNumVar C3;
	IloNumVar C4;
	IloNumVar C5;
	IloNumVar C6;
	IloNumVar C7;
	IloNumVar[][][] X;

	public IloCplex BuildModel(int[][] driverNoWork, int[][] driverPreferDayOff, int[][] driverPreferShift,
			int[][] driverOnBus) {

		try {
			IloCplex cplex = new IloCplex();

			// decision variable

			X = new IloNumVar[numOfDrivers][numOfLines][numOfShifts];

			for (int i = 0; i < numOfDrivers; i++) {
				for (int j = 0; j < numOfLines; j++) {
					X[i][j] = cplex.boolVarArray(numOfShifts);
				}
			}

			// 1. bus driver i can drive line j

			IloLinearNumExpr[][] ShiftSumForIJ = new IloLinearNumExpr[numOfDrivers][numOfLines];
			for (int i = 0; i < numOfDrivers; i++) {
				for (int j = 0; j < numOfLines; j++) {
					ShiftSumForIJ[i][j] = cplex.linearNumExpr();
					for (int s = 0; s < numOfShifts; s++) {
						ShiftSumForIJ[i][j].addTerm(1, X[i][j][s]);
					}
				}
			}

			for (int i = 0; i < numOfDrivers; i++) {
				for (int j = 0; j < numOfLines; j++) {
					cplex.addLe(ShiftSumForIJ[i][j], 28 * driverOnBus[i][j]);
				}
			}

			// 2. cannot assign shift on days off

			IloLinearNumExpr[][] SumDayShiftForID = new IloLinearNumExpr[numOfDrivers][numOfDays];

			for (int i = 0; i < numOfDrivers; i++) {
				for (int d = 0; d < numOfDays; d++) {
					SumDayShiftForID[i][d] = cplex.linearNumExpr();
					for (int j = 0; j < numOfLines; j++) {
						SumDayShiftForID[i][d].addTerm(1, X[i][j][2 * d]);
						SumDayShiftForID[i][d].addTerm(1, X[i][j][2 * d + 1]);
					}
				}
			}

			for (int i = 0; i < numOfDrivers; i++) {
				for (int d = 0; d < numOfDays; d++) {
					cplex.addLe(SumDayShiftForID[i][d], driverNoWork[i][d]);
				}
			}

			// 3. Each line should allocate wtoe drivers per day

			IloLinearNumExpr[][] SumDriverForJS = new IloLinearNumExpr[numOfLines][numOfShifts];
			for (int j = 0; j < numOfLines; j++) {
				for (int s = 0; s < numOfShifts; s++) {
					SumDriverForJS[j][s] = cplex.linearNumExpr();
					for (int i = 0; i < numOfDrivers; i++) {
						SumDriverForJS[j][s].addTerm(1, X[i][j][s]);
					}

				}
			}

			for (int j = 0; j < numOfLines; j++) {
				for (int s = 0; s < numOfShifts; s++) {
					cplex.addLe(SumDriverForJS[j][s], 1);
				}
			}

			// 5. respect single shfit

			C1 = cplex.numVar(0, Integer.MAX_VALUE);
			IloLinearNumExpr SumC1 = cplex.linearNumExpr();

			for (int i = 0; i < numOfDrivers; i++) {
				for (int j = 0; j < numOfLines; j++) {
					for (int s = 0; s < numOfShifts; s++) {
						SumC1.addTerm(driverPreferShift[i][s], X[i][j][s]);
					}
				}
			}
			cplex.addEq(C1, SumC1);

			// 6. Respect a driver day off preference
			C2 = cplex.numVar(0, Integer.MAX_VALUE);
			IloLinearNumExpr c2Expr = cplex.linearNumExpr();
			for (int i = 0; i < numOfDrivers; i++) {
				for (int d = 0; d < numOfDays; d++) {
					for (int j = 0; j < numOfLines; j++) {
						c2Expr.addTerm(driverPreferDayOff[i][d], X[i][j][2 * d]);
						c2Expr.addTerm(driverPreferDayOff[i][d], X[i][j][2 * d + 1]);
					}
				}
			}
			c2Expr.addTerm(1, C2);
			int SumDayOffDriver = 0;
			for (int i = 0; i < numOfDrivers; i++) {
				for (int d = 0; d < numOfDays; d++) {
					SumDayOffDriver += driverPreferDayOff[i][d];
				}
			}

			cplex.addEq(c2Expr, SumDayOffDriver);

			// 7. For each unassigned shift

			IloNumVar[][] l_unassigned = new IloNumVar[numOfLines][numOfShifts];
			for (int j = 0; j < numOfLines; j++) {
				l_unassigned[j] = cplex.boolVarArray(numOfShifts);
			}
			C3 = cplex.numVar(0, Integer.MAX_VALUE);
			IloLinearNumExpr C3_expre = cplex.linearNumExpr();
			C3_expre.addTerm(1, C3);
			IloLinearNumExpr[][] F_unassigned_expre1 = new IloLinearNumExpr[numOfLines][numOfShifts];
			IloLinearNumExpr[][] F_unassigned_expre2 = new IloLinearNumExpr[numOfLines][numOfShifts];
			for (int j = 0; j < numOfLines; j++) {
				for (int s = 0; s < numOfShifts; s++) {
					F_unassigned_expre1[j][s] = cplex.linearNumExpr();
					F_unassigned_expre2[j][s] = cplex.linearNumExpr();
					for (int i = 0; i < numOfDrivers; i++) {
						F_unassigned_expre1[j][s].addTerm(1, X[i][j][s]);
						F_unassigned_expre2[j][s].addTerm(1, X[i][j][s]);
					}
					F_unassigned_expre1[j][s].addTerm(1, l_unassigned[j][s]);
					F_unassigned_expre2[j][s].addTerm(upsilon, l_unassigned[j][s]);
					C3_expre.addTerm(-1, l_unassigned[j][s]);
				}
			}

			for (int j = 0; j < numOfLines; j++) {
				for (int s = 0; s < numOfShifts; s++) {
					cplex.addLe(F_unassigned_expre1[j][s], 1);
					cplex.addGe(F_unassigned_expre2[j][s], upsilon);
					cplex.addEq(C3_expre, 0);
				}
			}

			// 8.for each late shift followed immediately be an early shift in
			// a single drive plan

			IloNumVar[][] l_lateFollowedEarly = new IloNumVar[numOfDrivers][numOfDays - 1];
			for (int i = 0; i < numOfDrivers; i++) {
				l_lateFollowedEarly[i] = cplex.boolVarArray(numOfDays - 1);
			}

			C4 = cplex.numVar(0, Integer.MAX_VALUE);
			IloLinearNumExpr C4_Expre = cplex.linearNumExpr();
			C4_Expre.addTerm(1, C4);
			IloLinearNumExpr[][] F_lateFolloedEarly_Expre1 = new IloLinearNumExpr[numOfDrivers][numOfDays - 1];
			IloLinearNumExpr[][] F_lateFolloedEarly_Expre2 = new IloLinearNumExpr[numOfDrivers][numOfDays - 1];

			for (int i = 0; i < numOfDrivers; i++) {
				for (int d = 0; d < numOfDays - 1; d++) {
					F_lateFolloedEarly_Expre1[i][d] = cplex.linearNumExpr();
					F_lateFolloedEarly_Expre2[i][d] = cplex.linearNumExpr();
					for (int j = 0; j < numOfLines; j++) {
						F_lateFolloedEarly_Expre1[i][d].addTerm(1, X[i][j][2 * d + 1]);
						F_lateFolloedEarly_Expre1[i][d].addTerm(1, X[i][j][2 * d + 2]);
						F_lateFolloedEarly_Expre2[i][d].addTerm(1, X[i][j][2 * d + 1]);
						F_lateFolloedEarly_Expre2[i][d].addTerm(1, X[i][j][2 * d + 2]);
					}
					F_lateFolloedEarly_Expre1[i][d].addTerm(1, l_lateFollowedEarly[i][d]);
					F_lateFolloedEarly_Expre2[i][d].addTerm(1 + upsilon, l_lateFollowedEarly[i][d]);
					C4_Expre.addTerm(1, l_lateFollowedEarly[i][d]);
				}
			}

			for (int i = 0; i < numOfDrivers; i++) {
				for (int d = 0; d < numOfDays - 1; d++) {
					cplex.addLe(F_lateFolloedEarly_Expre1[i][d], 2);
					cplex.addGe(F_lateFolloedEarly_Expre2[i][d], upsilon + 1);
				}
			}
			cplex.addEq(C4_Expre, numOfDrivers * (numOfDays - 1));

			// 9. For every after the 3 consecutive late shift assigend to a
			// single driver

			IloNumVar[][] l_threeConsecutiveRest = new IloNumVar[numOfDrivers][numOfDays - 3];
			for (int i = 0; i < numOfDrivers; i++) {
				l_threeConsecutiveRest[i] = cplex.boolVarArray(numOfDays - 3);
			}
			C5 = cplex.numVar(0, Integer.MAX_VALUE);
			IloLinearNumExpr C5_expre = cplex.linearNumExpr();
			C5_expre.addTerm(1, C5);
			IloLinearNumExpr[][] F_threeConsecutiveRest_expre1 = new IloLinearNumExpr[numOfDrivers][numOfDays - 3];
			IloLinearNumExpr[][] F_threeConsecutiveRest_expre2 = new IloLinearNumExpr[numOfDrivers][numOfDays - 3];

			for (int i = 0; i < numOfDrivers; i++) {
				for (int d = 0; d < numOfDays - 3; d++) {
					F_threeConsecutiveRest_expre1[i][d] = cplex.linearNumExpr();
					F_threeConsecutiveRest_expre2[i][d] = cplex.linearNumExpr();
					for (int j = 0; j < numOfLines; j++) {
						F_threeConsecutiveRest_expre1[i][d].addTerm(1, X[i][j][2 * d + 1]);
						F_threeConsecutiveRest_expre1[i][d].addTerm(1, X[i][j][2 * d + 3]);
						F_threeConsecutiveRest_expre1[i][d].addTerm(1, X[i][j][2 * d + 5]);
						F_threeConsecutiveRest_expre1[i][d].addTerm(1, X[i][j][2 * d + 7]);
						F_threeConsecutiveRest_expre2[i][d].addTerm(1, X[i][j][2 * d + 1]);
						F_threeConsecutiveRest_expre2[i][d].addTerm(1, X[i][j][2 * d + 3]);
						F_threeConsecutiveRest_expre2[i][d].addTerm(1, X[i][j][2 * d + 5]);
						F_threeConsecutiveRest_expre2[i][d].addTerm(1, X[i][j][2 * d + 7]);
					}
					C5_expre.addTerm(1, l_threeConsecutiveRest[i][d]);
					F_threeConsecutiveRest_expre1[i][d].addTerm(1, l_threeConsecutiveRest[i][d]);
					F_threeConsecutiveRest_expre2[i][d].addTerm(3 + upsilon, l_threeConsecutiveRest[i][d]);
				}
			}

			for (int i = 0; i < numOfDrivers; i++) {
				for (int d = 0; d < numOfDays - 3; d++) {
					cplex.addLe(F_threeConsecutiveRest_expre1[i][d], 4);
					cplex.addGe(F_threeConsecutiveRest_expre2[i][d], upsilon + 3);
				}
			}
			cplex.addEq(C5_expre, numOfDrivers * (numOfDays - 3));

			// 10. for every late shift assigned that is not equal to 4

			IloNumVar[] l_lateshiftless3 = new IloNumVar[numOfDrivers];
			IloNumVar[] l_lateshiftlarge5 = new IloNumVar[numOfDrivers];
			l_lateshiftless3 = cplex.boolVarArray(numOfDrivers);
			l_lateshiftlarge5 = cplex.boolVarArray(numOfDrivers);
			C6 = cplex.numVar(0, Integer.MAX_VALUE);
			IloLinearNumExpr C6_expre = cplex.linearNumExpr();
			C6_expre.addTerm(1, C6);
			IloLinearNumExpr[] F_lateshiftless3_expre1 = new IloLinearNumExpr[numOfDrivers];
			IloLinearNumExpr[] F_lateshiftless3_expre2 = new IloLinearNumExpr[numOfDrivers];
			IloLinearNumExpr[] F_lateshiftlarge5_expre1 = new IloLinearNumExpr[numOfDrivers];
			IloLinearNumExpr[] F_lateshiftlarge5_expre2 = new IloLinearNumExpr[numOfDrivers];
			for (int i = 0; i < numOfDrivers; i++) {
				F_lateshiftless3_expre1[i] = cplex.linearNumExpr();
				F_lateshiftless3_expre2[i] = cplex.linearNumExpr();
				F_lateshiftlarge5_expre1[i] = cplex.linearNumExpr();
				F_lateshiftlarge5_expre2[i] = cplex.linearNumExpr();
				for (int j = 0; j < numOfLines; j++) {
					for (int d = 0; d < numOfDays; d++) {
						F_lateshiftless3_expre1[i].addTerm(1, X[i][j][2 * d + 1]);
						F_lateshiftless3_expre2[i].addTerm(1, X[i][j][2 * d + 1]);
						F_lateshiftlarge5_expre1[i].addTerm(1, X[i][j][2 * d + 1]);
						F_lateshiftlarge5_expre2[i].addTerm(1, X[i][j][2 * d + 1]);
					}
				}
				F_lateshiftless3_expre1[i].addTerm(11, l_lateshiftless3[i]);
				F_lateshiftless3_expre2[i].addTerm(3 + upsilon, l_lateshiftless3[i]);
				F_lateshiftlarge5_expre1[i].addTerm(-5, l_lateshiftlarge5[i]);
				F_lateshiftlarge5_expre2[i].addTerm(-9 - upsilon, l_lateshiftlarge5[i]);
				C6_expre.addTerm(-1, l_lateshiftless3[i]);
				C6_expre.addTerm(-1, l_lateshiftlarge5[i]);

			}

			for (int i = 0; i < numOfDrivers; i++) {
				cplex.addLe(F_lateshiftless3_expre1[i], 14);
				cplex.addGe(F_lateshiftless3_expre2[i], upsilon + 3);
				cplex.addGe(F_lateshiftlarge5_expre1[i], 0);
				cplex.addLe(F_lateshiftlarge5_expre2[i], 5 - upsilon);
			}
			cplex.addEq(C6_expre, 0);

			// For each allocated long rest
			IloNumVar[][] l_threeDayRest = new IloNumVar[numOfDrivers][numOfDays - 2];
			IloNumVar[][] l_fourDayRest = new IloNumVar[numOfDrivers][numOfDays - 3];
			for (int i = 0; i < numOfDrivers; i++) {
				l_threeDayRest[i] = cplex.boolVarArray(numOfDays - 2);
				l_fourDayRest[i] = cplex.boolVarArray(numOfDays - 3);

			}
			C7 = cplex.numVar(0, Integer.MAX_VALUE);
			IloLinearNumExpr C7_expre = cplex.linearNumExpr();
			C7_expre.addTerm(1, C7);
			IloLinearNumExpr[][] F_threeDayRest_expre1 = new IloLinearNumExpr[numOfDrivers][numOfDays - 2];
			IloLinearNumExpr[][] F_threeDayRest_expre2 = new IloLinearNumExpr[numOfDrivers][numOfDays - 2];
			IloLinearNumExpr[][] F_fourDayRest_expre1 = new IloLinearNumExpr[numOfDrivers][numOfDays - 3];
			IloLinearNumExpr[][] F_fourDayRest_expre2 = new IloLinearNumExpr[numOfDrivers][numOfDays - 3];

			for (int i = 0; i < numOfDrivers; i++) {
				for (int d = 0; d < numOfDays - 2; d++) {
					F_threeDayRest_expre1[i][d] = cplex.linearNumExpr();
					F_threeDayRest_expre2[i][d] = cplex.linearNumExpr();
					for (int j = 0; j < numOfLines; j++) {
						for (int m = 0; m <= 5; m++) {
							F_threeDayRest_expre1[i][d].addTerm(1, X[i][j][2 * d + m]);
							F_threeDayRest_expre2[i][d].addTerm(1, X[i][j][2 * d + m]);
						}
					}
					F_threeDayRest_expre1[i][d].addTerm(3, l_threeDayRest[i][d]);
					F_threeDayRest_expre2[i][d].addTerm(upsilon, l_threeDayRest[i][d]);
					C7_expre.addTerm(-1, l_threeDayRest[i][d]);
				}
			}

			for (int i = 0; i < numOfDrivers; i++) {
				for (int d = 0; d < numOfDays - 3; d++) {
					F_fourDayRest_expre1[i][d] = cplex.linearNumExpr();
					F_fourDayRest_expre2[i][d] = cplex.linearNumExpr();
					for (int j = 0; j < numOfLines; j++) {
						for (int m = 0; m <= 7; m++) {
							F_fourDayRest_expre1[i][d].addTerm(1, X[i][j][2 * d + m]);
							F_fourDayRest_expre2[i][d].addTerm(1, X[i][j][2 * d + m]);
						}
					}
					F_fourDayRest_expre1[i][d].addTerm(4, l_fourDayRest[i][d]);
					F_fourDayRest_expre2[i][d].addTerm(upsilon, l_fourDayRest[i][d]);
					C7_expre.addTerm(1, l_fourDayRest[i][d]);
				}
			}
			for (int i = 0; i < numOfDrivers; i++) {
				for (int d = 0; d < numOfDays - 2; d++) {
					cplex.addLe(F_threeDayRest_expre1[i][d], 3);
					cplex.addGe(F_threeDayRest_expre2[i][d], upsilon);
				}
			}
			for (int i = 0; i < numOfDrivers; i++) {
				for (int d = 0; d < numOfDays - 3; d++) {
					cplex.addLe(F_fourDayRest_expre1[i][d], 4);
					cplex.addGe(F_fourDayRest_expre2[i][d], upsilon);
				}
			}
			cplex.addEq(0, C7_expre);

			// objective
			IloLinearNumExpr obj = cplex.linearNumExpr();
			obj.addTerm(w1, C1);
			obj.addTerm(w2, C2);
			obj.addTerm(w3, C3);
			obj.addTerm(w4, C4);
			obj.addTerm(w5, C5);
			obj.addTerm(w6, C6);
			obj.addTerm(w7, C7);
			cplex.addMaximize(obj);

			return cplex;

		} catch (IloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public int[][][] solve(IloCplex cplex) {
		int[][][] solution = new int[numOfDrivers][numOfLines][numOfShifts];
		try {
			if (cplex.solve()) {
				cplex.output().println("Solution status = " + cplex.getStatus());
				cplex.output().println("Solution value = " + cplex.getObjValue());
				cplex.output().println("DriverPreferShift = " + cplex.getValue(C1));
				cplex.output().println("DriverDayOffPreference = " + cplex.getValue(C2));
				cplex.output().println("UnassignedShift = " + cplex.getValue(C3));
				cplex.output().println("NightFollowedByEarly = " + cplex.getValue(C4));
				cplex.output().println("ThreeConsecutiveNightShift = " + cplex.getValue(C5));
				cplex.output().println("NightShiftNotEqualTo4 = " + cplex.getValue(C6));
				cplex.output().println("LongRest = " + cplex.getValue(C7));
				for (int i = 0; i < numOfDrivers; i++) {
					for (int j = 0; j < numOfLines; j++) {
						for (int s = 0; s < numOfShifts; s++) {
							if (cplex.getValue(X[i][j][s]) > 0.5) {
								solution[i][j][s] = 1;
							} else {
								solution[i][j][s] = 0;
							}
						}
					}
				}
			}
			cplex.end();
		} catch (IloException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return solution;
	}

}
