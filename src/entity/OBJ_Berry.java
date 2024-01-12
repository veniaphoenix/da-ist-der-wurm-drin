package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Berry extends Entity {
	public boolean used = false;
	public OBJ_Berry(GamePanel gp, String color) {
		super(gp);
		loadImage(color);
	}

	public void loadImage(String color) {
		try { //load daisy
			down = ImageIO.read(getClass().getResourceAsStream("/image/strawberry/" + color + "_berry.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void draw(Graphics2D g2) {

		BufferedImage image = down;
		g2.drawImage(image, x, y, 80, 80, null);
	}
}
