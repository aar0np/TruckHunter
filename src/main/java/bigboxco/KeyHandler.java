package bigboxco;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean upPressed = false;
	private boolean downPressed = false;
	
	@Override
	public void keyPressed(KeyEvent keyPress) {
		
		int code = keyPress.getKeyCode();
		
		if (code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if (code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent keyPress) {
		
		int code = keyPress.getKeyCode();
		
		if (code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if (code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// not used
	}

	public boolean isLeftPressed() {
		return leftPressed;
	}

	public boolean isRightPressed() {
		return rightPressed;
	}
	
	public boolean isDownPressed() {
		return downPressed;
	}

	public boolean isUpPressed() {
		return upPressed;
	}
}
