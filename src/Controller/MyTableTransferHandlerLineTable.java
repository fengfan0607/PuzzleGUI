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

public class MyTableTransferHandlerLineTable extends TransferHandler {
	private final DataFlavor localObjectFlavor;

	private JTable table;
	private int dragRow;
	private int dragCol;
	private int dropRow;
	private int dropCol;

	public MyTableTransferHandlerLineTable(JTable table) {
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
		dragRow = row;
		dragCol = col;
		myImageIcon value = (myImageIcon) table.getModel().getValueAt(row, col);
		System.out.println("createTransferable" + value.getRow() + "," + value.getColumn());
		final DataHandler dh = new DataHandler(value, localObjectFlavor.getMimeType());

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
		// JTable.DropLocation dl = (JTable.DropLocation)
		// info.getDropLocation();
		// int row = dl.getRow();
		// int col = dl.getColumn();
		// dropCol = col;
		// dropRow = row;
		// Driver d = drivers.getDriver(row);
		// int occupation = d.getPrePlanAtPosition(col - 1);
		// if (occupation == 1) {
		// return false;
		// }
		// if (!info.isDataFlavorSupported(localObjectFlavor)) {
		// return false;
		// }
		// info.setShowDropLocation(true);
		return true;
	}

	// @Override
	// public boolean importData(TransferSupport support) {
	// JTable.DropLocation dl = (JTable.DropLocation) support.getDropLocation();
	// int row = dl.getRow();
	// int col = dl.getColumn();
	// System.err.println(row + "" + col + " " + dragCol);
	// if (!support.isDrop()) {
	// System.err.println("not support");
	// return false;
	// }
	//
	// if (!canImport(support)) {
	// System.err.println("not support");
	// return false;
	// }
	//
	// System.err.println("receive import" + row + col);
	// try {
	// myImageIcon src = (myImageIcon)
	// support.getTransferable().getTransferData(localObjectFlavor);
	// System.err.println("get resources");
	// JLabel label = (JLabel) table.getValueAt(row, col);
	// System.err.println("get target");
	// Color color = label.getBackground();
	// System.err.println(color.toString());
	// label = new JLabel(src);
	// label.setOpaque(true);
	// label.setBackground(color);
	// table.setValueAt(label, row, col);
	// updateDriver(row, col, 1);
	//
	// Line line = lines.getLine(dragRow);
	// line.setLineShiftAtPosition(dragCol, dropRow + 1);
	// System.err.println(line.toString());
	// System.err.println("inserted");
	// } catch (UnsupportedFlavorException e) {
	// return false;
	// } catch (IOException e) {
	// return false;
	// }
	//
	// // tableModel.setValueAt(data, row, col);
	//
	// return true;
	// }

	@Override
	protected void exportDone(JComponent c, Transferable data, int action) {
		System.out.println("exportDone");
		// table.getModel().getValueAt(rowIndex, columnIndex)
		if (action == TransferHandler.MOVE) {
			System.err.println("moved" + dragRow + dragCol);

			table.getModel().setValueAt(new ImageIcon(), dragRow, dragCol);
		}
	}

}
