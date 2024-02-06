package background;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Background {
	GamePanel gp;

	public BufferedImage image[] = new BufferedImage[10];

	public Background(GamePanel gp) {
		this.gp = gp;
		getBackground();
	}

	public void getBackground() {
		try {
			image[0] = ImageIO.read(getClass().getResourceAsStream("/image/title/wallpaperflare.com_wallpaper.png"));
			// image[1] = ImageIO.read(getClass().getResourceAsStream("/image/misc/map-upper-layer.png"));
			image[1] = ImageIO.read(getClass().getResourceAsStream("/image/misc/map-lower-layer1.png"));
			image[2] = ImageIO.read(getClass().getResourceAsStream("/image/misc/map-upper-layer.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2, int i) {
		g2.drawImage(image[i], 0, 0, gp.screenWidth, gp.screenHeight, null);

	}
}