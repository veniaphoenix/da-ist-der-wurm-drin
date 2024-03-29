package main;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

import entity.Player;
import entity.Body;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font CHLORINR, pixelFont;
	public BufferedImage image, text, diceImage, daisyImage, strawImage, finishImage, daisyObjectImage,
			berryObjectImage, upper, tutorial, tutorial_1;
	int commandNum = 0;
	int numberOfPlayer = 2;
	float image_scale = 3 / 4;
	public int titleScreenState = 0;
	public String textInventory = "Choose item";
	public String textOption = "Which player do you\nexpect to come first?";
	int slotRow = 0;
	int slotCol = 0;
	
	public int scencePhase = 0;
	int counter = 0;
	float alpha;
	double scale;

	public UI(GamePanel gp) {
		this.gp = gp;
		alpha = 0;
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

		if (gp.gameState == gp.playState || gp.gameState == gp.pauseState) {
			drawGameScreen();
			drawItem();
			// // new coordinates for each worm
			// Color c = new Color(255, 0, 0);
			// g2.setColor(c);

			// // for testing the tilt of lower layer
			// g2.fillRect(0, 185, 1680, 5);

			// // for testing the tilt of upper layer
			// g2.fillRect((int) (gp.screenWidth / 12 * 10.55), 0, 5, 1050);
		}
		if (gp.gameState == gp.inventoryState) {
			drawGameScreen();
			drawInventory();
			drawItem();
//			 checkCrossed();
		}
		if (gp.gameState == gp.ending) {
			drawEnding();
		}
		if (gp.gameState == gp.optionState) {
			drawGameScreen();
			drawOptionWindow();
			drawInventory();
			drawItem();
		}
		if (gp.gameState == gp.optionTitleState) {
			drawTutorial();
		}
	}

	public void drawGameScreen() {
		int width = (int) (gp.screenWidth / 10 * 7.6);
		int height = (int) (gp.screenHeight / 10 * 8);
		int x = (int) (gp.screenWidth / 10 * 1.25);
		int y = (int) (gp.screenHeight / 10);
		g2.drawImage(upper, x, y, width, height, null);

	}

	public void drawInventory() {
		int frameX = gp.screenWidth/10 * 8;
		int frameY = (int) (gp.gridSize * 0.8);
		int frameWidth = gp.gridSize * 3;
		int frameHeight = gp.gridSize * 2;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight, textInventory);

		// SLOT
		final int slotXStart = frameX + gp.gridSize / 2;
		final int slotYStart = frameY + (int) (gp.gridSize * 0.7);
		int slotX = slotXStart;
		int slotY = slotYStart;

		// CURSOR
		int cursorX = slotXStart + (gp.gridSize * slotCol);
		int cursorY = slotYStart + (gp.gridSize * slotRow);
		int cursorWidth = gp.gridSize;
		int cursorheight = gp.gridSize;

		for (int j = 0; j < gp.players.get(gp.currentPlayerIndex).inventoryList.size(); j++) {
			if (gp.players.get(gp.currentPlayerIndex).inventoryList.get(j).used == false) {
				g2.drawImage(gp.players.get(gp.currentPlayerIndex).inventoryList.get(j).down, slotX, slotY, gp.gridSize,
						gp.gridSize, null);
			}
			slotX += gp.gridSize;
		}

		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorheight, 10, 10);
	}

	public void drawTutorial() {
		if(gp.tutorial_page == 0)
		{
			g2.drawImage(tutorial, 0, 0, gp.screenWidth, gp.screenHeight, gp);
		}
		else{
			g2.drawImage(tutorial_1, 0, 0, gp.screenWidth, gp.screenHeight, gp);
		}
	}

	// INVENTORY WINDOW
	public void drawSubWindow(int x, int y, int width, int height, String text) {
		Color c = new Color(0, 0, 0, 210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
		g2.setFont(pixelFont);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
		y += gp.gridSize * 0.5;
		for (String line : text.split("\n")) {
			x += getXForCenteredTextSubWindow(width, line);
			g2.drawString(line, x, y);
			x -= getXForCenteredTextSubWindow(width, line);
			y += 32;
		}

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
			y = (int) (gp.gridSize * 9);
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

			text = "TUTORIAL";
			x = getXForCenteredText(text);
			y += gp.gridSize * 1.5;
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
			y += gp.gridSize * 1.5;
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
		} else { // choose number of players
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

			// choose player
			text = "2 Players";
			x = getXForCenteredText(text);
			y = (int) (gp.gridSize * 8);
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
			y += gp.gridSize * 1.5;
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
			y += gp.gridSize * 1.5;
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

			text = "Back";
			x = getXForCenteredText(text);
			y += gp.gridSize * 1.5;
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

	public void drawEnding() { // can insert additional step before this step
		// if(scencePhase == -1) {

		// }
		if (scencePhase == 0) {
			// can insert winning music here
			gp.playSE(2);
			gp.background.draw(g2, gp.playState);
			gp.dice.draw(g2, gp.screenWidth/10 * 8, gp.screenHeight/10);
			for (Player player : gp.players) {
				player.draw(g2);
			}
			for (Body bodypart : gp.bodyparts) {
				bodypart.draw(g2);

			}
			if (counterReached(300)) {
				scencePhase++;
			}
		}

		if (scencePhase == 1) {
			alpha += 0.005f;
			if (alpha > 1)
				alpha = 1;
			drawBlackBackground(alpha);
			if (alpha == 1) {
				alpha = 0;
				scencePhase++;
			}
		}
		if (scencePhase == 2) {
			drawBlackBackground(1f);
			alpha += 0.005f;
			if (alpha > 1)
				alpha = 1;

			String text = "Player " + gp.getWinner() + " win\n" + "Player " + gp.getWinner()
					+ " prove that he has the biggest worm among other players\n";

			drawString(alpha, 50f, 200, text, 70);

			if (counterReached(600)) {
				scencePhase++;
			}
		}

		if (scencePhase == 3) {
			drawBlackBackground(1f);
			drawString(1f, 150f, gp.screenHeight / 2, "Da ist\nDer Wurm Drin", 130);
			if (counterReached(480)) {
				scencePhase++;
			}
		}

		if (scencePhase == 4) {
			// drawBlackBackground(1f);
			// String text = "Credits\n"; // add credits here

			// drawString(1f, 150f, gp.screenHeight / 3, text, 130);
			// if (counterReached(480)) {
			// scencePhase++;
			// }
			scencePhase++;
		}

		if (scencePhase == 5) {
			drawBlackBackground(1f);
			String text = "Special thank to\nRyiSnow\nfor amazing tutorial";

			drawString(1f, 150f, gp.screenHeight / 3, text, 120);
			if (counterReached(480)) {
				scencePhase++;
			}
		}

		if (scencePhase == 6) {
			drawBlackBackground(1f);
			String text = "Thank you\nDr. Logofatu\nfor a fun Java course";

			drawString(1f, 150f, gp.screenHeight / 3, text, 120);
			if (counterReached(480)) {
				scencePhase++;
			}
		}

		if (scencePhase == 7) {
			scencePhase = 0;
			gp.retry();
			gp.gameState = gp.titleState;
		}
	}

	public boolean counterReached(int target) {
		boolean counterReached = false;
		counter++;
		if (counter > target) {
			counterReached = true;
			counter = 0;
		}
		return counterReached;
	}

	public void drawBlackBackground(float alpha) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}

	// OPTION TO CHOOSE DAISY OR BERRY
	public void drawOptionWindow() {
		int frameWidth = gp.gridSize * 4;
		int frameHeight = (int) (gp.gridSize * 4.3);
		int frameX = gp.screenWidth/10 * 8 - (frameWidth - gp.gridSize * 3)/2;
		int frameY = gp.gridSize * 3;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight, textOption);
		int textY = (int) (gp.gridSize * 4.6);
		Color c = new Color(0, 0, 205); // blue
		g2.setColor(c);
		g2.drawString("Blue", frameX + getXForCenteredTextSubWindow(frameWidth, "Blue"), textY);
		g2.setStroke(new BasicStroke(5));
		if (commandNum == 0) {
			g2.drawRoundRect(frameX + 10, textY - getTextHeight(), frameWidth - 20, getTextHeight() + 10, 25, 25);
		}
		textY += gp.gridSize * 0.7;
		c = new Color(220, 20, 60); // red
		g2.setColor(c);
		g2.drawString("Red", frameX + getXForCenteredTextSubWindow(frameWidth, "Red"), textY);
		g2.setStroke(new BasicStroke(5));
		if (commandNum == 1) {
			g2.drawRoundRect(frameX + 10, textY - getTextHeight(), frameWidth - 20, getTextHeight() + 10, 25, 25);

		}
		if (numberOfPlayer >= 3) {
			textY += gp.gridSize * 0.7;
			c = new Color(255, 250, 250); // white
			g2.setColor(c);
			g2.drawString("White", frameX + getXForCenteredTextSubWindow(frameWidth, "White"), textY);
			g2.setStroke(new BasicStroke(5));
			if (commandNum == 2) {
				g2.drawRoundRect(frameX + 10, textY - getTextHeight(), frameWidth - 20, getTextHeight() + 10, 25, 25);
			}
		}

		if (numberOfPlayer >= 4) {
			textY += gp.gridSize * 0.7;
			c = new Color(255, 127, 80); // orange
			g2.setColor(c);
			g2.drawString("Orange", frameX + getXForCenteredTextSubWindow(frameWidth, "Orange"), textY);
			g2.setStroke(new BasicStroke(5));
			if (commandNum == 3) {
				g2.drawRoundRect(frameX + 10, textY - getTextHeight(), frameWidth - 20, getTextHeight() + 10, 25, 25);

			}
		}
	}

	public void drawString(float alpha, float fontSize, int y, String text, int lineHeight) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(fontSize));

		for (String line : text.split("\n")) {
			int x = getXForCenteredText(line);
			g2.drawString(line, x, y);
			y += lineHeight;
		}

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}

	public void drawItem() {
		for (Player player : gp.players) {
			int pos = (int) (gp.screenWidth / 10 * 3.6);
			if (gp.daisyCrossed == false) {
				if (player.useDaisy) {
					try {
						daisyObjectImage = ImageIO
								.read(getClass().getResourceAsStream("/image/daisy/" + player.color + "_daisy.png"));
						g2.drawImage(daisyObjectImage,
								pos - (int) (player.indexDaisyPlayerUsed * gp.screenWidth / 10 * 0.2),
								player.yPlayerUsedItem_1_On, 80, 80, null);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			if (gp.berryCrossed == false) {
				if (player.useStrawberry) {
					try {
						berryObjectImage = ImageIO.read(
								getClass().getResourceAsStream("/image/strawberry/" + player.color + "_berry.png"));
						pos += (int) (gp.screenWidth / 10 * 2.4);
						g2.drawImage(berryObjectImage,
								pos - (int) (player.indexBerryPlayerUsed * gp.screenWidth / 10 * 0.2),
								player.yPlayerUsedItem_2_On, 80, 80, null);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void checkCrossed() {
		final int daisyPos = (int) (gp.screenWidth / 12 * 4.9);
		final int berryPos = (int) (gp.screenWidth / 10 * 6.5);
		if (!gp.berryCrossed) {
			for (Player player : gp.players) {
				if (player.x + 80 >= daisyPos && player.x < berryPos && gp.daisyCrossed == false) {
					for (Player bonusPlayer : gp.players) {
						if (bonusPlayer.yPlayerUsedItem_1_On == player.y) {

							bonusPlayer.bonus = 1;
						}
					}
					gp.daisyCrossed = true;
					return;
				} else if (player.x + 80 >= berryPos && gp.berryCrossed == false) {
					for (Player bonusPlayer : gp.players) {
						if (bonusPlayer.yPlayerUsedItem_2_On == player.y) {

							bonusPlayer.bonus = 2;
						}

					}
					gp.berryCrossed = true;
					return;
				}
			}
		}

		// System.out.println(daisyCrossed + " " + berryCrossed);
	}

	public int getXForCenteredText(String text) {
		int length = g2.getFontMetrics().stringWidth(text);
		int x = (gp.screenWidth - length) / 2;
		return x;
	}

	public int getXForCenteredTextSubWindow(int windowLength, String text) {
		int length = g2.getFontMetrics().stringWidth(text);
		int x = (windowLength - length) / 2;
		return x;
	}

	public int getTextHeight() {
		int length = g2.getFontMetrics().getHeight();
		return length;
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
			upper = ImageIO.read(getClass().getResourceAsStream("/image/misc/map-upper-layer.png"));
			tutorial = ImageIO.read(getClass().getResourceAsStream("/image/misc/tutorial.png"));
			tutorial_1 = ImageIO.read(getClass().getResourceAsStream("/image/misc/tutorial_page.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}