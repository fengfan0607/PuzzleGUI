package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DropMode;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import Components.TablePopUpMenu;
import Components.TablePopUpMenuUnDoPlan;
import Model.Driver;
import Model.Drivers;
import Model.Lines;
import View.driverView;
import View.lineView;
import View.toolBarView;

public class driverController {
	private driverView dView;
	private Drivers drivers;
	private Lines lines;
	private TableModelListener tableModelListener;
	private toolBarView tBarView;
	private TablePopUpMenu popUpMenu;
	private TablePopUpMenuUnDoPlan unDoPlan;
	private lineView lView;

	public driverController(Drivers d, driverView dView, Lines l, toolBarView tBarView, lineView lView) {
		// TODO Auto-generated constructor stub
		this.dView = dView;
		this.drivers = d;
		this.lines = l;
		this.tBarView = tBarView;
		this.lView = lView;
	}

	public void control() {

		tBarView.getPrePlan().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				popUpMenu = new TablePopUpMenu();
				dView.getTable().setComponentPopupMenu(popUpMenu);

				dView.getTableModel().addTableModelListener(new myTableClickListener(drivers, dView));
				popUpMenu.getMenuItemDayOff().addActionListener(new myTableMenuListener(dView.getTable(), drivers));
				popUpMenu.getMenuItemPreferOff().addActionListener(new myTableMenuListener(dView.getTable(), drivers));
				popUpMenu.getMenuItemPreferWork().addActionListener(new myTableMenuListener(dView.getTable(), drivers));

			}
		});

		tBarView.getPostPlan().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				unDoPlan = new TablePopUpMenuUnDoPlan();
				dView.getTable().setComponentPopupMenu(unDoPlan);
				dView.getTable().setDropMode(DropMode.USE_SELECTION);
				dView.getTable().setDragEnabled(true);
				dView.getTable()
						.setTransferHandler(new MyTableTransferHandlerDriverTable(dView.getTable(), drivers, lines));
				unDoPlan.getUnPlan().addActionListener(new myTableMenuUnDoPlanListener(lView, dView, drivers, lines));

			}
		});

	}

}
