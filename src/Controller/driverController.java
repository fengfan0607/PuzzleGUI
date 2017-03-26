package Controller;

import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import Model.Driver;
import Model.Drivers;
import View.driverView;

public class driverController {
	private driverView dView;
	private Drivers drivers;
	private TableModelListener tableModelListener;
	public driverController(Drivers d,driverView dView) {
		// TODO Auto-generated constructor stub
		this.dView = dView;
		this.drivers = d;
	}
	
	public void control() {
		dView.getTableModel().addTableModelListener(new myTableClickListener(drivers, dView));
		dView.getPopUpMenu().getMenuItemDayOff().addActionListener(new myTableMenuListener(dView.getTable(),drivers));
		dView.getPopUpMenu().getMenuItemPreferOff().addActionListener(new myTableMenuListener(dView.getTable(),drivers));
		dView.getPopUpMenu().getMenuItemPreferWork().addActionListener(new myTableMenuListener(dView.getTable(),drivers));
	}
	
	
	
}
