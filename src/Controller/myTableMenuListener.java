package Controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.TabableView;

import Components.JLabelRenderer;
import Model.Driver;
import Model.Drivers;
import common.Data;
import test.Renderer;

public class myTableMenuListener implements ActionListener, Data {

	JTable table;
	// JMenuItem item;
	Drivers drivers;

	public myTableMenuListener(JTable table, Drivers d) {
		// TODO Auto-generated constructor stub
		this.table = table;
		this.drivers = d;
	}

	public void actionPerformed(ActionEvent event) {
		JMenuItem menu = (JMenuItem) event.getSource();
		if (menu.getName() == menuItemPreferOffName) {
			int col = table.getSelectedColumn();
			int row = table.getSelectedRow();
			if (table.isCellSelected(row, col)) {
				setColor(row, col, Color.gray);
				updateDriver(row, col, 2);
				if (col % 2 == 0) {
					setColor(row, col - 1, Color.gray);
					updateDriver(row, col - 1, 2);
				} else {
					setColor(row, col + 1, Color.gray);
					updateDriver(row, col + 1, 2);
				}

			}
		} else if (menu.getName() == menuItemDayOffName) {
			int col = table.getSelectedColumn();
			int row = table.getSelectedRow();
			if (table.isCellSelected(row, col)) {
				setColor(row, col, Color.black);
				updateDriver(row, col, 1);
				if (col % 2 == 0) {
					setColor(row, col - 1, Color.black);
					updateDriver(row, col - 1, 1);
				} else {
					setColor(row, col + 1, Color.black);
					updateDriver(row, col + 1, 1);
				}

			}
		} else if (menu.getName() == menuItemPreferWorkName) {
			int col = table.getSelectedColumn();
			int row = table.getSelectedRow();
			if (table.isCellSelected(row, col)) {
				setColor(row, col, Color.YELLOW);
				insertIcon(row, col);
				updateDriver(row, col, 4);
			}
		}
	}

	public void setColor(int row, int col, Color color) {
		JLabel label = new JLabel();
		label.setOpaque(true);
		label.setBackground(color);
		table.getColumnModel().getColumn(col).setCellRenderer(new JLabelRenderer());
		table.setValueAt(label, row, col);
	}

	public void insertIcon(int row, int col) {
		ImageIcon testIcon = readImage("sun.png");
		JLabel label = (JLabel) table.getValueAt(row, col);
		Color color = label.getBackground();
		label = new JLabel(testIcon);
		label.setOpaque(true);
		label.setBackground(color);
		// table.getColumnModel().getColumn(col).setCellRenderer(new
		// ImageCellRendere());
		table.setValueAt(label, row, col);
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

	public void updateDriver(int row, int col, int value) {
		Driver d = drivers.getDriver(row);
		drivers.setDriver(row, updatePrePlan(d, col, row, value));
		System.err.println(drivers.getDriver(row).toString());
	}

	public Driver updatePrePlan(Driver d, int column, int row, int value) {
		d.setPrePlanAtPosition(column - 1, value);
		return d;
	}

}
