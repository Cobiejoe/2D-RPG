package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("2d Adventure");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack(); // causes this window to be sized to fit the preferred size of layouts
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.startGameThread();
	}
}
