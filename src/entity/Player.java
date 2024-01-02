package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

	GamePanel gp;
	KeyHandler keyH;
	int playerIndex;
	int x_temp;
	public boolean changePlayer = false;

	public Player(GamePanel gp, KeyHandler keyH, int playerIndex) {
		this.gp = gp;
		this.keyH = keyH;
		this.playerIndex = playerIndex;
		setDefaultValues();
		getPlayerImage();
	}

	public void setDefaultValues() {
		x = 100;
		x_temp = x;
		y = 100 + playerIndex*150;
		speed = 2;
		direction = "right";
	}

	public void getPlayerImage() {
		try {
			right = ImageIO.read(getClass().getResourceAsStream("/image/head/head_" + playerIndex + ".png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
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
		
		if (gp.dice.rolled == true) {
			x += speed;
			gp.gameState = gp.pauseState;
		}
		if (x >= (x_temp + 150)) { //change here for additional moving mechanism
			gp.dice.rolled = false;
			changePlayer = true;
			gp.gameState = gp.playState;
			x_temp = x;
		}
	}
	
	public void draw(Graphics2D g2) {

		BufferedImage image = right;
		g2.drawImage(image, x, y, 80, 80, null);
	}
}
