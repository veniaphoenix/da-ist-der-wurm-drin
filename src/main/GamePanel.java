package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import background.Background;
import entity.DiceRoller;
import entity.Player;
import entity.body;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1367007213484898844L;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public final int tilesize = 48;
	public final int screenWidth = screenSize.width;
	public final int screenHeight = screenSize.height;
	public final int gridSize = 70;
	int FPS = 60;

	KeyHandler keyH = new KeyHandler(this);
	public List<Player> players = new ArrayList<>();
	List<body> bodyparts = new ArrayList<>();

	int currentPlayerIndex = 0;

	public DiceRoller dice = new DiceRoller(this, keyH);
	Sound sound = new Sound();
	Background background = new Background(this);
	UI ui = new UI(this);

	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int inventoryState = 3;
	Thread gameThread;

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);

		this.setVisible(true);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);

		Player newPlayer = new Player(this, this.keyH, 0);
		this.addPlayer(newPlayer);
	}

	public void setupGame() {
		// playMusic(0);
		gameState = titleState;
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		double drawInterval = 1000000000 / FPS;
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
		}
	}

	public void addPlayer(Player player) {
		players.add(player);
	}

	public void nextPlayer() {
		currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
	}

	public void update() {

		Player currentPlayer = players.get(currentPlayerIndex);
		dice.update();
		currentPlayer.update();
		// List<body> newBodyParts = new ArrayList<>();

		// Update existing body parts and identify new ones
		Iterator<body> iterator = bodyparts.iterator();
		while (iterator.hasNext()) {
			body bodypart = iterator.next();
			if (bodypart.belong == currentPlayerIndex) {
				bodypart.update();
			}
		}

		if (ConditionToChangePlayer() && !currentPlayer.added) {
			body newPart = new body(this, dice.getFace(), currentPlayerIndex);
			bodyparts.add(newPart);
			currentPlayer.added = true;
		} else if (ConditionToChangePlayer()) {
			currentPlayer.changePlayer = false;
			currentPlayer.isMoving = false;
			nextPlayer();
		}

		// for (body bodypart : bodyparts) {
		// if(bodypart.belong == currentPlayerIndex){
		// bodypart.update();
		// }
		// }

		// if (ConditionToChangePlayer()) {
		// if(!currentPlayer.added) {
		// body newPart = new body(this, dice.getFace(), currentPlayerIndex);
		// bodyparts.add(newPart);
		// }
		// else{
		// currentPlayer.changePlayer = false;
		// nextPlayer();
		// }
		// }
	}

	public boolean ConditionToChangePlayer() {
		Player currentPlayer = players.get(currentPlayerIndex);
		if (currentPlayer.changePlayer == true) {
			return true;
		}
		return false;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		if (gameState == titleState) {
			background.draw(g2, titleState);
			ui.draw(g2);

		} else {
			background.draw(g2, playState);
			dice.draw(g2, 100, 750);
			for (Player player : players) {
				player.draw(g2);
			}
			for (body bodypart : bodyparts) {
				bodypart.draw(g2);

			}
			ui.draw(g2);
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