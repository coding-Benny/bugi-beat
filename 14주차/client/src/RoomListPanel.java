
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.*;

public class RoomListPanel extends JPanel {
	private Image background;
	private Image screenImage;
	private Graphics screenGraphic;
	private Image roomListPanel_bg = new ImageIcon(Main.class.getResource("/images/roomlist-bg.png")).getImage();

	private RoomSetting roomSetPanel = new RoomSetting();
	private JScrollPane roomScrollPane = new JScrollPane();

	public static JList<String> roomList = new JList<String>();
	public static DefaultListModel<String> room = new DefaultListModel<String>();

	public void paintComponent(Graphics g) {
		g.drawImage(roomListPanel_bg, 0, 0, null);
		super.paintComponent(g);
	}

	public RoomListPanel() {
		setSize(390, 615);
		setLayout(null);

		setBackground(new Color(0, 0, 0, 0));
		setOpaque(false);
		
		roomScrollPane.setBounds(45, 95, 300, 500);
		roomScrollPane.setBackground(new Color(0, 0, 0, 0));
		roomScrollPane.setBorder(null);
		roomScrollPane.setOpaque(false);
		roomScrollPane.setViewportView(roomList);
		roomScrollPane.getViewport().setOpaque(false);

		roomList.setBackground(new Color(0, 0, 0, 0));
		roomList.setSelectionBackground(new Color(0, 0, 0, 0));
		roomList.setOpaque(false);
		roomList.setFixedCellHeight(50);
		roomList.setFont(new Font("산돌수필B", Font.BOLD, 25));
		roomList.setForeground(Color.WHITE);
		roomList.setModel(room);
		roomList.ensureIndexIsVisible(room.getSize());
		roomList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JList list = (JList) e.getSource();
				if (e.getClickCount() == 2) {
					System.out.println(list.getSelectedIndex());
				}
			}
		});

		DefaultListCellRenderer renderer = new DefaultListCellRenderer();
		renderer.setBackground(new Color(0, 0, 0, 0));
		renderer.setForeground(Color.WHITE);
		roomList.setSelectionForeground(Color.GRAY);
		roomList.setCellRenderer(renderer);

		add(roomScrollPane);
		setVisible(true);
	}
}