package Components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class BackgroundCellRenderer extends DefaultTableCellRenderer {

    private Map colours = new HashMap();
    private Color defaultColour;
    private Object va;

    public Component getTableCellRendererComponent(
        JTable table,
        Object value,
        boolean isSelected,
        boolean hasFocus,
        int row,
        int col) {
    
        
        Component c = super.getTableCellRendererComponent(
            table,
            value,
            isSelected,
            hasFocus,
            row,
            col);
        
        

        if (defaultColour == null) {
            defaultColour = c.getBackground();
        }

        Color newColour = (Color) colours.get(new CellKey(row, col));

        if (newColour != null) {
            c.setBackground(newColour);
        }
        else {
            c.setBackground(defaultColour);
        }
        
        c.setFont(new Font("Dialog", Font.PLAIN, 18));
        return c;
    }
    
    public void setBackgroundColour(int row, int col, Color color) {
        colours.put(new CellKey(row, col), color);
    }
//	public void setValue(int row,int col,int value){
//		
//	}
    private static class CellKey {
        private int row;
        private int col;
        private CellKey(int row, int col) {
            this.row = row;
            this.col = col;
        }
        public int hashCode() {
            return row + col; // rubbish hashing function!
        }
        public boolean equals(Object obj) {
            if (obj instanceof CellKey) {
                CellKey cell = (CellKey) obj;
                return row == cell.row && col == cell.col;
            }
            return false;
        }
    }
    
}