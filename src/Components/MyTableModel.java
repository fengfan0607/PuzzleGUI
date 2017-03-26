package Components;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class MyTableModel extends AbstractTableModel {
	
	private String[] columnNames;
	public final Object[] longValues;
	private Vector data;
	public MyTableModel(String[] columnNames,Object[] longValues) {
		// TODO Auto-generated constructor stub
		this.columnNames = columnNames;
		this.longValues = longValues;
		data = new Vector<>();
	}
	
//	private String[] columnNames = {"Driver", "Day1","Day1", "Day2","Day2", "Day3","Day3", "Day4","Day4", "Day5","Day5", "Day6", "Day6","Day7","Day7","Day8","Day8","Day9","Day9", "Day10","Day10", "Day11","Day11", "Day12","Day12", "Day13","Day13", "Day14", "Day14"};
//	private Vector data = new Vector();
//
//	public final Object[] longValues = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };


	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		return ((Vector) data.get(row)).get(col);
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	public void setValueAt(Object value, int row, int col) {
		((Vector) data.get(row)).setElementAt(value, col);
		fireTableCellUpdated(row, col);
	}

	public boolean isCellEditable(int row, int col) {
		return true;
	}

	public void insertData(Object[] values) {
		data.add(new Vector());
		for (int i = 0; i < values.length; i++) {
			((Vector) data.get(data.size() - 1)).add(values[i]);
		}
		fireTableDataChanged();
	}
	
	public void removeRow(int row) {
		data.removeElementAt(row);
		fireTableDataChanged();
	}
}

class BorderColorRenderer extends DefaultTableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Border border;
		
		border = BorderFactory.createMatteBorder(0, 0, 1, 1, Color.CYAN);
		JComponent comp = (JComponent) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
				column);
		comp.setBorder(border);
		return comp;
	}
}
