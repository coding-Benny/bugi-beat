import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;

public class AnteRoom extends JFrame {

	private Image background;
	
	private ImageIcon bg1Img = new ImageIcon(Main.class.getResource("/images/anteroom1-bg.png"));
	private ImageIcon bg2Img = new ImageIcon(Main.class.getResource("/images/anteroom2-bg.png"));

	private RoomSetting roomSetPanel = new RoomSetting();
	
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null);
		
		paintComponents(g);
	}
	
	public AnteRoom() {
		super("대기실");
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setShape(new RoundRectangle2D.Double(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, 40, 40));
		setResizable(false);
		setLocationRelativeTo(null);
		
		Container c = getContentPane();
		c.setLayout(null);

		if(roomSetPanel.getBgSet() == 1)
			background = bg1Img.getImage();
		else
			background = bg2Img.getImage();
		
		JScrollPane accessListPanel = new JScrollPane();
		accessListPanel.setBounds(915, 230, 340, 420);
		c.add(accessListPanel);
		
		JPanel roomListPanel = new JPanel();
		roomListPanel.setBounds(500, 35, 390, 615);
		c.add(roomListPanel);
		
		JPanel rankingPanel = new JPanel();
		rankingPanel.setBounds(25, 35, 450, 335);
		c.add(rankingPanel);
		
		JPanel anteChatPanel = new JPanel();
		anteChatPanel.setBounds(25, 380, 450, 270);
		c.add(anteChatPanel);
		
		JButton createRoomBtn = new JButton("방 생성");
		createRoomBtn.setBounds(1010, 70, 160, 56);
		getContentPane().add(createRoomBtn);
		
		JButton gameSetBtn = new JButton("게임 설정");
		gameSetBtn.setBounds(1010, 140, 160, 56);
		getContentPane().add(gameSetBtn);
		
		setVisible(true);
		
	}
}