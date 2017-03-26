package Controller;

import java.awt.Color;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import Components.BackgroundCellRenderer;

public class myTableMouseListener extends MouseAdapter{
	BackgroundCellRenderer renderer;
	private JPanel colorPanel;
	public myTableMouseListener(BackgroundCellRenderer renderer) {
		// TODO Auto-generated constructor stub
		this.renderer = renderer;
		colorPanel = new JPanel();
		
	}
	 public void mousePressed(MouseEvent event) {
         JTable table = (JTable) event.getSource();
         int row = table.rowAtPoint(event.getPoint());
         int col = table.columnAtPoint(event.getPoint());
         if ((event.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK){
//        	  Color newColour = 
//        	             JColorChooser.showDialog(new JFrame(), "Pick a Colour!", Color.WHITE);
//        	         renderer.setBackgroundColour(row, col, Color.GRAY);
         }
       
     }	
 }


