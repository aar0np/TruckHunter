package bigboxco;

import javax.swing.JFrame;

public class TruckHunter {

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("BigBoxCo TruckHunter!");

		TruckHunterPanel panel = new TruckHunterPanel();
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);

		panel.start();
	}

}
