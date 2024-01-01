package main;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.swing.JPanel;

import background.Background;
import entity.Player;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1367007213484898844L;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public final int screenWidth = screenSize.width;
	public final int screenHeight = screenSize.height;
	public final int gridSize = 70;
	int FPS = 60;

	KeyHandler keyH = new KeyHandler(this);
	Player player = new Player(this, keyH);
	Sound sound = new Sound();
	Background background = new Background(this);
	UI ui = new UI(this);

	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	Thread gameThread;

	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 5;

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);

		this.setVisible(true);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void setupGame() {
		playMusic(0);
		gameState = titleState;
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		while (gameThread != null) {

			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;

			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();
				delta--;
			}
			System.out.println(screenWidth + " " + screenHeight);
		}
	}

	public void update() {
		player.update();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		if (gameState == titleState) {
			background.draw(g2);
			ui.draw(g2);
			
		} else if (gameState == playState) {
			// background.draw(g2);

			// player.draw(g2);
		}

		g2.dispose();
	}

	public void playMusic(int i) {
		sound.loadFile(i);
		sound.play();
		sound.loop();
	}

	public void stopMusic() {
		sound.stop();
	}

	public void playSE(int i) {
		sound.loadFile(i);
		sound.play();
	}
}
