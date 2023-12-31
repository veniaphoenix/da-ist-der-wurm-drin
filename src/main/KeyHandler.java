package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entity.Player;

public class KeyHandler implements KeyListener {

	public boolean upPressed, downPressed, leftPressed, rightPressed, spacePress;
	GamePanel gp;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (gp.gameState == gp.titleState) {

			if (gp.ui.titleScreenState == 0) {

				if (code == KeyEvent.VK_UP) {
					gp.ui.commandNum--;
					if (gp.ui.commandNum < 0) {
						gp.ui.commandNum = 2;
					}
				}
				if (code == KeyEvent.VK_DOWN) {
					gp.ui.commandNum++;
					if (gp.ui.commandNum > 2) {
						gp.ui.commandNum = 0;
					}
				}
				if (code == KeyEvent.VK_ENTER) {
					if (gp.ui.commandNum == 0) {
						gp.ui.titleScreenState = 1;

					}
					if (gp.ui.commandNum == 1) {

					}
					if (gp.ui.commandNum == 2) {
						System.exit(0);
					}
				}
			} else {
				if (code == KeyEvent.VK_UP) {
					gp.ui.commandNum--;
					if (gp.ui.commandNum < 0) {
						gp.ui.commandNum = 3;
					}
				}
				if (code == KeyEvent.VK_DOWN) {
					gp.ui.commandNum++;
					if (gp.ui.commandNum > 3) {
						gp.ui.commandNum = 0;
					}
				}
				if (code == KeyEvent.VK_ENTER) {
					if (gp.ui.commandNum == 0) {
						// 2 players
						gp.gameState = gp.playState;
						Player newPlayer1 = new Player(gp, gp.keyH, 1);
						gp.addPlayer(newPlayer1);

					}
					if (gp.ui.commandNum == 1) {
						// 3 players
						gp.gameState = gp.playState;
						Player newPlayer1 = new Player(gp, gp.keyH, 1);
						gp.addPlayer(newPlayer1);
						Player newPlayer2 = new Player(gp, gp.keyH, 2);
						gp.addPlayer(newPlayer2);
					}
					if (gp.ui.commandNum == 2) {
						// 4 players
						gp.gameState = gp.playState;
						Player newPlayer1 = new Player(gp, gp.keyH, 1);
						gp.addPlayer(newPlayer1);
						Player newPlayer2 = new Player(gp, gp.keyH, 2);
						gp.addPlayer(newPlayer2);
						Player newPlayer3 = new Player(gp, gp.keyH, 3);
						gp.addPlayer(newPlayer3);
					}
					if (gp.ui.commandNum == 3) {
						// back button
						gp.ui.titleScreenState = 0;

					}
				}
			}
		} else if (gp.gameState == gp.playState) {
			if (code == KeyEvent.VK_SPACE) {
				spacePress = true;
			}
			if (code == KeyEvent.VK_I) {
				gp.gameState = gp.inventoryState;
			}
		} else if (gp.gameState == gp.inventoryState) {
			if (code == KeyEvent.VK_I) {
				gp.gameState = gp.playState;
			}
			if (code == KeyEvent.VK_LEFT) {
				gp.playSE(1);
				gp.ui.slotCol--;
				if (gp.ui.slotCol < 0) {
					gp.ui.slotCol = 1;
				}
			}
			if (code == KeyEvent.VK_RIGHT) {
				gp.playSE(1);
				gp.ui.slotCol++;
				if (gp.ui.slotCol > 1) {
					gp.ui.slotCol = 0;
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (gp.gameState == gp.playState) {
			int code = e.getKeyCode();

			if (code == KeyEvent.VK_W) {
				upPressed = false;
			}
			if (code == KeyEvent.VK_S) {
				downPressed = false;
			}
			if (code == KeyEvent.VK_A) {
				leftPressed = false;
			}
			if (code == KeyEvent.VK_D) {
				rightPressed = false;
			}
			if (code == KeyEvent.VK_SPACE) {
				spacePress = false;
			}
		}
	}
}
