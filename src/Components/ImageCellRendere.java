package Components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

public class ImageCellRendere extends BackgroundCellRenderer{
	JLabel lbl = new JLabel();

	  ImageIcon icon = new ImageIcon("sun.png");

	  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
	      boolean hasFocus, int row, int column) {
//	    lbl.setText((String) value);
		  if(value instanceof Icon){
			  setIcon((Icon)value);
		  }
	  
	    return this;	  }
	
}
