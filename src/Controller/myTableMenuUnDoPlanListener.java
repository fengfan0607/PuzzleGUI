package Controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.border.LineBorder;

import Model.Driver;
import Model.Drivers;
import Model.Line;
import Model.Lines;
import View.driverView;
import View.lineView;
import View.myImageIcon;
import common.Data;

public class myTableMenuUnDoPlanListener implements ActionListener, Data {

	lineView lView;
	driverView dView;
	Drivers drivers;
	Lines lines;

	public myTableMenuUnDoPlanListener(lineView lView, driverView dView, Drivers drivers, Lines lines) {
		// TODO Auto-generated constructor stub
		this.lView = lView;
		this.dView = dView;
		this.drivers = drivers;
		this.lines = lines;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JMenuItem menu = (JMenuItem) event.getSource();
		int row = dView.getTable().getSelectedRow();
		int col = dView.getTable().getSelectedColumn();

		// undo driver view
		Driver driver = drivers.getDriver(row);
		int undoLineshift = driver.getPostPlanAtPosition(col - 1);
		driver.setPostPlanAtPosition(col - 1, 0);
		drivers.setDriver(row, driver);
		JLabel label = (JLabel) dView.getTable().getValueAt(row, col);
		Color backGound = label.getBackground();
		ImageIcon icon = (ImageIcon) label.getIcon();
		JLabel newLabel = new JLabel();
		newLabel.setBackground(backGound);
		newLabel.setBorder(new LineBorder(Color.BLACK));
		dView.getTable().setValueAt(newLabel, row, col);

		// undo lineview
		Line line = lines.getLine(undoLineshift - 1);
		System.err.println(line);
		line.setLineShiftAtPosition(col - 1, 0);
		System.err.println(line);
		myImageIcon newLineIcon = new myImageIcon(undoLineshift - 1, col, icon.getImage());
		lView.getTable().setValueAt(newLineIcon, undoLineshift - 1, col);
	}

}
