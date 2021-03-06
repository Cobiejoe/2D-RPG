package main;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	// screen settings
	final int originalTileSize = 16; // 16x16 tile (default size of characters and tiles)
	final int scale = 3; // scale pixels
	
	public final int tileSize = originalTileSize * scale; // 48x48 tiles
	
	public final int maxScreenCol = 16;										//
	public final int maxScreenRow = 12;										// Setting max
	public final int screenWidth = tileSize * maxScreenCol; // 768 pixels	// screen size
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels 	// 
	
	// world settings
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	
	// FPS
	int fps = 60;
	
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	public Player player = new Player(this, keyH);
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
		
	}

	@Override
	public void run() { // Game Loop
		
		double drawInterval = 1000000000/fps;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while (gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if (timer >= 1000000000) {
				System.out.println("FPS:" + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
		
	}
	
	public void update() { // player movement. Update position using Key Handler
		
		player.update();
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g; // Graphics2D makes it easier to draw
		
		tileM.draw(g2); // layered, make sure to draw tiles before player
		player.draw(g2);
		
		g2.dispose();
	}
}
