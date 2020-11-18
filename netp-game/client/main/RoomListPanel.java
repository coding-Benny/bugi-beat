package main;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class RoomListPanel extends JPanel {
	private Image roomListPanel_bg= new ImageIcon(Main.class.getResource("../images/roomlist-bg.png")).getImage();
	public void paint(Graphics g) {
		g.drawImage(roomListPanel_bg, 0, 0, null);
		
		this.repaint();
		paintComponents(g);
	}
	public RoomListPanel() {
		setSize(390, 615);
		setLayout(null);
	}
}