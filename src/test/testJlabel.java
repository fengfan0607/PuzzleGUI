package test;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;

import Components.MyTableModel;
import Components.TablePopUpMenu;
import Controller.myTableMenuListener;
import Model.Drivers;
import View.MainView;
import common.Data;

public class testJlabel extends JFrame implements Data{
	MyTableModel tableModel;
	JTable table;
	TablePopUpMenu popUpMenu;
	public testJlabel() {
		// TODO Auto-generated constructor stub
		String[] columnNames = new String[] { "Driver", "Day1", "Day2"};
		Object[] longValues = new Object[] {"","",""};

		 table = new JTable();
		 tableModel = new MyTableModel(columnNames, longValues);
		table.setModel(tableModel);
//		table.setDefaultRenderer(Object.class, renderer1);
		table.setFillsViewportHeight(true);
		Color color = UIManager.getColor("Table.gridColor");
		MatteBorder border = new MatteBorder(1, 1, 0, 0, color);
		table.setBorder(border);
		table.setCellSelectionEnabled(true);
//		tableModel.isCellEditable(row, col)
		insertData();
		JScrollPane scrollPane = new JScrollPane(table);
		insertLabel();
		popUpMenu = new TablePopUpMenu();
		popUpMenu.getMenuItemDayOff().addActionListener(new myTableMenuListener(table,new Drivers()));
		popUpMenu.getMenuItemPreferOff().addActionListener(new myTableMenuListener(table,new Drivers()));
		popUpMenu.getMenuItemPreferWork().addActionListener(new myTableMenuListener(table,new Drivers()));
		table.setComponentPopupMenu(popUpMenu);
		this.add(scrollPane);
		this.setSize(1200, 500);
	}
	public void insertData() {

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
	
	public void insertLabel(){
		Icon image = readImage("sun.png");
		JLabel label = new JLabel(image);
//		label.setIcon(readImage("sun.png"));
		label.setOpaque(true);
		label.setBackground(Color.RED);
		
		table.getColumnModel().getColumn(1).setCellRenderer(new Renderer());
		table.setValueAt(label, 1, 1);
		
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
	public static void main(String[] args) {
		 SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					testJlabel mView =new testJlabel();
					mView.setVisible(true);
					
				}
			});
		
	}
	 
	 public class TranslucentLabel extends JLabel {

	        public TranslucentLabel(String text, Icon icon, int horizontalAlignment) {
	            super(text, icon, horizontalAlignment);
	        }

	        public TranslucentLabel(String text, int horizontalAlignment) {
	            super(text, horizontalAlignment);
	        }

	        public TranslucentLabel(String text) {
	            super(text);
	        }

	        public TranslucentLabel(Icon image, int horizontalAlignment) {
	            super(image, horizontalAlignment);
	        }

	        public TranslucentLabel(Icon image) {
	            super(image);
	        }

	        public TranslucentLabel() {
	            super();
	        }

	        @Override
	        public boolean isOpaque() {
	            return false;
	        }

	        @Override
	        protected void paintComponent(Graphics g) {
	            Graphics2D g2d = (Graphics2D) g.create();
	            g2d.setColor(getBackground());
	            g2d.fillRect(0, 0, getWidth(), getHeight());
	            super.paintComponent(g2d);
	            g2d.dispose();
	        }
	    }
	
}
