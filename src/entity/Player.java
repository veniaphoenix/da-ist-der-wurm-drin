package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

	KeyHandler keyH;
	int playerIndex;
	int x_temp;
	int firstmove;
	public boolean changePlayer = false;
	public boolean added;
	public boolean isMoving = false;
	
	Graphics2D g;

	public ArrayList<Entity> inventoryList = new ArrayList<>();
	String color = "white";
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
		added = true;
		x = 100;
		x_temp = x;
		y = 100 + playerIndex*150;
		speed = 2;
		direction = "right";
		if (playerIndex == 0) {
			color = "white";
		}
		else if (playerIndex == 1) {
			color = "blue";
		}
		else if (playerIndex == 2) {
			color = "orange";
		}
		else if (playerIndex == 3) {
			color = "red";
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
//		if (keyH.upPressed == true) {
//			direction = "up";
//			y -= speed;
//		} else if (keyH.downPressed == true) {
//			direction = "down";
//			y += speed;
//		} else if (keyH.rightPressed == true) {
//			direction = "right";
//			x += speed;
//		} else if (keyH.leftPressed == true) {
//			direction = "left";
//			x -= speed;
//		}
		if(gp.dice.rollingFinished){
			if (gp.dice.rolled == true) {
			x += speed;
			isMoving = true;
			gp.gameState = gp.pauseState;
		}
		if (x >= (x_temp + gp.dice.getFace() * 26 + firstmove)) { //change here for additional moving mechanism
			added = false;
			firstmove = 0;
			changePlayer = true;
			gp.gameState = gp.playState;
			x_temp = x;
			gp.dice.rolled = false;
			// System.out.println(gp.dice.getFace());
			}
		}
		// if (gp.dice.rolled == true) {
		// 	x += speed;
		// 	isMoving = true;
		// 	gp.gameState = gp.pauseState;
		// }
		// if (x >= (x_temp + gp.dice.getFace() * 26 + firstmove)) { //change here for additional moving mechanism
		// 	added = false;
		// 	firstmove = 0;
		// 	changePlayer = true;
		// 	gp.gameState = gp.playState;
		// 	x_temp = x;
		// 	gp.dice.rolled = false;
		// }
	}
	
	public void draw(Graphics2D g2) {

		BufferedImage image = right;
		g2.drawImage(image, x, y, 80, 80, null);
	}
}
