
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.*;

public class MonitorPanel extends JPanel {
	private Image screenImage;
	private Graphics screenGraphic;
	
	private Image background;

	private ImageIcon bg1Img = new ImageIcon(Main.class.getResource("/images/room-bg2.png"));
	private ImageIcon bg2Img = new ImageIcon(Main.class.getResource("/images/room2-bg2.png"));
	
	private RoomSetting roomSetPanel = new RoomSetting();
	
	public void paint(Graphics g) {  //컴포넌트 본인만 paint
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw((Graphics2D) screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}
	
	
	public void screenDraw(Graphics2D g) {
		g.drawImage(background, 0, 0, null);
		setOpaque(false);
		
		g.drawImage(screenImage, 0, 0, null);
		this.repaint();
		paintComponents(g);
	}
	
	public MonitorPanel() {
		setSize(480, 720);
		setLayout(null);
		if(roomSetPanel.getBgSet() == 1)
			background = bg1Img.getImage();
		else
			background = bg2Img.getImage();
	}
}
