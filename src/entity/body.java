package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.GamePanel;

public class body extends Entity {

  GamePanel gp;
  int bodyLength;
  int x_temp, firstmove;
  public int belong;
  public boolean isNew = true;
  public boolean toRemove = false;

  public body(GamePanel gp, int bodyLength, int playerIndex) {
    super(gp);
    this.gp = gp;
    this.bodyLength = bodyLength;
    speed = 2;
    x = 180;
    x_temp = x;
    this.belong = playerIndex;
    getImage();
  }

  public void update() {
    // if (gp.dice.rolled == true) {
		// 	x += speed;
			
		// 	gp.gameState = gp.pauseState;
		// }
		// if (x >= (x_temp + gp.dice.getFace() * 26 )) {
		// 	gp.gameState = gp.playState;
		// 	x_temp = x;
    //   // gp.players.get(belong).added = true;
		// 	// gp.dice.rolled = false;
		// }
    if(gp.players.get(belong).isMoving){
      x += speed;
    }

  }

public boolean isNew() {
    return isNew;
}

// Method to mark the body part for removal
public void markToRemove() {
    toRemove = true;
}

// Method to check if the body part should be removed
public boolean isToRemove() {
    return toRemove;
}

  public void getImage() {
    try {
      bodyImage = ImageIO.read(getClass().getResourceAsStream("/image/section/body" + bodyLength + ".png"));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void draw(Graphics2D g2) {

    BufferedImage image = bodyImage;
    g2.drawImage(image, x, 100 + belong * 150, bodyLength * 26, 80, null);
  }
}
