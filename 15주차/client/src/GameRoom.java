import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.swing.*;

public class GameRoom extends JFrame {
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("/images/bar.png")));
	private ImageIcon exitBtnEnteredImg = new ImageIcon(Main.class.getResource("/images/exit1.png"));
	private ImageIcon exitBtnImg = new ImageIcon(Main.class.getResource("/images/exit0.png"));
	private JButton exitBtn = new JButton(exitBtnImg);

	public static GamePanel gamePanel = new GamePanel();
	private MonitorPanel monitorPanel = new MonitorPanel();
	
	private int mouseX, mouseY;
	private ObjectOutputStream oos;
	
	private int id;
	private String title;
	private String difficulty;
	private int numOfLines;
	private String owner;
	
	public GameRoom(int id, String title, String difficulty, int numOfLines, String owner) {
		super("게임 방");
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setShape(new RoundRectangle2D.Double(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, 40, 40));
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		this.id = id;
		this.title = title;
		this.difficulty = difficulty;
		this.numOfLines = numOfLines;
		this.owner = owner;
		
		Container c = getContentPane();
		c.setLayout(null);
		
		menuBar.setBounds(0, 0, 1220, 30);
		menuBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {	/* 메뉴바 잡고 이동 */
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);	
			}
		});
		c.add(menuBar);
	
		exitBtn.setBounds(1238, 12, 30, 27);
		exitBtn.setBorderPainted(false);
		exitBtn.setContentAreaFilled(false);
		exitBtn.setFocusPainted(false);
		exitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitBtn.setIcon(exitBtnEnteredImg);
				exitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				if(Main.SOUND_EFFECT) {
					Music btnEnteredMusic = new Music("btnEnteredSound.mp3", false);
					btnEnteredMusic.start();
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				exitBtn.setIcon(exitBtnImg);
				exitBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(Main.SOUND_EFFECT) {
					Music btnPressedMusic = new Music("btnPressedSound.mp3", false);
					btnPressedMusic.start();
				}
				try {
					Thread.sleep(500);	//효과음이 정상적으로 나타날 수 있도록 sleep
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				if (gamePanel.isPlaying())	// 게임중에 나가면 대기실로 이동
					GamePanel.game.close();
				else {	// 게임방에서 나가기
					try {
						oos = WaitingRoom.oos;
						oos.flush();
						
						ChatMsg obcm = new ChatMsg(WaitingRoom.user, "410", "LEAVE");
						oos.writeObject(obcm);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					if(Main.SOUND_EFFECT)
						gamePanel.getBackgroundMusic().close();	// 게임방 배경음악은 끄고
					// 대기실 배경음악은 켜야 함
				}
				dispose();
			}
		});
		c.add(exitBtn);
		
		gamePanel.setBounds(0, 0, 800, 720); // 가로위치, 세로위치, 가로길이, 세로길이
		c.add(gamePanel);
		
		monitorPanel.setBounds(800, 0, 480, 720);
		c.add(monitorPanel);
	}

	public int getId() {
		return id;
	}
	
	public String getRoomTitle() {
		return title;
	}
	
	public static GamePanel getGamePanel() {
		return gamePanel;
	}
	
	public MonitorPanel getMonitorPanel() {
		return monitorPanel;
	}
}