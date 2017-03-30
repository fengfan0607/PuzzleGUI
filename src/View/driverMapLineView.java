package View;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

import common.Data;

public class driverMapLineView extends JPanel implements Data {
	private JTextField[] LineTextField;
	private JTextField[] LineTextFieldTitle;

	public driverMapLineView() {
		// TODO Auto-generated constructor stub
		setLayout(new GridLayout(3, 1));
		LineTextFieldTitle = new JTextField[numOfLines];
		LineTextField = new JTextField[numOfLines];
		for (int i = 0; i < numOfLines; i++) {
			LineTextFieldTitle[i] = new JTextField("Line" + (i + 1));
			LineTextFieldTitle[i].setEditable(false);
			LineTextField[i] = new JTextField(10);
			LineTextField[i].setEditable(true);
			add(LineTextFieldTitle[i]);
			add(LineTextField[i]);
		}
	}

	public JTextField[] getLineTextField() {
		return LineTextField;
	}

	public void setLineTextField(JTextField[] lineTextField) {
		LineTextField = lineTextField;
	}

	public JTextField[] getLineTextFieldTitle() {
		return LineTextFieldTitle;
	}

	public void setLineTextFieldTitle(JTextField[] lineTextFieldTitle) {
		LineTextFieldTitle = lineTextFieldTitle;
	}
}
