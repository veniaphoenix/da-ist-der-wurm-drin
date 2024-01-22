package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;

public class Body extends Entity {

  GamePanel gp;
  int bodyLength;
  int x_temp, firstmove;
  int normal = 0;
  int bonus_daisy = 1;
  int bonus_berry = 2;
  public int belong;

  public Body(GamePanel gp, int bodyLength, int playerIndex, int initialPos, int type) {
    super(gp);
    this.gp = gp;
    this.bodyLength = bodyLength;
    this.belong = playerIndex;
    setDefaultValues(initialPos);
    if (type == normal) {
      getImage();
    } else {
      if (type == bonus_daisy)
        getDaisyImage();
      else
        getBerryImage();
    }
  }

  public void setDefaultValues(int initialPos) {
    speed = 2;
    x = initialPos;
    x_temp = x;
  }

  public void update() {
    if (gp.players.get(belong).isMoving) {
      x += speed;
    }
  }

  public void getBerryImage() {
    try {
      bodyImage = ImageIO.read(getClass().getResourceAsStream("/image/strawberry/" + gp.players.get(belong).color + "_berry.png"));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void getDaisyImage() {
    try {
      bodyImage = ImageIO.read(getClass().getResourceAsStream("/image/daisy/" + gp.players.get(belong).color + "_daisy.png"));

    } catch (IOException e) {
      e.printStackTrace();
    }
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
    g2.drawImage(image, x, 100 + belong * 215, bodyLength * 26, 80, null);
  }
}
