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
		tableModelListener = new TableModelListener() {		
			public void tableChanged(TableModelEvent e) {
				int firstRow = e.getFirstRow();
				int lastRow = e.getLastRow();
				int index = e.getColumn();

				switch (e.getType()) {
				case TableModelEvent.INSERT:
					for (int i = firstRow; i <= lastRow; i++) {
						System.out.println(i);
					}
					break;
				case TableModelEvent.UPDATE:
					if (firstRow == TableModelEvent.HEADER_ROW) {
						if (index == TableModelEvent.ALL_COLUMNS) {
							System.out.println("A column was added");
						} else {
							System.out.println(index + "in header changed");
						}
					} else {
						for (int i = firstRow; i <= lastRow; i++) {
							if (index == TableModelEvent.ALL_COLUMNS) {
								System.out.println("All columns have changed");
							} else {
								
								Driver d = drivers.getDriver(firstRow);
							
								drivers.setDriver(firstRow, updatePrePlan(d,index,firstRow));
								System.err.println(drivers.getDriver(firstRow).toString());
							}
						}
					}
					break;
				case TableModelEvent.DELETE:
					for (int i = firstRow; i <= lastRow; i++) {
						System.out.println(i);
					}
					break;
				}
			}
		};
		dView.getTableModel().addTableModelListener(tableModelListener);
	}
	
	public Driver updatePrePlan(Driver d, int column,int row){
		int value = Integer.valueOf((String) dView.getTableModel().getValueAt(row, column)) ;
		d.setPrePlanAtPosition(column-1, value);
		return d;
	}
	
}
