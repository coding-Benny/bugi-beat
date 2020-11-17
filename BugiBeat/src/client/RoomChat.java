package client;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.*;

public class RoomChat extends JPanel {
	private Image background1;
	private Image background2;
	
	private ImageIcon roomchatImg = new ImageIcon(Main.class.getResource("../images/roomchat-bg.png"));
	private ImageIcon roomchattingImg = new ImageIcon(Main.class.getResource("../images/roomchatting-bg.png"));
	
	public void paint(Graphics g) {
		g.drawImage(background1, 0, 0, null);
		g.drawImage(background2, 0, 120, null);
		
		paintComponents(g);
	}
	
	public RoomChat() {
		setSize(550, 180);
		setLayout(null);
		background1 = roomchatImg.getImage();
		background2 = roomchattingImg.getImage();
		
	}
}
