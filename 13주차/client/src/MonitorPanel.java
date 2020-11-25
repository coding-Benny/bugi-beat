import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.*;

public class MonitorPanel extends JPanel {
	private Image screenImage;
	private Graphics screenGraphic;
	
	public static Image background;

	private ImageIcon sendItemImg = new ImageIcon(Main.class.getResource("/images/item-send.png"));
	private ImageIcon recvItemImg = new ImageIcon(Main.class.getResource("/images/item-recv.png"));
	
	private RoomSetting roomSetPanel = new RoomSetting();
	private JPanel p2Panel, p3Panel, p4Panel;
	private JLabel p2Name, p3Name, p4Name;
	private JLabel p2Rank, p3Rank, p4Rank;
	private JLabel p2Icon, p3Icon, p4Icon;
	
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
		background = roomSetPanel.setMonitorPanelBg();
		
		p2Panel = new JPanel();
		p2Panel.setBackground(new Color(0, 0, 0));
		p2Panel.setBounds(120, 40, 300, 165);
		add(p2Panel);
		
		p3Panel = new JPanel();
		p3Panel.setBackground(new Color(0, 0, 0));
		p3Panel.setBounds(120, 260, 300, 165);
		add(p3Panel);
		
		p4Panel = new JPanel();
		p4Panel.setBackground(new Color(0, 0, 0));
		p4Panel.setBounds(120, 480, 300, 165);
		add(p4Panel);
		
		p2Name = new JLabel();
		p3Name = new JLabel();
		p4Name = new JLabel();

		p2Name.setBounds(220, 210, 100, 40);
		p3Name.setBounds(220, 430, 100, 40);
		p4Name.setBounds(220, 650, 100, 40);
		p2Name.setFont(new Font("산돌수필B", Font.PLAIN, 16));
		p3Name.setFont(new Font("산돌수필B", Font.PLAIN, 16));
		p4Name.setFont(new Font("산돌수필B", Font.PLAIN, 16));
		p2Name.setForeground(Color.WHITE);
		p3Name.setForeground(Color.WHITE);
		p4Name.setForeground(Color.WHITE);
		add(p2Name);
		add(p3Name);
		add(p4Name);
		
		p2Rank = new JLabel();
		p3Rank = new JLabel();
		p4Rank = new JLabel();
		p2Rank.setBounds(170, 210, 50, 40);
		p3Rank.setBounds(170, 430, 50, 40);
		p4Rank.setBounds(170, 650, 50, 40);
		p2Name.setForeground(Color.YELLOW);
		p2Rank.setForeground(Color.YELLOW);
		p3Rank.setForeground(Color.YELLOW);
		p4Rank.setFont(new Font("산돌수필B", Font.PLAIN, 30));
		p3Rank.setFont(new Font("산돌수필B", Font.PLAIN, 30));
		p4Rank.setFont(new Font("산돌수필B", Font.PLAIN, 30));
		add(p2Rank);
		add(p3Rank);
		add(p4Rank);
		
		p2Icon = new JLabel();
		p3Icon = new JLabel();
		p4Icon = new JLabel();
		p2Icon.setBounds(20, 85, 100, 80);
		p3Icon.setBounds(20, 305, 100, 80);
		p4Icon.setBounds(20, 525, 100, 80);
		add(p2Icon);
		add(p3Icon);
		add(p4Icon);
		
		setFocusable(false);
	}
}
