
import java.awt.*;
import javax.swing.*;

public class MonitorPanel extends JPanel {
	private Image screenImage;
	private Graphics screenGraphic;

	public static Image background;

	private ImageIcon sendItemImg = new ImageIcon(Main.class.getResource("/images/item-send.png"));
	private ImageIcon recvItemImg = new ImageIcon(Main.class.getResource("/images/item-recv.png"));
	private Image sendItem = new ImageIcon(Main.class.getResource("/images/item-send.png")).getImage();
	private Image recvItem = new ImageIcon(Main.class.getResource("/images/item-recv.png")).getImage();
	private Image ItemIcon = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();;
	
	private RoomSetting roomSetPanel = new RoomSetting();
	private JPanel p2Panel, p3Panel, p4Panel;
	private JLabel p2Label, p3Label, p4Label;
	private JLabel p2Status, p3Status, p4Status;
	private JLabel p2Name, p3Name, p4Name;
	private JLabel p2Score, p3Score, p4Score;
	private JLabel p2Rank, p3Rank, p4Rank;
	private JLabel p2Icon, p3Icon, p4Icon;

	public void paint(Graphics g) { // 컴포넌트 본인만 paint
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw((Graphics2D) screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics2D g) {
		g.drawImage(background, 0, 0, null);
		setOpaque(false);
		if (Game.isSendItem == 0) {
			g.drawImage(ItemIcon, 0, 0, null);
		} else if (Game.isSendItem == 2) {
			g.drawImage(sendItem, 20, 85, null);
		} else if (Game.isSendItem == 3) {
			g.drawImage(sendItem, 20, 305, null);
		} else if (Game.isSendItem == 4) {
			g.drawImage(sendItem, 20, 525, null);
		}

		if (Game.isRecvItem == 0) {
			g.drawImage(ItemIcon, 0, 0, null);
		} else if (Game.isRecvItem == 2) {
			g.drawImage(recvItem, 20, 85, null);
		} else if (Game.isRecvItem == 3) {
			g.drawImage(recvItem, 20, 305, null);
		} else if (Game.isRecvItem == 4) {
			g.drawImage(recvItem, 20, 525, null);
		}
		g.drawImage(screenImage, 0, 0, null);
		this.repaint();
		paintComponents(g);
	}

	public MonitorPanel() {
		setSize(480, 720);
		setLayout(null);
		background = roomSetPanel.getMonitorPanelBg();
		
		p2Label = new JLabel();
		p2Status = new JLabel();
		p2Panel = new JPanel();
		p2Panel.setBackground(new Color(0, 0, 0));
		p2Panel.setBounds(120, 40, 300, 165);
		p2Status.setBounds(p2Panel.getX()*3/2, p2Panel.getY(), p2Panel.getWidth()/2, p2Panel.getHeight()/3);
		p2Status.setFont(new Font("산돌수필B", Font.BOLD, 60));
		p2Status.setForeground(Color.MAGENTA);
		p2Panel.add(p2Label);
		p2Panel.add(p2Status);
		add(p2Panel);
		
		p3Label = new JLabel();
		p3Status = new JLabel();
		p3Panel = new JPanel();
		p3Panel.setBackground(new Color(0, 0, 0));
		p3Panel.setBounds(120, 260, 300, 165);
		p3Status.setBounds(p2Panel.getX()*3/2, p2Panel.getY(), p2Panel.getWidth()/2, p2Panel.getHeight()/3);
		p3Status.setFont(new Font("산돌수필B", Font.BOLD, 60));
		p3Status.setForeground(Color.PINK);
		p3Panel.add(p3Label);
		p3Panel.add(p3Status);
		add(p3Panel);
		
		p4Label = new JLabel();
		p4Status = new JLabel();
		p4Panel = new JPanel();
		p4Panel.setBackground(new Color(0, 0, 0));
		p4Panel.setBounds(120, 480, 300, 165);
		p4Status.setBounds(p2Panel.getX()*3/2, p2Panel.getY(), p2Panel.getWidth()/2, p2Panel.getHeight()/3);
		p4Status.setFont(new Font("산돌수필B", Font.BOLD, 60));
		p4Status.setForeground(Color.CYAN);
		p4Panel.add(p4Label);
		p4Panel.add(p4Status);
		add(p4Panel);
		
		p2Name = new JLabel();
		p3Name = new JLabel();
		p4Name = new JLabel();

		p2Name.setBounds(220, 210, 100, 40);
		p3Name.setBounds(220, 430, 100, 40);
		p4Name.setBounds(220, 650, 100, 40);
		p2Name.setFont(new Font("산돌수필B", Font.PLAIN, 32));
		p3Name.setFont(new Font("산돌수필B", Font.PLAIN, 32));
		p4Name.setFont(new Font("산돌수필B", Font.PLAIN, 32));
		p2Name.setForeground(Color.WHITE);
		p3Name.setForeground(Color.WHITE);
		p4Name.setForeground(Color.WHITE);
		add(p2Name);
		add(p3Name);
		add(p4Name);

		p2Score = new JLabel();
		p3Score = new JLabel();
		p4Score = new JLabel();
		
		p2Score.setBounds(350, 210, 100, 40);
		p3Score.setBounds(350, 430, 100, 40);
		p4Score.setBounds(350, 650, 100, 40);
		p2Score.setFont(new Font("산돌수필B", Font.PLAIN, 32));
		p3Score.setFont(new Font("산돌수필B", Font.PLAIN, 32));
		p4Score.setFont(new Font("산돌수필B", Font.PLAIN, 32));
		p2Score.setForeground(Color.PINK);
		p3Score.setForeground(Color.PINK);
		p4Score.setForeground(Color.PINK);
		add(p2Score);
		add(p3Score);
		add(p4Score);
		
		p2Rank = new JLabel();
		p3Rank = new JLabel();
		p4Rank = new JLabel();
		p2Rank.setBounds(170, 210, 50, 40);
		p3Rank.setBounds(170, 430, 50, 40);
		p4Rank.setBounds(170, 650, 50, 40);
		p2Rank.setForeground(Color.YELLOW);
		p3Rank.setForeground(Color.YELLOW);
		p4Rank.setForeground(Color.YELLOW);
		p2Rank.setFont(new Font("산돌수필B", Font.PLAIN, 30));
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
	public JLabel getP2Label() {
		return p2Label;
	}
	
	public JLabel getP2Name() {
		return p2Name;
	}
	
	public JLabel getP2Status() {
		return p2Status;
	}
	
	public JLabel getP2Score() {
		return p2Score;
	}
}