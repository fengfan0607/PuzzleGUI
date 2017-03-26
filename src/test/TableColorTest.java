package test;


import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;


public class TableColorTest {

    public static void main(String[] args) {
        final BackgroundCellRenderer renderer = new BackgroundCellRenderer();
        
        final JColorChooser colorChooser = new JColorChooser();
        
        String[] columns = {"A", "B", "C"};
        String[][] data = { {"1", "2", "3"}, {"4", "5", "6"} };
        JTable table = new JTable(data, columns) {
            public TableCellRenderer getCellRenderer(int row, int col) {
                return renderer;
            }
        };

        final JFrame frame = new JFrame("JTable Cell Colour App");
        frame.getContentPane().add(new JScrollPane(table));
        frame.setSize(200, 200);
        
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                JTable table = (JTable) event.getSource();
                int row = table.rowAtPoint(event.getPoint());
                int col = table.columnAtPoint(event.getPoint());
                Color newColour = 
                    JColorChooser.showDialog(frame, "Pick a Colour!", Color.WHITE);
                renderer.setBackgroundColour(row, col, newColour);
            }
        });

        frame.setVisible(true);
    }
    
    private static class BackgroundCellRenderer extends DefaultTableCellRenderer {

        private Map colours = new HashMap();
        private Color defaultColour;

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

            return c;
        }
        
        public void setBackgroundColour(int row, int col, Color color) {
            colours.put(new CellKey(row, col), color);
        }

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
}