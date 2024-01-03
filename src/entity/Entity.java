package entity;

import java.awt.image.BufferedImage;

import main.GamePanel;

public class Entity {

	public int x, y;
	public int speed;

	public BufferedImage up, down, left, right, diceImage;
	public String direction;
	GamePanel gp;
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
}
