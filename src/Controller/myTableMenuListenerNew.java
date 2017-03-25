package Controller;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import Components.TablePopUpMenu;

public class myTableMenuListenerNew extends MouseAdapter{
	JTable table;
	TablePopUpMenu popUpMenu;
	public myTableMenuListenerNew(JTable t, TablePopUpMenu menu) {
		// TODO Auto-generated constructor stub
		this.table=t;
		popUpMenu = menu;
	}
	public void mouseReleased(MouseEvent e)
    {
        if (e.isPopupTrigger()&& e.getComponent() instanceof JTable)
        {
            JTable source = (JTable)e.getSource();
            int row = source.rowAtPoint( e.getPoint() );
            int column = source.columnAtPoint( e.getPoint() );

            if (!source.isRowSelected(row))
                source.changeSelection(row, column, false, false);         
//            popUpMenu = new TablePopUpMenu();
//    		table.setComponentPopupMenu(popUpMenu);
//    		popUpMenu.show(e.getComponent(), e.getX(), e.getY());
    		System.err.println("-------sss" +row);
        }
    }
	
}
