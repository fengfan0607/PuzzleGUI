package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;

import Components.MyTableModel;
import Components.myJTable;
import common.Data;

public class driverView extends JPanel implements Data{
	JTable table;
	MyTableModel tableModel;
	public driverView() {		
		super(new GridLayout(1, 0));
		table = new JTable();
		tableModel = new MyTableModel();
		table.setModel(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		Color color = UIManager.getColor("Table.gridColor");
		MatteBorder border = new MatteBorder(1, 1, 0, 0, color);
		table.setBorder(border);
		table.setDefaultRenderer(Object.class, new BorderColorRenderer());
		String[] columnNames = new String[numOfShifts];
		for (int i = 0; i < numOfShifts; i++) {
			String string = "Day" + ((i + 2) / 2) + ":" + (i + 1);
			columnNames[i] = string;
		}		
		for (int i = 0; i < numOfDrivers; i++) {
			Object[] values = new Object[numOfDays + 1];
			for (int j = 0; j <= numOfDays; j++) {
				if (j == 0) {
					values[j] ="               " + (char) (65 + i);
				} else {
					values[j] = "";
				}
			}
			tableModel.insertData(values);
		}
//		tableModel.addTableModelListener(new MyTableModelListener(table));
		JScrollPane scrollPane = new JScrollPane(table);
		this.add(scrollPane);
	}
	public MyTableModel getTableModel() {
		return tableModel;
	}
	public void setTableModel(MyTableModel tableModel) {
		this.tableModel = tableModel;
	}
	public JTable getTable() {
		return table;
	}
	public void setTable(JTable table) {
		this.table = table;
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
}

