package Components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableColumn;

import common.Data;


public class myJTable extends JTable implements Data{
	public String space = "               ";
	private MyTableModel tableModel;
	public myJTable() {
		tableModel = new MyTableModel();
		// TODO Auto-generated constructor stub
		this.setModel(tableModel);
		this.setPreferredScrollableViewportSize(new Dimension(500, 70));
		this.setFillsViewportHeight(true);
		Color color = UIManager.getColor("Table.gridColor");
		MatteBorder border = new MatteBorder(1, 1, 0, 0, color);
		this.setBorder(border);
		this.setDefaultRenderer(Object.class, new BorderColorRenderer());
	}
	public MyTableModel getTableModel() {
		return tableModel;
	}
	public void setTableModel(MyTableModel tableModel) {
		this.tableModel = tableModel;
	}
	

}
