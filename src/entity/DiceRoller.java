package entity;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import main.GamePanel;
import main.KeyHandler;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class DiceRoller extends Entity{

    GamePanel gp;
	KeyHandler keyH;

    public int face; 
    // public Image diceImage;
    public boolean isRolling;
    public int animationCounter;

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
        if (!isRolling) {
            isRolling = true;
            animationCounter = 0;
        }

        if (animationCounter < 30) { 
            face = (int) (Math.random() * 6) + 1;
            loadImage();
            animationCounter++;
            roll();
        } else {
            isRolling = false;
        }
    }
    public void update() {
		if (keyH.spacePress == true) {
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
