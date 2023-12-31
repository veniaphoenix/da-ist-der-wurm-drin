package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import entity.DiceRoller;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font CHLORINR, pixelFont;
	public BufferedImage image, text, diceImage;
	int commandNum = 0;
	float image_scale = 3 / 4;
	public int titleScreenState = 0;

	public UI(GamePanel gp) {
		this.gp = gp;

		try {
			InputStream is = getClass().getResourceAsStream("/font/Caveman.ttf");
			CHLORINR = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/font/pixelFont.ttf");
			pixelFont = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


		getImage();
	}

	public void draw(Graphics2D g2) {
		this.g2 = g2;

		if (gp.gameState == gp.titleState) {
			drawTitleScreen();
		}

		if (gp.gameState == gp.playState) {
			drawGameScreen();
		}
	}

	public void drawGameScreen() {
		// g2.drawImage(diceImage, getXForCenteredText(""), 750, 96, 96, null);
	}

	public void drawTitleScreen() {
		if (titleScreenState == 0) {
			g2.setFont(CHLORINR);
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));

			int image_width = (int) (image.getWidth() * 0.7);
			int image_height = (int) (image.getHeight() * 0.7);
			g2.drawImage(image, (gp.screenWidth - image_width) / 2, (int) (gp.gridSize * 3.5), image_width,
					image_height, null);
			g2.drawImage(text, (gp.screenWidth - text.getWidth() * 5 / 6) / 2, (int) (gp.gridSize * 0.5),
					text.getWidth() * 5 / 6, text.getHeight() * 5 / 6, null);
			String text = "DA IST";
			int x = getXForCenteredText(text);
			int y = (int) (gp.gridSize * 6.5);

			g2.setFont(pixelFont);
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50F));

			// new game
			text = "NEW GAME";
			x = getXForCenteredText(text);
			y = (int) (gp.gridSize * 9.3);
			drawBorder(text, x, y);
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			if (commandNum == 0) {
				recolor(text, x, y);
				int length = g2.getFontMetrics().stringWidth(text);
				int length_arrow = g2.getFontMetrics().stringWidth("◄");
				drawBorder("►", x - gp.gridSize / 2 - length_arrow, y);
				g2.setColor(Color.yellow);
				g2.drawString("►", x - gp.gridSize / 2 - length_arrow, y);
				drawBorder("◄", x + length + gp.gridSize / 2, y);
				g2.setColor(Color.yellow);
				g2.drawString("◄", x + length + gp.gridSize / 2, y);
			}

			text = "OPTION";
			x = getXForCenteredText(text);
			y += gp.gridSize * 2;
			drawBorder(text, x, y);
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			if (commandNum == 1) {
				recolor(text, x, y);
				int length = g2.getFontMetrics().stringWidth(text);
				int length_arrow = g2.getFontMetrics().stringWidth("◄");
				drawBorder("►", x - gp.gridSize / 2 - length_arrow, y);
				g2.setColor(Color.yellow);
				g2.drawString("►", x - gp.gridSize / 2 - length_arrow, y);
				drawBorder("◄", x + length + gp.gridSize / 2, y);
				g2.setColor(Color.yellow);
				g2.drawString("◄", x + length + gp.gridSize / 2, y);
			}

			text = "EXIT GAME";
			x = getXForCenteredText(text);
			y += gp.gridSize * 2;
			drawBorder(text, x, y);
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			if (commandNum == 2) {
				recolor(text, x, y);
				int length = g2.getFontMetrics().stringWidth(text);
				int length_arrow = g2.getFontMetrics().stringWidth("◄");
				drawBorder("►", x - gp.gridSize / 2 - length_arrow, y);
				g2.setColor(Color.yellow);
				g2.drawString("►", x - gp.gridSize / 2 - length_arrow, y);
				drawBorder("◄", x + length + gp.gridSize / 2, y);
				g2.setColor(Color.yellow);
				g2.drawString("◄", x + length + gp.gridSize / 2, y);
			}
		} else { 			//choose number of players
			g2.setFont(CHLORINR);
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));

			int image_width = (int) (image.getWidth() * 0.7);
			int image_height = (int) (image.getHeight() * 0.7);
			g2.drawImage(image, (gp.screenWidth - image_width) / 2, (int) (gp.gridSize * 3.5), image_width,
					image_height,
					null);
			g2.drawImage(text, (gp.screenWidth - text.getWidth() * 5 / 6) / 2, (int) (gp.gridSize * 0.5),
					text.getWidth() * 5 / 6, text.getHeight() * 5 / 6, null);
			String text = "DA IST";
			int x = getXForCenteredText(text);
			int y = (int) (gp.gridSize * 6.5);

			g2.setFont(pixelFont);
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50F));

			// choose player
			text = "2 Players";
			x = getXForCenteredText(text);
			y = (int) (gp.gridSize * 6);
			drawBorder(text, x, y);
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			if (commandNum == 0) {
				recolor(text, x, y);
				int length = g2.getFontMetrics().stringWidth(text);
				int length_arrow = g2.getFontMetrics().stringWidth("◄");
				drawBorder("►", x - gp.gridSize / 2 - length_arrow, y);
				g2.setColor(Color.yellow);
				g2.drawString("►", x - gp.gridSize / 2 - length_arrow, y);
				drawBorder("◄", x + length + gp.gridSize / 2, y);
				g2.setColor(Color.yellow);
				g2.drawString("◄", x + length + gp.gridSize / 2, y);
			}

			text = "3 Players";
			x = getXForCenteredText(text);
			y += gp.gridSize * 2;
			drawBorder(text, x, y);
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			if (commandNum == 1) {
				recolor(text, x, y);
				int length = g2.getFontMetrics().stringWidth(text);
				int length_arrow = g2.getFontMetrics().stringWidth("◄");
				drawBorder("►", x - gp.gridSize / 2 - length_arrow, y);
				g2.setColor(Color.yellow);
				g2.drawString("►", x - gp.gridSize / 2 - length_arrow, y);
				drawBorder("◄", x + length + gp.gridSize / 2, y);
				g2.setColor(Color.yellow);
				g2.drawString("◄", x + length + gp.gridSize / 2, y);
			}

			text = "4 Players";
			x = getXForCenteredText(text);
			y += gp.gridSize * 2;
			drawBorder(text, x, y);
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			if (commandNum == 2) {
				recolor(text, x, y);
				int length = g2.getFontMetrics().stringWidth(text);
				int length_arrow = g2.getFontMetrics().stringWidth("◄");
				drawBorder("►", x - gp.gridSize / 2 - length_arrow, y);
				g2.setColor(Color.yellow);
				g2.drawString("►", x - gp.gridSize / 2 - length_arrow, y);
				drawBorder("◄", x + length + gp.gridSize / 2, y);
				g2.setColor(Color.yellow);
				g2.drawString("◄", x + length + gp.gridSize / 2, y);
			}
			
			//back
			text = "Back";
			x = getXForCenteredText(text);
			y += gp.gridSize * 2;
			drawBorder(text, x, y);
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			if (commandNum == 3) {
				recolor(text, x, y);
				int length = g2.getFontMetrics().stringWidth(text);
				int length_arrow = g2.getFontMetrics().stringWidth("◄");
				drawBorder("►", x - gp.gridSize / 2 - length_arrow, y);
				g2.setColor(Color.yellow);
				g2.drawString("►", x - gp.gridSize / 2 - length_arrow, y);
				drawBorder("◄", x + length + gp.gridSize / 2, y);
				g2.setColor(Color.yellow);
				g2.drawString("◄", x + length + gp.gridSize / 2, y);
			}
		}
	}

	public int getXForCenteredText(String text) {
		int length = g2.getFontMetrics().stringWidth(text);
		int x = (gp.screenWidth - length) / 2;
		return x;
	}

	public void drawBorder(String text, int x, int y) {
		g2.setColor(Color.black);
		g2.drawString(text, x - 2, y - 2);
		g2.drawString(text, x + 2, y - 2);
		g2.drawString(text, x - 2, y + 2);
		g2.drawString(text, x + 2, y + 2);
	}

	public void recolor(String text, int x, int y) {
		g2.setColor(Color.yellow);
		g2.drawString(text, x, y);
	}

	public void getImage() {
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/image/title/logo.png"));
			text = ImageIO.read(getClass().getResourceAsStream("/image/title/text.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
