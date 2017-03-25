package Controller;

import java.awt.Color;
import java.awt.Component;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;

import Model.Driver;
import Model.Drivers;
import View.driverView;

public class myTableClickListener implements TableModelListener{
	Drivers drivers;
	driverView dView;
	public myTableClickListener(Drivers d,driverView dV) {
		// TODO Auto-generated constructor stub
		drivers = d;
		dView=dV;
	}
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
//						TableCellRenderer tableCellRenderer =  dView.getTable().getCellRenderer(firstRow, index);
//						Component component= dView.getTable().prepareRenderer(tableCellRenderer, firstRow, index);
//						component.setBackground(Color.gray);
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
	public Driver updatePrePlan(Driver d, int column,int row){
		int value = Integer.valueOf((String) dView.getTableModel().getValueAt(row, column)) ;
		d.setPrePlanAtPosition(column-1, value);
		return d;
	}

}
