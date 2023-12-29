package background;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Background {
	GamePanel gp;
	public BufferedImage background;

	public Background(GamePanel gp) {
		this.gp = gp;
		getBackground();
	}

	public void getBackground() {
		try {
			background = ImageIO.read(getClass().getResourceAsStream("/image/title/wallpaperflare.com_wallpaper.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2) {
		g2.drawImage(background, 0, 0, gp.screenWidth, gp.screenHeight, null);
	}
}