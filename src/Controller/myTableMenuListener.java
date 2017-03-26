package Controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.TabableView;

import Components.BackgroundCellRenderer;
import Model.Driver;
import Model.Drivers;
import common.Data;

public class myTableMenuListener implements ActionListener,Data {
	
	JTable table ; 
//	JMenuItem item;
	BackgroundCellRenderer renderer;
	Drivers drivers;
	public myTableMenuListener(JTable table, Drivers d) {
		// TODO Auto-generated constructor stub
		this.table = table;
		this.drivers = d;
	}
	
	public void actionPerformed(ActionEvent event) {	
		JMenuItem menu = (JMenuItem) event.getSource();
//		System.err.println(menu.i);
		if (menu.getName()==menuItemPreferOffName) {
			BackgroundCellRenderer renderer = new BackgroundCellRenderer();
			int col = table.getSelectedColumn();
			int row = table.getSelectedRow();
			if(table.isCellSelected(row, col)){
				renderer = (BackgroundCellRenderer) table.getCellRenderer(row, col);
				renderer.setBackgroundColour(row, col, Color.gray);
				updateDriver(row, col, 2);
				if(col%2==0){
					renderer.setBackgroundColour(row, col-1, Color.gray);
					updateDriver(row, col-1, 2);
				}else{
					renderer.setBackgroundColour(row, col+1, Color.gray);
					updateDriver(row, col+1, 2);
				}
				
			}
		} else if (menu.getName() == menuItemDayOffName) {
			int col = table.getSelectedColumn();
			int row = table.getSelectedRow();
			if(table.isCellSelected(row, col)){
				renderer = (BackgroundCellRenderer) table.getCellRenderer(row, col);
				renderer.setBackgroundColour(row, col, Color.BLACK);
				updateDriver(row, col, 1);
				if(col%2==0){
					renderer.setBackgroundColour(row, col-1, Color.BLACK);
					updateDriver(row, col-1, 1);
				}else{
					renderer.setBackgroundColour(row, col+1, Color.BLACK);
					updateDriver(row, col+1, 1);
				}
				
			}
		} else if (menu.getName() == menuItemPreferWorkName) {
			int col = table.getSelectedColumn();
			int row = table.getSelectedRow();
			if(table.isCellSelected(row, col)){
				renderer = (BackgroundCellRenderer) table.getCellRenderer(row, col);
				renderer.setBackgroundColour(row, col, Color.YELLOW);
				updateDriver(row, col, 4);
			}
		}
	}
	
    public void updateDriver(int row, int col,int value){
    	Driver d = drivers.getDriver(row);
		drivers.setDriver(row, updatePrePlan(d,col,row,value));
		System.err.println(drivers.getDriver(row).toString());
    }
    public Driver updatePrePlan(Driver d, int column,int row,int value){
		d.setPrePlanAtPosition(column-1, value);
		return d;
	}
        
    }



