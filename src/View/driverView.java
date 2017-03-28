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

import Components.BackgroundCellRenderer;
import Components.ColumnGroup;
import Components.GroupableTableHeader;
import Components.GroupableTableHeaderUI;
import Components.ImageCellRendere;
import Components.MyTableModel;
import Components.TablePopUpMenu;
import Components.TransferableImage;
import Controller.myTableClickListener;
import Controller.myTableMouseListener;
import common.Data;

public class driverView extends JPanel implements Data {
	JTable table;
	MyTableModel tableModel;
	TablePopUpMenu popUpMenu;
	BackgroundCellRenderer renderer1;
	ImageCellRendere renderer2;
	String[] columnNames;
	Object[] longValues;
	boolean flag = false;
	public driverView() {
		super(new GridLayout(1, 0));
		renderer1 = new BackgroundCellRenderer();
		renderer2 = new ImageCellRendere();
		table = new JTable(){
			protected JTableHeader createDefaultTableHeader() {
				  return new GroupableTableHeader(columnModel);
		      }
			
			@Override
			public TableCellRenderer getCellRenderer(int row, int column) {
				// TODO Auto-generated method stub
				return renderer1;
			}
		};
		columnNames = new String[] { "Driver", "Day1", "Day1", "Day2", "Day2", "Day3", "Day3", "Day4", "Day4", "Day5",
				"Day5", "Day6", "Day6", "Day7", "Day7", "Day8", "Day8", "Day9", "Day9", "Day10", "Day10", "Day11",
				"Day11", "Day12", "Day12", "Day13", "Day13", "Day14", "Day14" };
		longValues = new Object[] { "",new ImageIcon(), new ImageIcon(), new ImageIcon(), new ImageIcon(), new ImageIcon(), new ImageIcon(), new ImageIcon(), new ImageIcon(), new ImageIcon(),new ImageIcon(), new ImageIcon(), new ImageIcon(), new ImageIcon(), new ImageIcon(), new ImageIcon(), new ImageIcon(), new ImageIcon(), new ImageIcon(),
				new ImageIcon(), new ImageIcon(), new ImageIcon(), new ImageIcon(), new ImageIcon(), new ImageIcon(),new ImageIcon(), new ImageIcon(), new ImageIcon(), new ImageIcon() };

		tableModel = new MyTableModel(columnNames, longValues);
		table.setModel(tableModel);
		table.setDefaultRenderer(Object.class, renderer1);
		table.setFillsViewportHeight(true);
		Color color = UIManager.getColor("Table.gridColor");
		MatteBorder border = new MatteBorder(1, 1, 0, 0, color);
		table.setBorder(border);
		table.setCellSelectionEnabled(true);
//		table.setDefaultRenderer(Object.class, renderer2);
		insertData();
		groupHeader();
		popUpMenu = new TablePopUpMenu();
		table.setComponentPopupMenu(popUpMenu);
		table.setRowHeight(rowHeight);
		insertIcon();
		JScrollPane scrollPane = new JScrollPane(table);
		// ***************************************************
		// table.addMouseListener(new myTableMouseListener(renderer));
		// ***********************************************
		this.add(scrollPane);
		
	}

	public TablePopUpMenu getPopUpMenu() {
		return popUpMenu;
	}

	public void setPopUpMenu(TablePopUpMenu popUpMenu) {
		this.popUpMenu = popUpMenu;
	}

	public void insertData() {
//		ImageIcon iconSun =readImage("sun.png");
//		ImageIcon iconMoon =readImage("moon.jpg");
//		for (int i = 0; i < numOfDrivers; i++) {
//			Object[] values = new Object[numOfShifts + 1];	
//			for (int j = 0; j <= numOfShifts; j++) {
//				if (j == 0) {
//					values[j] ="          " + (i+1);
//				} else {
//					if(j%2==1){
//						values[j] = iconSun;
//					}else{
//						values[j] = iconMoon;
//					}
//					
//				}
//			}
//			tableModel.insertData(values);
//		}
		for (int i = 0; i < numOfDrivers; i++) {
			Object[] values = new Object[numOfShifts + 1];
			for (int j = 0; j <= numOfShifts; j++) {
				if (j == 0) {
					values[j] = "          " + (char) (65 + i);
				} else {
					values[j] = "";
				}
			}
			tableModel.insertData(values);
		}
	}
	public void insertIcon(){
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

	
}
