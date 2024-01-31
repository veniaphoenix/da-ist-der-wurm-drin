package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

	KeyHandler keyH;
	public int x_temp;
	int firstmove;

	public int playerIndex;	// player 1,2,3,4
	public boolean win;	// decide if this player win or not
	public boolean changePlayer;	// decide if the player turn has end and move to next player
	public boolean added;	// to check whether the new body section has been added or not
	public boolean isMoving;	// to check if the player is still in moving phase
	public boolean useStrawberry = false;	// to check if player use the item to bet
	public boolean useDaisy = false;

	public int bonus;	// to check if player win the bet

	public int yPlayerUsedItem_1_On;	// to check if player bet on who reach daisy 1st
	public int yPlayerUsedItem_2_On;	// to check if player bet on who reach berry 1st
	public int indexDaisyPlayerUsed = 0;	// to check which player this player bet on
	public int indexBerryPlayerUsed = 0;	// to check which player this player bet on
	public int daisyCounter = 0;	// number of people bet on this player
	public int berryCounter = 0;	// number of people bet on this player

	Graphics2D g;

	public ArrayList<Entity> inventoryList = new ArrayList<>();
	public String color = "white";
	OBJ_Daisy daisy;
	OBJ_Berry berry;

	public Player(GamePanel gp, KeyHandler keyH, int playerIndex) {
		super(gp);
		this.keyH = keyH;
		this.playerIndex = playerIndex;
		this.firstmove = 80;
		setDefaultValues();
		getPlayerImage();
		setInventory();
	}

	public void setDefaultValues() {
		win = false;
		isMoving = false;
		changePlayer = false;
		added = true;
		x = gp.screenWidth/13;
		x_temp = x;
		y = (int)(gp.screenHeight/4.5) + playerIndex * (int)(gp.screenHeight/6.4);
		speed = 2;
		bonus = 0;
		direction = "right";
		if (playerIndex == 0) {
			color = "blue";
		} else if (playerIndex == 1) {
			color = "red";
		} else if (playerIndex == 2) {
			color = "white";
		} else if (playerIndex == 3) {
			color = "orange";
		}
		daisy = new OBJ_Daisy(gp, color);
		berry = new OBJ_Berry(gp, color);
	}

	public void getPlayerImage() {
		try {
			right = ImageIO.read(getClass().getResourceAsStream("/image/head/head_" + playerIndex + ".png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setInventory() {
		// each player have 2 item to bet
		inventoryList.add(daisy);
		inventoryList.add(berry);
	}

	public void update() {
		if (gp.dice.rollingFinished) {
			int BonusDis = 0;
			if(bonus > 0) BonusDis = 80;
			if (gp.dice.rolled == true) {
				x += speed;
				isMoving = true;
				gp.gameState = gp.pauseState;

			}
			if (x >= (x_temp + gp.dice.getFace() * 26 + firstmove + BonusDis)) {
				added = false;
				firstmove = 0;
				changePlayer = true;
				gp.gameState = gp.playState;
				x_temp = x;
				gp.dice.rolled = false;
				if (x >= (int) (gp.screenWidth / 12 * 10.55) - 80) {
					win = true;
					gp.gameState = gp.ending;
					// return;
				}
			}
		}
	}

	public void draw(Graphics2D g2) {

		BufferedImage image = right;
		g2.drawImage(image, x, y, 80, 80, null);
	}
}
