package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import Components.ColumnGroup;
import Components.GroupableTableHeader;
import Components.GroupableTableHeaderUI;
import Components.MyTableModel;
import Components.TablePopUpMenu;
import common.Data;

public class driverView extends JPanel implements Data{
	JTable table;
	MyTableModel tableModel;
	TablePopUpMenu popUpMenu;
	public driverView() {		
	super(new GridLayout(1, 0));
		table = new JTable(){  protected JTableHeader createDefaultTableHeader() {
			  return new GroupableTableHeader(columnModel);
	      }
	    };	
		tableModel = new MyTableModel();
		table.setModel(tableModel);
		table.setFillsViewportHeight(true);
		Color color = UIManager.getColor("Table.gridColor");
		MatteBorder border = new MatteBorder(1, 1, 0, 0, color);
		table.setBorder(border);
		table.setDefaultRenderer(Object.class, new BorderColorRenderer());
		insertData();
		groupHeader();
		popUpMenu = new TablePopUpMenu();
		table.setComponentPopupMenu(popUpMenu);
		JScrollPane scrollPane = new JScrollPane(table);
		this.add(scrollPane);
	}
	
	public TablePopUpMenu getPopUpMenu() {
		return popUpMenu;
	}

	public void setPopUpMenu(TablePopUpMenu popUpMenu) {
		this.popUpMenu = popUpMenu;
	}

	public void insertData(){
		for (int i = 0; i < numOfDrivers; i++) {
			Object[] values = new Object[numOfShifts + 1];
			for (int j = 0; j <= numOfShifts; j++) {
				if (j == 0) {
					values[j] ="          " + (char) (65 + i);
				} else {
					values[j] = "";
				}
			}
			tableModel.insertData(values);
		}
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
	
	public void groupHeader(){
	    TableColumnModel cm = table.getColumnModel();
	    for(int i=0;i<numOfDays;i++){
	    	ColumnGroup g_day = new ColumnGroup("Day"+ ""+ (i+1));
	    	g_day.add(cm.getColumn(2*i+1));
	    	g_day.add(cm.getColumn(2*i+2));
	 	    GroupableTableHeader header = (GroupableTableHeader) table.getTableHeader();
	 	    header.addColumnGroup(g_day);
	    }
//		TableCellRenderer renderer =  new DefaultTableCellRenderer() {
//	        public Component getTableCellRendererComponent(JTable table, Object value,
//	                         boolean isSelected, boolean hasFocus, int row, int column) {
//	          JTableHeader header = table.getTableHeader();
//	          if (header != null) {
//	            setForeground(header.getForeground());
//	            setBackground(header.getBackground());
//	            setFont(header.getFont());
//	          }
//	          setHorizontalAlignment(JLabel.CENTER);
//	          setText((value == null) ? "" : value.toString());
//	          setBorder(UIManager.getBorder("TableHeader.cellBorder"));
//	          return this;
//	        }
//	      };
//	      TableColumnModel model = table.getColumnModel();
//	      for (int i=0;i<model.getColumnCount();i++) {
//	        model.getColumn(i).setHeaderRenderer(renderer);
//	      }
	   
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

