
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class RoomListPanel extends JPanel {
	private Image background;
	private Image screenImage;
	private Graphics screenGraphic;
	private Image roomListPanel_bg= new ImageIcon(Main.class.getResource("/images/roomlist-bg.png")).getImage();
	private Image bg1Img = new ImageIcon(Main.class.getResource("/images/anteroom1-bg.png")).getImage();
	private Image bg2Img = new ImageIcon(Main.class.getResource("/images/anteroom2-bg.png")).getImage();
	private Image bg1CroppedImg = createImage(new FilteredImageSource(bg1Img.getSource(), new CropImageFilter(500, 35, 390, 615)));
	private Image bg2CroppedImg = createImage(new FilteredImageSource(bg2Img.getSource(), new CropImageFilter(500, 35, 390, 615)));
	
	private RoomSetting roomSetPanel = new RoomSetting();
	
	private JScrollPane roomScrollPane = new JScrollPane();
	public static JList<String> roomList = new JList<String>();
	public static Vector<String> room = new Vector<String>();
	
	public void paint(Graphics g) { // 컴포넌트 본인만 paint
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw((Graphics2D) screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics2D g) {
		g.drawImage(background, 0, 0, null);
		g.drawImage(roomListPanel_bg, 0, 0, null);
		
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		paintComponents(g);
		try {
			Thread.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.repaint();

	}

	public RoomListPanel() {
		setSize(390, 615);
		setLayout(null);
		
		if (roomSetPanel.getBgSet() == 1) {
			background = bg1CroppedImg;
		}
		else
			background = bg2CroppedImg;
		
		roomList.setOpaque(false);
		roomList.setFixedCellHeight(50);
		roomList.setFont(new Font("산돌수필B", Font.BOLD, 25));
		roomList.setForeground(Color.WHITE);
		DefaultListCellRenderer renderer = new DefaultListCellRenderer();
		renderer.setOpaque(false);
		renderer.setForeground(Color.WHITE);
		roomList.setCellRenderer(renderer);
		roomScrollPane.setBounds(45, 95, 300, 500);
		roomScrollPane.setBorder(null);
		roomScrollPane.setOpaque(false);
		roomScrollPane.setViewportView(roomList);
		roomScrollPane.getViewport().setOpaque(false);
		add(roomScrollPane);
		setVisible(true);
	}
}