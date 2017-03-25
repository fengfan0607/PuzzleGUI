package Controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.TabableView;

import Components.MyCellRenderer;
import common.Data;

public class myTableMenuListener implements ActionListener,Data {
	
	JTable table ; 
//	JMenuItem item;
	public myTableMenuListener(JTable table) {
		// TODO Auto-generated constructor stub
		this.table = table;
//		this.item = menu;
	}
	
	public void actionPerformed(ActionEvent event) {	
		JMenuItem menu = (JMenuItem) event.getSource();
//		System.err.println(menu.i);
		if (menu.getName()==menuItemPreferOffName) {
			MyCellRenderer mcr = new MyCellRenderer();
			int col = table.getSelectedColumn();
			int row = table.getSelectedRow();
			TableCellRenderer tableCellRenderer = table.getCellRenderer(row, col);
			Component component= table.prepareRenderer(tableCellRenderer, row, col);
			component.setBackground(Color.gray);
			System.err.println(row+","+ col + "menu click prefer off");
//			table.getColumnModel().getColumn(x).setCellRenderer(mcr);
//			System.err.println(table.getSelectedRow()+" ," + table.getSelectedColumn()+"menu click prefer off");
		} else if (menu.getName() == menuItemDayOffName) {
			int x = table.getSelectedColumn();
			int y = table.getSelectedRow();
			System.err.println(y+","+ x + "menu click prefer off");
//			System.err.println(table.getSelectedRow() +" ," +table.getSelectedColumn()+"menu click day off");
		} else if (menu.getName() == menuItemPreferWorkName) {
			int x = table.getSelectedColumn();
			int y = table.getSelectedRow();
			System.err.println(y+","+ x + "menu click prefer off");
//			System.err.println(table.getSelectedRow() +" ,"+ table.getSelectedColumn()+"menu click prefer work");
		}
	}


}
