

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {
	@Override
	public void keyPressed(KeyEvent e) {
		if (GamePanel.game == null) {	/* 게임 진행중이 아니라면 아래 작업을 거치지 않도록 무력화 */
			return;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_S) {
			GamePanel.game.pressS();
		}
		else if (e.getKeyCode() == KeyEvent.VK_D) {
			GamePanel.game.pressD();
		}
		else if (e.getKeyCode() == KeyEvent.VK_F) {
			GamePanel.game.pressF();
		}
		else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			GamePanel.game.pressSpace();
		}
		else if (e.getKeyCode() == KeyEvent.VK_J) {
			GamePanel.game.pressJ();
		}
		else if (e.getKeyCode() == KeyEvent.VK_K) {
			GamePanel.game.pressK();
		}
		else if (e.getKeyCode() == KeyEvent.VK_L) {
			GamePanel.game.pressL();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if (GamePanel.game == null) {	/* 게임 진행중이 아니라면 아래 작업을 거치지 않도록 무력화 */
			return;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_S) {
			GamePanel.game.releaseS();
		}
		else if (e.getKeyCode() == KeyEvent.VK_D) {
			GamePanel.game.releaseD();
		}
		else if (e.getKeyCode() == KeyEvent.VK_F) {
			GamePanel.game.releaseF();
		}
		else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			GamePanel.game.releaseSpace();
		}
		else if (e.getKeyCode() == KeyEvent.VK_J) {
			GamePanel.game.releaseJ();
		}
		else if (e.getKeyCode() == KeyEvent.VK_K) {
			GamePanel.game.releaseK();
		}
		else if (e.getKeyCode() == KeyEvent.VK_L) {
			GamePanel.game.releaseL();
		}
	}
}
