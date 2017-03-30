package View;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import Model.Driver;
import Model.Point;
import common.Data;

public class pointsView extends JPanel implements Observer, Data {

	JLabel shiftPreference;
	JLabel DayOffPrefernce;
	JLabel UnassignedShift;
	JLabel LongRest;
	JLabel EarlyAfterLateShifts;
	JLabel ConsecutiveLateShift;
	JLabel DeviationTargetlateShift;
	JLabel totalPoints;

	JLabel shiftPreferenceCal;
	JLabel DayOffPrefernceCal;
	JLabel UnassignedShiftCal;
	JLabel LongRestCal;
	JLabel EarlyAfterLateShiftsCal;
	JLabel ConsecutiveLateShiftCal;
	JLabel DeviationTargetlateShiftCal;
	JLabel totalPointsCal;

	String space = "   ";
	// Point point;

	public pointsView() {
		// TODO Auto-generated constructor stub
		// JPanel pan= new JPanel();
		setLayout(new GridLayout(16,1));

		// *************************************************
		totalPoints = new JLabel(space + "Total Points");
		totalPointsCal = new JLabel();
		totalPointsCal.setBorder(new LineBorder(Color.black));
		setTotalPointsCal(0);
		add(totalPoints);
		add(totalPointsCal);

		shiftPreference = new JLabel(space + "Shift Prefenrences");
		shiftPreferenceCal = new JLabel();
		shiftPreferenceCal.setBorder(new LineBorder(Color.black));
		setShiftPreferenceCal(0);
		add(shiftPreference);
		add(getShiftPreferenceCal());

		DayOffPrefernce = new JLabel(space + "DayOff Prefernces");
		DayOffPrefernceCal = new JLabel();
		DayOffPrefernceCal.setBorder(new LineBorder(Color.black));
		setDayOffPrefernceCal(0);
		add(DayOffPrefernce);
		add(getDayOffPrefernceCal());

		UnassignedShift = new JLabel(space + "Unassigned Shift");
		UnassignedShiftCal = new JLabel();
		UnassignedShiftCal.setBorder(new LineBorder(Color.black));
		setUnassignedShiftCal(0);
		add(UnassignedShift);
		add(getUnassignedShiftCal());

		LongRest = new JLabel(space + "Long Rest");
		LongRestCal = new JLabel();
		LongRestCal.setBorder(new LineBorder(Color.black));
		setLongRestCal(0);
		add(LongRest);
		add(getLongRestCal());

		EarlyAfterLateShifts = new JLabel("Early After Late Shifts");
		EarlyAfterLateShiftsCal = new JLabel();
		EarlyAfterLateShiftsCal.setBorder(new LineBorder(Color.black));
		setEarlyAfterLateShiftsCal(0);
		add(EarlyAfterLateShifts);
		add(getEarlyAfterLateShiftsCal());

		ConsecutiveLateShift = new JLabel("Consecutive Late Shift");
		ConsecutiveLateShiftCal = new JLabel();
		ConsecutiveLateShiftCal.setBorder(new LineBorder(Color.black));
		setConsecutiveLateShiftCal(0);
		add(ConsecutiveLateShift);
		add(getConsecutiveLateShiftCal());

		DeviationTargetlateShift = new JLabel("Deviation Target late Shift");
		DeviationTargetlateShiftCal = new JLabel();
		DeviationTargetlateShiftCal.setBorder(new LineBorder(Color.black));
		setDeviationTargetlateShiftCal(0);
		add(DeviationTargetlateShift);
		add(getDeviationTargetlateShiftCal());
		// shiftPreference.add(textArea);

		// ***************************************************
		// add(panel);
	}

	public JLabel getTotalPointsCal() {
		return totalPointsCal;
	}

	public void setTotalPointsCal(int point) {
		this.totalPointsCal.setText(space + point);

	}

	public void update(Observable o, Object arg) {
		if (o instanceof Point) {
			System.err.println("receive update");
			setShiftPreferenceCal(((Point) o).getShiftPreference());
			setUnassignedShiftCal(((Point) o).getUnassignedShifts());
			setLongRestCal(((Point) o).getLongRest());
			setConsecutiveLateShiftCal(((Point) o).getConsevutiveLateShifts());
			setEarlyAfterLateShiftsCal(((Point) o).getEarlyAfterLateShift());
			setDeviationTargetlateShiftCal(((Point) o).getDeviationTargeLateShift());
			setDayOffPrefernceCal(((Point) o).getDayOffPreference());
			setTotalPointsCal(((Point) o).getTotalPoints());
		}
	}

	public JLabel getShiftPreferenceCal() {
		return shiftPreferenceCal;
	}

	public void setShiftPreferenceCal(int point) {
		int points = w1 * point;
		this.shiftPreferenceCal.setText(space + w1 + " * " + point + " = " + points);
	}

	public JLabel getDayOffPrefernceCal() {
		return DayOffPrefernceCal;
	}

	public void setDayOffPrefernceCal(int point) {
		int points = w2 * point;
		this.DayOffPrefernceCal.setText(space + w2 + " * " + point + " = " + points);
	}

	public JLabel getUnassignedShiftCal() {
		return UnassignedShiftCal;
	}

	public void setUnassignedShiftCal(int point) {
		int points = w3 * point;
		this.UnassignedShiftCal.setText(space + w3 + " * " + point + " = " + points);
	}

	public JLabel getLongRestCal() {
		return LongRestCal;
	}

	public void setLongRestCal(int point) {
		int points = w7 * point;
		this.LongRestCal.setText(space + w7 + " * " + point + " = " + points);
	}

	public JLabel getEarlyAfterLateShiftsCal() {
		return EarlyAfterLateShiftsCal;
	}

	public void setEarlyAfterLateShiftsCal(int point) {
		int points = w4 * point;
		this.EarlyAfterLateShiftsCal.setText(space + w4 + " * " + point + " = " + points);
		;
	}

	public JLabel getConsecutiveLateShiftCal() {
		return ConsecutiveLateShiftCal;
	}

	public void setConsecutiveLateShiftCal(int point) {
		int points = w5 * point;
		this.ConsecutiveLateShiftCal.setText(space + w5 + " * " + point + " = " + points);
	}

	public JLabel getDeviationTargetlateShiftCal() {
		return DeviationTargetlateShiftCal;
	}

	public void setDeviationTargetlateShiftCal(int point) {
		int points = w6 * point;
		this.DeviationTargetlateShiftCal.setText(space + w6 + " * " + point + " = " + points);
	}
}
