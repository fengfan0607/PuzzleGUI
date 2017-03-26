
package View;

import common.Data;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.TransferHandler.TransferSupport;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import Components.ColumnGroup;
import Components.GroupableTableHeader;
import Components.MyTableModel;
import Components.TransferableImage;

public class lineView extends JPanel implements Data{
	JTable table;
	MyTableModel tableModel;
	ImageIcon iconSun;
	ImageIcon iconMoon;
	public lineView() {
		// TODO Auto-generated constructor stub
		super(new GridLayout(1, 0));
		String[] columnNames = {"Lines", "Day1","Day1", "Day2","Day2", "Day3","Day3", "Day4","Day4", "Day5","Day5", "Day6", "Day6","Day7","Day7","Day8","Day8","Day9","Day9", "Day10","Day10", "Day11","Day11", "Day12","Day12", "Day13","Day13", "Day14", "Day14"};
		Object[] longValues = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };

		tableModel = new MyTableModel(columnNames,longValues);
		table = new JTable(tableModel){
			protected JTableHeader createDefaultTableHeader() {
				  return new GroupableTableHeader(columnModel);
		      }
		};
		table.setFillsViewportHeight(true);
		Color color = UIManager.getColor("Table.gridColor");
		MatteBorder border = new MatteBorder(1, 1, 0, 0, color);
		table.setBorder(border);
		table.setDefaultRenderer(Object.class, new BorderColorRenderer());
		table.setCellSelectionEnabled(true);
		table.setRowHeight(rowHeight);
//		table.setDragEnabled(true);
//		table.setTransferHandler(new MyTransferHandler());
	
		iconSun =readImage("sun.png");
		iconMoon =readImage("moon.jpg");
		insertData();
//		insertIcon();
		groupHeader();
		DragSource dragSource = DragSource.getDefaultDragSource();
		dragSource.createDefaultDragGestureRecognizer(table,
		DnDConstants.ACTION_COPY_OR_MOVE, new DragGestureListener()
		{
		public void dragGestureRecognized(DragGestureEvent event)
		{
		//当前选择中单元格的内容
		int row = table.getSelectedRow(); 
		int col = table.getSelectedColumn(); 
		ImageIcon imageicon = (ImageIcon)tableModel.getValueAt(row,col);
		Image image =  imageicon.getImage();
		BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		JLabel label = new JLabel();
		label.setIcon(imageicon);
		Transferable transferable = new TransferableImage(bimage);
		event.startDrag(
		DragSource.DefaultCopyDrop,
		transferable);
			}
		});	
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);
	}
	public void insertData(){
		for (int i = 0; i < numOfLines; i++) {
			Object[] values = new Object[numOfShifts + 1];	
			for (int j = 0; j <= numOfShifts; j++) {
				if (j == 0) {
					values[j] ="          " + (i+1);
				} else {
					if(j%2==1){
						values[j] = iconSun;
					}else{
						values[j] = iconMoon;
					}
					
				}
			}
			tableModel.insertData(values);
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
	
	class MyTransferHandler extends TransferHandler{
      
        public Transferable createTransferable(JComponent c){
        	System.err.println("selcted");
        	JTable table = (JTable)c;
            int row = table.getSelectedRow();
            int col = table.getSelectedColumn();

            if(table.getValueAt(row, col) instanceof ImageIcon)
                return (Transferable)table.getValueAt(row, col);
            return null;
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
}
