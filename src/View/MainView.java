package View;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Controller.driverController;
import Model.Driver;
import Model.Drivers;
import common.Data;

public class MainView extends JFrame implements Data{
	driverView dView;
	textView tView;
	lineView lView;
	driverController dController;
	GridBagLayout gbl = new GridBagLayout();
//	Drivers drivers;
	public MainView() {
		// TODO Auto-generated constructor stub
		getContentPane().setLayout(gbl);  
		dView = new driverView();
		tView = new textView();
		lView = new lineView();
		this.pack();
		dController = new driverController(createDrivers(), dView);
		getContentPane().add(lView);
		getContentPane().add(dView);
		getContentPane().add(tView);
		makeConstraints(gbl, lView, 1, 1, 0, 0, 1.0, 0.4);
		makeConstraints(gbl, dView, 1, 1, 0, 1, 1.0, 0.5);
		makeConstraints(gbl, tView, 1, 1, 0, 2, 1.0, 0.1);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           
		setSize(1400,600); 
		setLocation(300, 500);
		setVisible(true);
		dController.control();
	}
	
	public  Drivers createDrivers(){
		Drivers ds = new Drivers();
		ds.setAllDrivers(new Driver[numOfDrivers]);
		for(int i=0; i<numOfDrivers;i++){
			Driver driver = new Driver( ""+(char) (65 + i), new int[numOfShifts], new int[numOfShifts]);
			driver.addObserver(tView);
			ds.setDriver(i, driver);
		}
		
		return ds;
		
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
