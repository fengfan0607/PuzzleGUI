package Components;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import common.Data;

public class TablePopUpMenuUnDoPlan extends JPopupMenu implements Data {
	private JMenuItem unPlan;

	public TablePopUpMenuUnDoPlan() {
		// TODO Auto-generated constructor stub
		unPlan = new JMenuItem("UnPlan");

		this.add(unPlan);
	}

	public JMenuItem getUnPlan() {
		return unPlan;
	}

	public void setUnPlan(JMenuItem unPlan) {
		this.unPlan = unPlan;
	}

}
