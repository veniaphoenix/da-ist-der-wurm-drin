package entity;

import java.awt.image.BufferedImage;

import main.GamePanel;

public class Entity {

	public int x, y;
	public int speed;

	public BufferedImage up, down, left, right, diceImage, bodyImage;
	public String direction;
	GamePanel gp;
	public int itemIndex;
	public boolean used;
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
}
