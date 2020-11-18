package main;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;

public class AnteRoom extends JFrame {

	private Image background;
	
	private ImageIcon bg1Img = new ImageIcon(Main.class.getResource("../images/anteroom1-bg.png"));
	private ImageIcon bg2Img = new ImageIcon(Main.class.getResource("../images/anteroom2-bg.png"));

	private RoomSetting roomSetPanel = new RoomSetting();
	
	public void paint(Graphics g) {//그리는 함수
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
		setVisible(true);
		getContentPane().setLayout(null);

		if(roomSetPanel.getBgSet() == 1)
			background = bg1Img.getImage();
		else
			background = bg2Img.getImage();
		
		JScrollPane accessListPanel = new JScrollPane();
		accessListPanel.setBounds(915, 230, 340, 420);
		getContentPane().add(accessListPanel);
		
		JPanel roomListPanel = new JPanel();
		roomListPanel.setBounds(500, 35, 390, 615);
		getContentPane().add(roomListPanel);
		
		JPanel rankingPanel = new JPanel();
		rankingPanel.setBounds(25, 35, 450, 335);
		getContentPane().add(rankingPanel);
		
		JPanel anteChatPanel = new JPanel();
		anteChatPanel.setBounds(25, 380, 450, 270);
		getContentPane().add(anteChatPanel);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(1010, 70, 160, 56);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setBounds(1010, 140, 160, 56);
		getContentPane().add(btnNewButton_1);
		
	}
}