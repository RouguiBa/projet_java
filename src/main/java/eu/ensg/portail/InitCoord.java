package eu.ensg.portail;

import javax.swing.JFrame;

public class InitCoord extends JFrame{

	public InitCoord() {
		this.init();
	}
	
	
	public void init() {
		this.setTitle("Fenêtre d'instruction");
		this.setSize(400, 400);
		this.setLocation(50,50);
		this.setVisible(true);
		this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		
		
	}

}
