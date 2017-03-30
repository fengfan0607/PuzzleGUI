package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DropMode;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import Components.TablePopUpMenu;
import Components.TablePopUpMenuUnDoPlan;
import Model.Driver;
import Model.Drivers;
import Model.Lines;
import Model.Point;
import View.driverMapLineView;
import View.driverView;
import View.lineView;
import View.toolBarView;
import common.Data;
import optimization.algorithm_new;

public class driverController implements Data {
	private driverView dView;
	private Drivers drivers;
	private Lines lines;
	private TableModelListener tableModelListener;
	private toolBarView tBarView;
	private TablePopUpMenu popUpMenu;
	private TablePopUpMenuUnDoPlan unDoPlan;
	private lineView lView;
	private Point point;
	private driverMapLineView driverMapLineView;

	public driverController(Drivers d, driverView dView, Lines l, toolBarView tBarView, lineView lView, Point p,
			driverMapLineView dlvisew) {
		// TODO Auto-generated constructor stub
		this.dView = dView;
		this.drivers = d;
		this.lines = l;
		this.tBarView = tBarView;
		this.lView = lView;
		this.point = p;
		this.driverMapLineView = dlvisew;
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
				readDriverForBus(driverMapLineView, drivers);
				calculation.Cal(drivers, point);
				unDoPlan = new TablePopUpMenuUnDoPlan();
				dView.getTable().setComponentPopupMenu(unDoPlan);
				dView.getTable().setDropMode(DropMode.USE_SELECTION);
				dView.getTable().setDragEnabled(true);
				dView.getTable()
						.setTransferHandler(new MyTableTransferHandlerDriverTable(dView.getTable(), drivers, lines));
				unDoPlan.getUnPlan().addActionListener(new myTableMenuUnDoPlanListener(lView, dView, drivers, lines));

			}
		});

		tBarView.getLoadData1().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				LoadData.loadData(dView.getTable(), drivers, 1);

			}
		});
		tBarView.getLoadData2().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				LoadData.loadData(dView.getTable(), drivers, 2);

			}
		});

		tBarView.getOptimization().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int[][] prePlanDrivers = new int[numOfDrivers][numOfShifts];
				int[][] driversForLines = new int[numOfDrivers][numOfLines];

				for (int i = 0; i < numOfDrivers; i++) {
					prePlanDrivers[i] = drivers.getDriver(i).getPrePlan();
					driversForLines[i] = drivers.getDriver(i).getDriveLines();
				}
				algorithm_new aNew = new algorithm_new(prePlanDrivers, driversForLines);
				int[][] postPlan = aNew.optimization();
				StringBuilder sBuilder = new StringBuilder();
				for (int i = 0; i < numOfDrivers; i++) {
					Driver d = drivers.getDriver(i);
					d.setPostPlan(postPlan[i]);
					drivers.setDriver(i, d);
					sBuilder.append(drivers.getDriver(i).toStringPostPlan() + "\n");
				}
				System.err.println(sBuilder.toString());
				aNew.drawing(dView, lView, drivers);

			}
		});
		tBarView.getResetPlan().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

	}

	public void readDriverForBus(driverMapLineView view, Drivers drivers) {
		JTextField[] textFields = view.getLineTextField();
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0; i < numOfLines; i++) {
			String line = textFields[i].getText();
			List<String> list = Arrays.asList(line.split(","));
			System.err.println(list);
			for (String s : list) {
				for (int j = 0; j < numOfDrivers; j++) {
					Driver driver = drivers.getDriver(j);
					if (s.equals(driver.getName())) {
						driver.setDriveLines(i);
					}
				}
			}
		}
		// for (int i = 0; i < numOfDrivers; i++) {
		// sBuilder.append(Arrays.toString(drivers.getDriver(i).getDriveLines())
		// + "\n");
		// }
	}

}
