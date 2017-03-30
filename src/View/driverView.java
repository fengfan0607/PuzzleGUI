package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.BorderFactory;
import javax.swing.DropMode;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import Components.ColumnGroup;
import Components.GroupableTableHeader;
import Components.GroupableTableHeaderUI;
import Components.JLabelRenderer;
import Components.MyTableModel;
import Components.TablePopUpMenu;
import Controller.myTableClickListener;
import Model.Driver;
import common.Data;

public class driverView extends JPanel implements Observer, Data {
	JTable table;
	MyTableModel tableModel;
	TablePopUpMenu popUpMenu;
	String[] columnNames;
	Object[] longValues;
	boolean flag = false;

	public driverView() {
		super(new GridLayout(1, 0));
		table = new JTable() {
			protected JTableHeader createDefaultTableHeader() {
				return new GroupableTableHeader(columnModel);
			}

		};
		columnNames = new String[] { "Driver", "Day1", "Day1", "Day2", "Day2", "Day3", "Day3", "Day4", "Day4", "Day5",
				"Day5", "Day6", "Day6", "Day7", "Day7", "Day8", "Day8", "Day9", "Day9", "Day10", "Day10", "Day11",
				"Day11", "Day12", "Day12", "Day13", "Day13", "Day14", "Day14" };
		longValues = new Object[] { "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "" };

		tableModel = new MyTableModel(columnNames, longValues);
		table.setModel(tableModel);
		table.setFillsViewportHeight(true);
		Color color = UIManager.getColor("Table.gridColor");
		MatteBorder border = new MatteBorder(1, 1, 1, 1, color);
		// table.setBorder(border);
		table.setGridColor(Color.BLACK);
		table.setDefaultRenderer(Object.class, new JLabelRenderer());
		table.setCellSelectionEnabled(true);
		insertData();
		groupHeader();
		table.setRowHeight(rowHeight);
		JScrollPane scrollPane = new JScrollPane(table);
		this.add(scrollPane);
	}

	public void insertData() {
		for (int i = 0; i < numOfDrivers; i++) {
			Object[] values = new Object[numOfShifts + 1];
			for (int j = 0; j <= numOfShifts; j++) {
				if (j == 0) {
					values[j] = "  " + (char) (65 + i);
				} else {
					JLabel label = new JLabel();
					label.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
					values[j] = label;
				}
			}
			tableModel.insertData(values);
		}
	}

	public void insertIcon() {
		ImageIcon testIcon = readImage("sun.png");
		tableModel.setValueAt("line1", 1, 1);
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

	public void groupHeader() {
		TableColumnModel cm = table.getColumnModel();
		for (int i = 0; i < numOfDays; i++) {
			ColumnGroup g_day = new ColumnGroup("Day" + "" + (i + 1));
			g_day.add(cm.getColumn(2 * i + 1));
			g_day.add(cm.getColumn(2 * i + 2));
			GroupableTableHeader header = (GroupableTableHeader) table.getTableHeader();
			header.addColumnGroup(g_day);
		}

	}

	public ImageIcon readImage(String fileNmes) {
		ImageIcon image = null;
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(fileNmes));
			Image dimg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
			image = new ImageIcon(dimg);
			return image;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
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

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (o instanceof Driver) {
			int[] lines = ((Driver) o).getDriveLines();
			String string = "";
			for (int i = 0; i < numOfLines; i++) {
				if (lines[i] != 0) {
					string += (i + 1) + ",";
				}
			}
			String driverName = ((Driver) o).getName();
			int rowIndex = (int) (((Driver) o).getName().charAt(0)) - 65;
			table.getModel().setValueAt(driverName + "(" + string + ")", rowIndex, 0);
		}

	}

}
