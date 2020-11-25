import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

public class UserListPanel extends JPanel {
	private Image screenImage;
	private Graphics screenGraphic;
	private Image background;
	private Image accessListImg = new ImageIcon(Main.class.getResource("/images/accesslist-bg.png")).getImage();
	private Image bg1Img = new ImageIcon(Main.class.getResource("/images/anteroom1-bg.png")).getImage();
	private Image bg2Img = new ImageIcon(Main.class.getResource("/images/anteroom2-bg.png")).getImage();
	private Image bg1Cropped = createImage(new FilteredImageSource(bg1Img.getSource(), new CropImageFilter(915, 230, 340, 420)));
	private Image bg2Cropped = createImage(new FilteredImageSource(bg2Img.getSource(), new CropImageFilter(915, 230, 340, 420)));
	
	private RoomSetting roomSetPanel = new RoomSetting();
	
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw((Graphics2D) screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics2D g) {
		g.drawImage(background, 0, 0, null);
		g.drawImage(accessListImg, 0, 0, null);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(new Font("산돌수필B", Font.BOLD, 20));
		g.setColor(Color.WHITE);
		for (int i=0; i<WaitingRoom.member.size(); i++) {
			g.drawString(WaitingRoom.member.elementAt(i), 50, 120 + i*30);
		}
		try {
			Thread.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.repaint();
	}
	
	public UserListPanel() {
		setSize(340, 420);
		setLayout(null);
		
		if (roomSetPanel.getBgSet() == 1) {
			background = bg1Cropped;
		}
		else
			background = bg2Cropped;
	}	
	
}