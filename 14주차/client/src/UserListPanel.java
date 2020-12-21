
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

	private RoomSetting roomSetPanel = new RoomSetting();
	private JScrollPane userScrollPane = new JScrollPane();
	
	public static DefaultListModel<String> member = new DefaultListModel<String>();
	public static JList<String> userList = new JList<String>(member);
	
	public void paintComponent(Graphics g) {
		g.drawImage(accessListImg, 0, 0, null);
		super.paintComponent(g);
	}
	
	public UserListPanel() {
		setSize(340, 420);
		setLayout(null);
		
		setBackground(new Color(0, 0, 0, 0));
		setOpaque(false);
		
		userScrollPane.setBounds(35, 95, 270, 310);
		userScrollPane.setBorder(null);
		userScrollPane.setOpaque(false);
		userScrollPane.setViewportView(userList);
		userScrollPane.getViewport().setOpaque(false);
		
		userList.setBackground(new Color(0, 0, 0, 0));
		userList.setSelectionBackground(new Color(0, 0, 0, 0));
		userList.setOpaque(false);
		userList.setFixedCellHeight(40);
		userList.setFont(new Font("산돌수필B", Font.BOLD, 25));
		userList.setForeground(Color.WHITE);
		userList.setModel(member);
		userList.ensureIndexIsVisible(member.getSize());
		
		DefaultListCellRenderer renderer = new DefaultListCellRenderer();
		renderer.setBackground(new Color(0, 0, 0, 0));
		renderer.setForeground(Color.WHITE);
		userList.setSelectionForeground(Color.LIGHT_GRAY);
		userList.setCellRenderer(renderer);
		
		add(userScrollPane);
		setVisible(true);
	}
}