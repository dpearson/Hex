/*
 * Copyright 2012 David Pearson. BSD License.
 */

import javax.swing.JFrame;

/**
 * Runs the application.
 *
 * @author David
 */
public class Main {
	/**
	 * Main method. Creates a window.
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		JFrame frame=new JFrame("Hex");
		frame.setSize(400, 500);
		frame.setLocation(100, 50);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new Panel());
		frame.setVisible(true);
	}

}
