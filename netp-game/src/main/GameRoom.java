package main;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.swing.*;

public class GameRoom extends JFrame {
	private Image screenImage;
	private Graphics screenGraphic;

	private Image background;

	public static Music standbyMusic = new Music("stand by beat.mp3", true);
	
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/bar.png")));
	private ImageIcon exitBtnEnteredImg = new ImageIcon(Main.class.getResource("../images/exit1.png"));
	private ImageIcon exitBtnImg = new ImageIcon(Main.class.getResource("../images/exit0.png"));
	public static ImageIcon line6_bg_Img = new ImageIcon(Main.class.getResource("../images/6line-bg.png"));
	public static ImageIcon fever_line6_bg_Img = new ImageIcon(Main.class.getResource("../images/fever-6line-bg.png"));
	public static ImageIcon line4_bg_Img = new ImageIcon(Main.class.getResource("../images/4line-bg.png"));
	public static ImageIcon fever_line4_bg_Img = new ImageIcon(Main.class.getResource("../images/fever-4line-bg.png"));
	
	private JButton exitBtn = new JButton(exitBtnImg);
	
	private GamePanel gamePanel = new GamePanel();
	private MonitorPanel monitorPanel = new MonitorPanel();
	private JLayeredPane Menubar = new JLayeredPane();

	public static ArrayList<Track> trackList = new ArrayList<Track>();
	private int mouseX, mouseY;
	public static int level = 0;  //기본 난이도 easy
	public static  Music selectedMusic;  //방에서 선택한 노래
	public static  Image selectedImg;
	public static  int nowSelected = 0;
	public static  int line = 4;  //칸 수 
	public static  int bgImg = 2; //배경이미지
	
	public GameRoom() {
		super("게임 방");
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setShape(new RoundRectangle2D.Double(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, 40, 40));
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		getContentPane().setLayout(null);;
		
		trackList.add(new Track("onion-start-image.png", "onion-highlight.mp3", "Onion.mp3", "Onion - Lukrembo"));
		trackList.add(new Track("alien-start-image.png", "alien-highlight.mp3", "LEE SUHYUN-ALIEN.mp3", "Alien - Lee SuHyun"));

		standbyMusic.start();
		
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
		getContentPane().add(menuBar);


		exitBtn.setBounds(1238, 12, 30, 27);
		exitBtn.setBorderPainted(false);
		exitBtn.setContentAreaFilled(false);
		exitBtn.setFocusPainted(false);
		exitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitBtn.setIcon(exitBtnEnteredImg);
				exitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				if(Main.soundeffect) {
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
				if(Main.soundeffect) {
					Music btnPressedMusic = new Music("btnPressedSound.mp3", false);
					btnPressedMusic.start();
				}
				try {
					Thread.sleep(500);	//효과음이 정상적으로 나타날 수 있도록 sleep
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		});
		getContentPane().add(exitBtn);
		
		gamePanel.setBounds(0, 0, 800, 720); // 가로위치, 세로위치, 가로길이, 세로길이
		getContentPane().add(gamePanel);
		
		monitorPanel.setBounds(800, 0, 480, 720);
		getContentPane().add(monitorPanel);

	}
}
