package View;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Controller.calculation;
import Controller.driverController;
import Model.Driver;
import Model.Drivers;
import Model.Line;
import Model.Lines;
import Model.Point;
import common.Data;

public class MainView extends JFrame implements Data {
	driverView dView;
	textView tView;
	lineView lView;
	driverController dController;
	GridBagLayout gbl = new GridBagLayout();
	pointsView pView;
	toolBarView tBarView;
	private calculation calculation;
	private Point point;

	private driverMapLineView dLineView;

	// Drivers drivers;
	public MainView() {
		// TODO Auto-generated constructor stub
		getContentPane().setLayout(gbl);
		dView = new driverView();
		tView = new textView();
		lView = new lineView();
		pView = new pointsView();
		tBarView = new toolBarView();
		dLineView = new driverMapLineView();
		this.pack();
		point = createPoint();
		dController = new driverController(createDrivers(), dView, createLines(), tBarView, lView, point, dLineView);
		getContentPane().add(tBarView);
		getContentPane().add(pView);
		getContentPane().add(lView);
		getContentPane().add(dView);
		getContentPane().add(dLineView);
		// getContentPane().add(tView);
		makeConstraints(gbl, tBarView,  8, 1, 0, 0, 1, 0.1);
		makeConstraints(gbl, dLineView, 1, 2, 0, 1, 0, 0.2);
		makeConstraints(gbl, pView, 	1, 5, 0, 2, 0, 1);
		makeConstraints(gbl, lView, 	7, 1, 1, 1, 1, 0.2);
		makeConstraints(gbl, dView, 	7, 4, 1, 3, 1, 0.7);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 600);
		setLocation(300, 500);
		setVisible(true);
		dController.control();
	}

	public Point createPoint() {
		Point point = new Point();
		point.addObserver(pView);
		return point;

	}

	public Drivers createDrivers() {
		Drivers ds = new Drivers();
		calculation = new calculation(this.point);
		ds.setAllDrivers(new Driver[numOfDrivers]);
		for (int i = 0; i < numOfDrivers; i++) {
			Driver driver = new Driver("" + (char) (65 + i), new int[numOfShifts], new int[numOfShifts],
					new int[numOfLines]);
			driver.addObserver(dView);
			ds.setDriver(i, driver);
		}
		ds.addObserver(calculation);
		return ds;
	}

	public Lines createLines() {
		Lines lines = new Lines();
		lines.setAllLines(new Line[numOfLines]);
		for (int i = 0; i < numOfLines; i++) {
			Line line = new Line(new int[numOfShifts], "Line" + (i + 1));
			lines.setLine(i, line);
		}
		return lines;
	}

	public void makeConstraints(GridBagLayout gbl, JComponent comp, int w, int h, int x, int y, double weightx,
			double weighty) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = w;
		constraints.gridheight = h;
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.weightx = weightx;
		constraints.weighty = weighty;
		gbl.setConstraints(comp, constraints);
	}

}
