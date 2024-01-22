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
import entity.Body;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1367007213484898844L;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public final int tilesize = 48;
	public final int screenWidth = screenSize.width;
	public final int screenHeight = screenSize.height;
	public final int gridSize = 70;
	int FPS = 60;
	int winner;

	KeyHandler keyH = new KeyHandler(this);
	public List<Player> players = new ArrayList<>();
	List<Body> bodyparts = new ArrayList<>();

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
	public final int ending = 4;
	public final int optionState = 5;
	public boolean daisyCrossed = false;
	public boolean berryCrossed = false;

	Thread gameThread;

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);

		this.setVisible(true);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);

		this.winner = 0;

		// Player newPlayer = new Player(this, this.keyH, 0);
		// this.addPlayer(newPlayer);
	}

	public void setupGame() {
		// playMusic(0);
		Player newPlayer = new Player(this, this.keyH, 0);
		this.addPlayer(newPlayer);
		this.daisyCrossed = false;
		this.berryCrossed = false;
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
		if(players.isEmpty()) return;
		Player currentPlayer = players.get(currentPlayerIndex);
		dice.update();
		currentPlayer.update();
		
		// Update existing body parts and identify new ones
		Iterator<Body> iterator = bodyparts.iterator();
		while (iterator.hasNext()) {
			Body bodypart = iterator.next();
			if (bodypart.belong == currentPlayerIndex) {
				bodypart.update();
			}
		}
		
		if (ConditionToChangePlayer() && !currentPlayer.added) {
			if(currentPlayer.bonus == 0) {
				Body newPart = new Body(this, dice.getFace(), currentPlayerIndex, 180, 0);
				bodyparts.add(newPart);
				
			} else {
				Body newPart = new Body(this, dice.getFace(), currentPlayerIndex, 260, 0);
				bodyparts.add(newPart);
				Body newPart1 = new Body(this, 3, currentPlayerIndex, 180, currentPlayer.bonus);
				bodyparts.add(newPart1);
				currentPlayer.bonus = 0;
			}
			currentPlayer.added = true;
		} else if (ConditionToChangePlayer()) {
			currentPlayer.changePlayer = false;
			currentPlayer.isMoving = false;
			nextPlayer();
		}
		ui.checkCrossed();
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
		// repaint();
		// revalidate();
	}

	public void retry() {
		if (gameThread != null) {
			gameThread.interrupt();
			gameThread = null;
		}
	
		// Clear players and body parts
		players.clear();
		bodyparts.clear();
		this.daisyCrossed = false;
		this.berryCrossed = false;
		// Reset player index and add a new player
		currentPlayerIndex = 0;
		Player newPlayer = new Player(this, keyH, 0);
		addPlayer(newPlayer);
	
		// Reset dice and other game-related variables
		gameState = titleState;
	
		// Restart the game thread
		startGameThread();
	}

	public int getWinner() {
		for (Player player : players) {
			if (player.win == true) {
				winner = player.playerIndex;
				// System.out.print(winner);
				player.win = false;
				winner++;
				return winner;
			}
		}
		return winner;
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

		} else if(gameState == playState || 
				gameState == pauseState || 
				gameState == inventoryState ||
				gameState == optionState){
			background.draw(g2, playState);
			dice.draw(g2, 1565, 50);
			for (Player player : players) {
				player.draw(g2);
			}
			for (Body bodypart : bodyparts) {
				bodypart.draw(g2);

			}
			ui.draw(g2);
		} else {
			ui.draw(g2);;
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