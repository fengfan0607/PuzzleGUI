package test;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Renderer extends DefaultTableCellRenderer{
 
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
         boolean hasFocus, int row, int column)
     {
 
        if(value instanceof JLabel){
            JLabel label = (JLabel)value;
            //to make label foreground n background visible you need to
            // setOpaque -> true
            label.setOpaque(true);
//            fillColor(table,label,isSelected);
            return label;
 
        }
 
        else
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
     }
 
}