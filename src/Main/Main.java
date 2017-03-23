package Main;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import View.MainView;
import View.driverView;

public class Main {
	
	public static void main(String[] args) {
		
		  SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					MainView mView =new MainView();
					
				}
			});
		
	}

}
