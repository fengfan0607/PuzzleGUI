package View;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class toolBarView extends JPanel {

	JMenuBar menubar;
	JMenuItem PrePlan;
	JMenuItem PostPlan;
	JMenu options;

	public toolBarView() {
		// TODO Auto-generated constructor stub
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(new LineBorder(Color.BLACK));
		menubar = new JMenuBar();
		options = new JMenu("Options");
		PrePlan = new JMenuItem("Set preference");
		PostPlan = new JMenuItem("Start Manul plan");
		options.add(PrePlan);
		options.add(PostPlan);
		menubar.add(options);
		add(menubar);
	}

	public JMenuItem getPostPlan() {
		return PostPlan;
	}

	public void setPostPlan(JMenuItem postPlan) {
		PostPlan = postPlan;
	}

	public JMenuBar getMenubar() {
		return menubar;
	}

	public void setMenubar(JMenuBar menubar) {
		this.menubar = menubar;
	}

	public JMenuItem getPrePlan() {
		return PrePlan;
	}

	public void setPrePlan(JMenuItem prePlan) {
		PrePlan = prePlan;
	}

	public JMenu getOptions() {
		return options;
	}

	public void setOptions(JMenu options) {
		this.options = options;
	}
}
