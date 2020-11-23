
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.*;

public class RoomChat extends JPanel {
	
	private Image roomchatImg = new ImageIcon(Main.class.getResource("/images/roomchat-bg.png")).getImage();
	private Image roomchattingImg = new ImageIcon(Main.class.getResource("/images/roomchatting-bg.png")).getImage();
	
	public void paint(Graphics g) {//그리는 함수
		g.drawImage(roomchatImg, 0, 0, null);
		g.drawImage(roomchattingImg, 0, 120, null);
		
		paintComponents(g);
	}
	
	public RoomChat() {
		setSize(550, 180);
		setLayout(null);		
	}
}
