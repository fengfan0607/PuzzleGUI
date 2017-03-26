package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DropMode;
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
import Components.MyTableModel;
import Components.TablePopUpMenu;
import Controller.myTableClickListener;
import Controller.myTableMouseListener;
import common.Data;

public class driverView extends JPanel implements Data{
	JTable table;
	MyTableModel tableModel;
	TablePopUpMenu popUpMenu;
	BackgroundCellRenderer renderer;
	public driverView() {		
	super(new GridLayout(1, 0));
	renderer = new BackgroundCellRenderer();
		table = new JTable(){  
			protected JTableHeader createDefaultTableHeader() {
			  return new GroupableTableHeader(columnModel);
	      }
		 public TableCellRenderer getCellRenderer(int row, int col) {
             return renderer;
         }
	    };	
		String[] columnNames = {"Driver", "Day1","Day1", "Day2","Day2", "Day3","Day3", "Day4","Day4", "Day5","Day5", "Day6", "Day6","Day7","Day7","Day8","Day8","Day9","Day9", "Day10","Day10", "Day11","Day11", "Day12","Day12", "Day13","Day13", "Day14", "Day14"};
		Object[] longValues = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };

		tableModel = new MyTableModel(columnNames,longValues);
		table.setModel(tableModel);
		table.setFillsViewportHeight(true);
		Color color = UIManager.getColor("Table.gridColor");
		MatteBorder border = new MatteBorder(1, 1, 0, 0, color);
		table.setBorder(border);
//		table.setDefaultRenderer(Object.class, new BorderColorRenderer());
		table.setCellSelectionEnabled(true);
		new DropTarget(table,DnDConstants.ACTION_COPY,new DropTargetAdapter()
		{
		@Override
		public  void drop(DropTargetDropEvent event)    
		{
		try
		{
		event.acceptDrop(DnDConstants.ACTION_MOVE);
		Transferable transferable = event.getTransferable();
		DataFlavor[] flavors = transferable.getTransferDataFlavors();
		//这里简单起见就取0号，
		DataFlavor data = flavors[0];
		Image image = (BufferedImage)transferable.getTransferData(data);
		//得到当前的鼠标位置
		Point p = event.getLocation();
		//获得当前鼠标所在的行、列
		int row = table.rowAtPoint(p);
		int col = table.columnAtPoint(p);
		ImageIcon imageIcon  = readImage("sun.png");
		System.err.println(imageIcon.getDescription());
		tableModel.setValueAt(imageIcon, 2, 2);
//		tableModel.setValueAt(imageIcon,row,col);
		//免得阻塞进程
		event.dropComplete(true);
		}
		catch (Exception e)
		{
		event.dropComplete(true);
		e.printStackTrace();
		}}	
		});
		
//		table.setDropMode(DropMode.INSERT_ROWS);
//		table.setTransferHandler(new MyTransferHandler());
		insertData();
		groupHeader();
		popUpMenu = new TablePopUpMenu();
		table.setComponentPopupMenu(popUpMenu);
		table.setRowHeight(rowHeight);
		JScrollPane scrollPane = new JScrollPane(table);
		//***************************************************
//		table.addMouseListener(new myTableMouseListener(renderer));
		//***********************************************
		
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
	   
	}
	public ImageIcon readImage(String fileNmes){
		ImageIcon image = null;
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(fileNmes));
			Image dimg = img.getScaledInstance(30, 30,Image.SCALE_SMOOTH);
			image = new ImageIcon(dimg);
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
	class MyTransferHandler extends TransferHandler{
        public boolean canImport(JComponent c, DataFlavor[] f){
        	return true;
        }

        public boolean importData(TransferSupport support){
        	
        	 JTable.DropLocation dl = (JTable.DropLocation) support
        	            .getDropLocation();
        	 int row = dl.getRow();
             int col=dl.getColumn();
            
             MyTableModel tableModel=(MyTableModel)table.getModel();
             Image data;
             ImageIcon icon;
//             System.err.println("receive"+ row + col+data);
             try {
            	
                 data = (Image) support.getTransferable().getTransferData(
                         DataFlavor.imageFlavor);
                  icon = new ImageIcon(data);
                 System.err.println("receive"+ row + col+data);
             } catch (UnsupportedFlavorException e) {
                 return false;
             } catch (IOException e) {
                 return false;
             }

             tableModel.setValueAt(icon, row, col);

             return true;
        }

        public Transferable createTransferable(JComponent c){
           
            return null;
        }

    }
}

