package Components;

import java.awt.Color;

public class MyCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	
    	java.awt.Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    	  if (isSelected)
    		  cellComponent.setBackground(Color.YELLOW);
        cellComponent.setBackground(java.awt.Color.GRAY);
        return cellComponent;
    }
}