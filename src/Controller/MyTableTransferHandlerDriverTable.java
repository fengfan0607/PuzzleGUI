package Controller;

import java.awt.Color;
import java.awt.Label;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.io.IOException;
import java.util.ArrayList;

import javax.activation.ActivationDataFlavor;
import javax.activation.DataHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.InternationalFormatter;
import javax.swing.text.DefaultEditorKit.CutAction;

import Components.MyTableModel;
import Model.Driver;
import Model.Drivers;
import Model.Line;
import Model.Lines;
import View.myImageIcon;

public class MyTableTransferHandlerDriverTable extends TransferHandler {
	private final DataFlavor localObjectFlavor;

	private JTable table;
	private int dragRow;
	private int dragCol;
	private int dropRow;
	private int dropCol;
	private Drivers drivers;
	private Lines lines;

	public MyTableTransferHandlerDriverTable(JTable table, Drivers d, Lines l) {
		// TODO Auto-generated constructor stub
		localObjectFlavor = new ActivationDataFlavor(myImageIcon.class, DataFlavor.javaJVMLocalObjectMimeType,
				"JLabel");
		this.table = table;
		this.drivers = d;
		this.lines = l;
	}

	public MyTableTransferHandlerDriverTable(JTable table) {
		// TODO Auto-generated constructor stub
		localObjectFlavor = new ActivationDataFlavor(myImageIcon.class, DataFlavor.javaJVMLocalObjectMimeType,
				"JLabel");
		this.table = table;
	}

	public int getSourceActions(JComponent c) {
		System.err.println("get resource");
		return DnDConstants.ACTION_COPY_OR_MOVE;
	}

	@Override
	public Transferable createTransferable(JComponent comp) {
		int row = table.getSelectedRow();
		int col = table.getSelectedColumn();
		myImageIcon value = (myImageIcon) table.getModel().getValueAt(row, col);
		final DataHandler dh = new DataHandler(value, localObjectFlavor.getMimeType());
		System.out.println("createTransferable" + value.getRow() + "," + value.getColumn());
		return new Transferable() {
			@Override
			public DataFlavor[] getTransferDataFlavors() {
				ArrayList<DataFlavor> list = new ArrayList<>();
				for (DataFlavor f : dh.getTransferDataFlavors()) {
					list.add(f);
				}
				return list.toArray(dh.getTransferDataFlavors());
			}

			public boolean isDataFlavorSupported(DataFlavor flavor) {
				for (DataFlavor f : getTransferDataFlavors()) {
					if (flavor.equals(f)) {
						return true;
					}
				}
				return false;
			}

			public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
				return dh.getTransferData(flavor);
			}
		};
	}

	@Override
	public boolean canImport(TransferHandler.TransferSupport info) {
		JTable.DropLocation dl = (JTable.DropLocation) info.getDropLocation();
		int row = dl.getRow();
		int col = dl.getColumn();
		System.err.println("position" + row + "," + col);
		dropCol = col;
		dropRow = row;
		try {
			myImageIcon src = (myImageIcon) info.getTransferable().getTransferData(localObjectFlavor);
			dragRow = src.getRow();
			dragCol = src.getColumn();
		} catch (UnsupportedFlavorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Driver dri = drivers.getDriver(row);
		int occupation = dri.getPrePlanAtPosition(col - 1);
		if (col != dragCol || occupation == 1) {
			return false;
		}
		if (!info.isDataFlavorSupported(localObjectFlavor)) {
			System.err.println("not supported data flavor");
			return false;
		}
		info.setShowDropLocation(true);
		System.err.println("can import");
		return true;
	}

	@Override
	public boolean importData(TransferSupport support) {
		JTable.DropLocation dl = (JTable.DropLocation) support.getDropLocation();
		int row = dl.getRow();
		int col = dl.getColumn();
		System.err.println(row + "" + col + " " + dragCol);
		if (!support.isDrop()) {
			return false;
		}

		if (!canImport(support)) {
			return false;
		}

		try {
			myImageIcon src = (myImageIcon) support.getTransferable().getTransferData(localObjectFlavor);
			dragRow = src.getRow();
			dragCol = src.getColumn();
			System.err.println("data from " + dragRow + "," + dragCol);
			JLabel label = (JLabel) table.getValueAt(row, col);
			Color color = label.getBackground();
			label = new JLabel(src);
			label.setOpaque(true);
			label.setBackground(color);
			table.setValueAt(label, row, col);
			updateDriver(row, col, dragRow + 1);
			Line line = lines.getLine(dragRow);
			line.setLineShiftAtPosition(dragCol - 1, dropRow + 1);
			System.err.println(line.toString());
			System.err.println("inserted");
		} catch (UnsupportedFlavorException e) {
			return false;
		} catch (IOException e) {
			return false;
		}

		// tableModel.setValueAt(data, row, col);

		return true;
	}

	@Override
	protected void exportDone(JComponent c, Transferable data, int action) {
		System.out.println("exportDone");
		// table.getModel().getValueAt(rowIndex, columnIndex)
		if (action == TransferHandler.MOVE) {
			System.err.println("moved" + dragRow + dragCol);
			table.getModel().setValueAt(new myImageIcon(), dragRow, dragCol);
		}
	}

	public void updateDriver(int row, int col, int value) {
		Driver d = drivers.getDriver(row);
		drivers.setDriver(row, updatePostPlan(d, col, value));
	}

	public Driver updatePostPlan(Driver d, int column, int value) {
		d.setPostPlanAtPosition(column - 1, value);
		return d;
	}
}
