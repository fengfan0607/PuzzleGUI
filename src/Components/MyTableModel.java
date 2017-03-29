package Components;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class MyTableModel extends AbstractTableModel {

	private String[] columnNames;
	public final Object[] longValues;
	private Vector data;

	public MyTableModel(String[] columnNames, Object[] longValues) {
		// TODO Auto-generated constructor stub
		this.columnNames = columnNames;
		this.longValues = longValues;
		data = new Vector<>();
	}

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
		// return ImageIcon.class;
		// return JLabel.class;
		return getValueAt(0, c).getClass();
	}

	public void setValueAt(Object value, int row, int col) {
		((Vector) data.get(row)).setElementAt(value, col);
		fireTableCellUpdated(row, col);
	}

	public boolean isCellEditable(int row, int col) {
		return false;
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
