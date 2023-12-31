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

	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		setDefaultValues();
		getPlayerImage();
	}

	public void setDefaultValues() {
		x = 100;
		y = 100;
		speed = 10;
		direction = "right";
	}

	public void getPlayerImage() {
		try {
			right = ImageIO.read(getClass().getResourceAsStream("/image/head/head_1.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		// if (keyH.upPressed == true) {
		// 	direction = "up";
		// 	y -= speed;
		// } else if (keyH.downPressed == true) {
		// 	direction = "down";
		// 	y += speed;
		// } else if (keyH.rightPressed == true) {
		// 	direction = "right";
		// 	x += speed;
		// } else if (keyH.leftPressed == true) {
		// 	direction = "left";
		// 	x -= speed;
		// }
	}

	public void draw(Graphics2D g2) {

		BufferedImage image = right;
		g2.drawImage(image, x, y, 80, 80, null);
	}
}
