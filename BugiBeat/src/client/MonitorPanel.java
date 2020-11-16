package client;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.*;

public class MonitorPanel extends JPanel {
	private Image screenImage;
	private Graphics screenGraphic;
	
	private Image background;

	
	public void paintComponent(Graphics g) {  // 컴포넌트 본인만 paint
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw((Graphics2D) screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}
	
	public void screenDraw(Graphics2D g) {
		g.drawImage(background, 0, 0, null);
		setOpaque(false); 
		/*
		if(isMainScreen)
		{
			g.drawImage(selectedImage, 340, 100, null);
			g.drawImage(titleImage, 340, 70, null);
		}
		if(isGameScreen)
		{
			game.screenDraw(g);
		}
		paintComponents(g);
		*/
		this.repaint();
	}
	
	public MonitorPanel() {
		setSize(480, 720);
		setLayout(null);
		background = new ImageIcon(Main.class.getResource("../images/room-bg2.png")).getImage();
	}
}
