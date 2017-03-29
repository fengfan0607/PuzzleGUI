package View;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.xml.soap.Text;

import Model.Driver;

public class textView extends JPanel implements Observer{
	
	JTextArea textArea;
	public textView() {
		textArea = new JTextArea(10,100);
		add(textArea);		
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Driver){
			textArea.append(((Driver) o).toStringPrePlan()+"\n");
			textArea.append(((Driver) o).toStringPostPlan()+"\n");
		}
	}
}
