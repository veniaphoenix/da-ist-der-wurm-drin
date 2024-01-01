package entity;

import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;


public class DiceRoller extends Entity{

    GamePanel gp;
	KeyHandler keyH;

    public int face; 
    // public Image diceImage;
    public boolean isRolling;
    public int animationCounter;
    public static final int ROLL_SPEED = 2; 
    public static final int TOTAL_FRAMES = 180;
    long StartTime, EndTime;

    public DiceRoller(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
		this.keyH = keyH;
        face = 1; 
        loadImage(); 
        isRolling = false;
        animationCounter = 0;
    }

    private void loadImage() {
        try {
			diceImage = ImageIO.read(getClass().getResourceAsStream("/image/dice/dice" + face + ".png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void roll() {
        // if (!isRolling) {
        //     isRolling = true;
        //     animationCounter = 0;
        // }

        // if (animationCounter < 30) { 
        //     face = (int) (Math.random() * 6) + 1;
        //     loadImage();
        //     animationCounter++;
        //     roll();
        // } else {
        //     isRolling = false;
        // }

        // if (!isRolling) {
        //     isRolling = true;
        //     animationCounter = 0;
        // }

        // while (animationCounter < TOTAL_FRAMES) {
        //     if (animationCounter % ROLL_SPEED == 0) {
        //         face = (int) (Math.random() * 6) + 1;
        //         loadImage();
        //     }
        //     animationCounter++;
        // } 
        // isRolling = false;
        Thread rollThread = new Thread(new Runnable() {
            @Override
            public void run() {
                long endTime = System.currentTimeMillis();
                try{
                    while((endTime - StartTime)/1000F < 3){
                        // roll dice
                        face = (int) (Math.random() * 6) + 1;

                        // update dice images
                        loadImage();

                        // sleep thread
                        Thread.sleep(60);

                        endTime = System.currentTimeMillis();

                    }

                }catch(InterruptedException e){
                    System.out.println("Threading Error: " + e);
                }
            }
        });
        rollThread.start();
    }

    public void update() {
		if (keyH.spacePress == true) {
            StartTime = System.currentTimeMillis();
			roll();
		} 
	}

    public int getFace() {
        return face;
    }

    public boolean isRolling() {
        return isRolling;
    }

    public void draw(Graphics g, int x, int y) {
        g.drawImage(diceImage, x, y, 100, 100, null);
    }
}
