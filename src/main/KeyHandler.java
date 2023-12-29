package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	public boolean upPressed, downPressed, leftPressed, rightPressed;
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

					}
					if (gp.ui.commandNum == 1) {
						// 3 players
						gp.gameState = gp.playState;

					}
					if (gp.ui.commandNum == 2) {
						// 4 players
						gp.gameState = gp.playState;

					}
					if (gp.ui.commandNum == 3) {
						// back button
						gp.ui.titleScreenState = 0;

					}
				}
			}
		}
		if (gp.gameState == gp.playState) {

			if (code == KeyEvent.VK_W) {
				upPressed = true;
			}
			if (code == KeyEvent.VK_S) {
				downPressed = true;
			}
			if (code == KeyEvent.VK_A) {
				leftPressed = true;
			}
			if (code == KeyEvent.VK_D) {
				rightPressed = true;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
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
	}

}
