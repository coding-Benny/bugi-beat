
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class RoomListPanel extends JPanel {
	private Image roomListPanel_bg= new ImageIcon(Main.class.getResource("/images/roomlist-bg.png")).getImage();
	
	private RoomSetting roomSetPanel = new RoomSetting();
	private JScrollPane roomScrollPane = new JScrollPane();
	
	public static JList<String> roomList = new JList<String>();
	public static DefaultListModel<String> room = new DefaultListModel<String>();
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(roomListPanel_bg, 0, 0, null);
	}

	public RoomListPanel() {
		setSize(390, 615);
		setLayout(null);
		setOpaque(false);
		
		roomScrollPane.setBounds(45, 95, 300, 500);
		roomScrollPane.setBorder(null);
		roomScrollPane.setOpaque(false);
		roomScrollPane.setViewportView(roomList);
		roomScrollPane.getViewport().setOpaque(false);
		roomScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		roomList.setOpaque(false);
		roomList.setFixedCellHeight(50);
		roomList.setFont(new Font("산돌수필B", Font.BOLD, 25));
		roomList.setForeground(Color.WHITE);
		roomList.setModel(room);
		roomList.ensureIndexIsVisible(room.getSize());
		
		DefaultListCellRenderer renderer = new DefaultListCellRenderer();
		renderer.setOpaque(false);
		renderer.setForeground(Color.WHITE);
		roomList.setSelectionForeground(Color.GRAY);
		roomList.setCellRenderer(renderer);

		add(roomScrollPane);
		setVisible(true);
	}
}