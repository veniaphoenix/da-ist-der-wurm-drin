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

	public int playerIndex;
	public boolean win;
	public boolean changePlayer;
	public boolean added;
	public boolean isMoving;
	public boolean useStrawberry = false;
	public boolean useDaisy = false;

	public int bonus;

	public int yPlayerUsedItem_1_On;
	public int yPlayerUsedItem_2_On;
	public int indexDaisyPlayerUsed = 0;
	public int indexBerryPlayerUsed = 0;
	public int daisyCounter = 0;
	public int berryCounter = 0;

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
		x = 150;
		x_temp = x;
		y = 180 + playerIndex * 170;
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
				if (x >= gp.screenWidth - gp.screenWidth / 5) {
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
