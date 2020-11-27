
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;

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
	private JScrollPane userScrollPane = new JScrollPane();
	
	public static DefaultListModel<String> member = new DefaultListModel<String>();
	public static JList<String> userList = new JList<String>(member);
	
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
		paintComponents(g);
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
		
		userScrollPane.setBounds(35, 85, 270, 310);
		userScrollPane.setBorder(null);
		userScrollPane.setOpaque(false);
		userScrollPane.setViewportView(userList);
		userScrollPane.getViewport().setOpaque(false);
		
		userList.setOpaque(false);
		userList.setFixedCellHeight(50);
		userList.setFont(new Font("산돌수필B", Font.BOLD, 25));
		userList.setForeground(Color.WHITE);
		userList.setModel(member);
		userList.ensureIndexIsVisible(member.getSize());
		
		DefaultListCellRenderer renderer = new DefaultListCellRenderer();
		renderer.setOpaque(false);
		renderer.setForeground(Color.WHITE);
		userList.setSelectionForeground(Color.LIGHT_GRAY);
		userList.setCellRenderer(renderer);
		
		add(userScrollPane);
		setVisible(true);
	}
}