package Components;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import common.Data;

public class TablePopUpMenu extends JPopupMenu implements Data{
	private JMenuItem menuItemPreferOff;
	private JMenuItem menuItemDayOff;
	private JMenuItem menuItemPreferWork;

	
	public TablePopUpMenu() {
		// TODO Auto-generated constructor stub
		menuItemPreferOff = new JMenuItem(menuItemPreferOffName);
		menuItemPreferOff.setName(menuItemPreferOffName);
		menuItemDayOff = new JMenuItem(menuItemDayOffName);
		menuItemDayOff.setName(menuItemDayOffName);
		menuItemPreferWork = new JMenuItem(menuItemPreferWorkName);
		menuItemPreferWork.setName(menuItemPreferWorkName);
		
		this.add(menuItemDayOff);
		this.add(menuItemPreferOff);
		this.add(menuItemPreferWork);		
		}


	public JMenuItem getMenuItemPreferOff() {
		return menuItemPreferOff;
	}


	public void setMenuItemPreferOff(JMenuItem menuItemPreferOff) {
		this.menuItemPreferOff = menuItemPreferOff;
	}


	public JMenuItem getMenuItemDayOff() {
		return menuItemDayOff;
	}


	public void setMenuItemDayOff(JMenuItem menuItemDayOff) {
		this.menuItemDayOff = menuItemDayOff;
	}


	public JMenuItem getMenuItemPreferWork() {
		return menuItemPreferWork;
	}


	public void setMenuItemPreferWork(JMenuItem menuItemPreferWork) {
		this.menuItemPreferWork = menuItemPreferWork;
	}

}
