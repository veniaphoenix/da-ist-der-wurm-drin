package entity;

import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;

public class DiceRoller extends Entity {

	GamePanel gp;
	KeyHandler keyH;

	public int face;
	// public Image diceImage;
	public boolean isRolling;
	public boolean rolled;
	public int animationCounter;
	public static final int ROLL_SPEED = 2;
	public static final int TOTAL_FRAMES = 180;
	long StartTime, EndTime;
	public boolean rollingFinished = true;

	public DiceRoller(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		face = 1;
		loadImage();
		isRolling = false;
		animationCounter = 0;
		rolled = false;
	}

	private void loadImage() {
		try {
			diceImage = ImageIO.read(getClass().getResourceAsStream("/image/dice/dice" + face + ".png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void roll() {
		rollingFinished = false;
		Thread rollThread = new Thread(new Runnable() {
			@Override
			public void run() {
				long endTime = System.currentTimeMillis();
				try {
					while ((endTime - StartTime) / 1000F < 3) {
						// roll dice
						face = (int) (Math.random() * 6) + 1;

						// update dice images
						loadImage();

						// sleep thread
						Thread.sleep(60);

						endTime = System.currentTimeMillis();
					}
					rolled = true;

				} catch (InterruptedException e) {
					System.out.println("Threading Error: " + e);
				}
			}
		});
		rollThread.start();
//		rolled = false;
		// rollingFinished = true;
	}

	public void update() {
		if (keyH.spacePress == true) {
			StartTime = System.currentTimeMillis();
			roll();
			rollingFinished = true;
		}
	}

	public int getFace() {
		return face;
	}


	public void draw(Graphics g, int x, int y) {
		g.drawImage(diceImage, x, y, 100, 100, null);
	}
}
