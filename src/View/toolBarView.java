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
	JMenu simulation;
	JMenu loadData;
	JMenuItem loadData1;
	JMenuItem loadData2;
	JMenu Reset;
	JMenuItem resetPlan;
	JMenuItem resetAll;

	public JMenu getSimulation() {
		return simulation;
	}

	public void setSimulation(JMenu simulation) {
		this.simulation = simulation;
	}

	public JMenuItem getLoadData() {
		return loadData;
	}

	public void setLoadData(JMenu loadData) {
		this.loadData = loadData;
	}

	public JMenuItem getOptimization() {
		return Optimization;
	}

	public void setOptimization(JMenuItem optimization) {
		Optimization = optimization;
	}

	JMenuItem Optimization;

	public toolBarView() {
		// TODO Auto-generated constructor stub
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(new LineBorder(Color.BLACK));
		menubar = new JMenuBar();
		// first tool menu
		options = new JMenu("Options");
		PrePlan = new JMenuItem("Set preference");
		PostPlan = new JMenuItem("Start Manul plan");
		options.add(PrePlan);
		options.add(PostPlan);

		// second tool menu
		simulation = new JMenu("Simulation");
		loadData = new JMenu("Load Data");
		loadData1 = new JMenuItem("set 1");
		loadData2 = new JMenuItem("set 2");
		loadData.add(loadData1);
		loadData.add(loadData2);
		Optimization = new JMenuItem("Optimization");
		simulation.add(loadData);
		simulation.add(Optimization);

		// thrid
		JMenu Reset = new JMenu("Reset");
		JMenuItem resetPlan = new JMenuItem("Reset Plan");
		JMenuItem resetAll = new JMenuItem("Reset All");
		Reset.add(resetPlan);
		Reset.add(resetAll);

		menubar.add(options);
		menubar.add(simulation);
		menubar.add(Reset);
		add(menubar);
	}

	public JMenu getReset() {
		return Reset;
	}

	public void setReset(JMenu reset) {
		Reset = reset;
	}

	public JMenuItem getResetPlan() {
		return resetPlan;
	}

	public void setResetPlan(JMenuItem resetPlan) {
		this.resetPlan = resetPlan;
	}

	public JMenuItem getResetAll() {
		return resetAll;
	}

	public void setResetAll(JMenuItem resetAll) {
		this.resetAll = resetAll;
	}

	public JMenuItem getLoadData1() {
		return loadData1;
	}

	public void setLoadData1(JMenuItem loadData1) {
		this.loadData1 = loadData1;
	}

	public JMenuItem getLoadData2() {
		return loadData2;
	}

	public void setLoadData2(JMenuItem loadData2) {
		this.loadData2 = loadData2;
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
