package main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

public class GamePanel extends JPanel {
	private Image screenImage;
	private Graphics screenGraphic;

	private Music standbyMusic = new Music("stand by beat.mp3", true);

	private RoomSetting roomSetPanel = new RoomSetting();
	private RoomChat roomChatPanel = new RoomChat();
	private Image background;
	public static Image gameScreenBg;

	private ImageIcon gamescreenbgImg = new ImageIcon(Main.class.getResource("../images/game-bg0.png"));
	private ImageIcon startBtnEnteredImg = new ImageIcon(Main.class.getResource("../images/start1.png"));
	private ImageIcon startBtnImg = new ImageIcon(Main.class.getResource("../images/start0.png"));
	private ImageIcon quitBtnEnteredImg = new ImageIcon(Main.class.getResource("../images/roomquit1.png"));
	private ImageIcon quitBtnImg = new ImageIcon(Main.class.getResource("../images/roomquit0.png"));
	private ImageIcon roomsetEnteredImg = new ImageIcon(Main.class.getResource("../images/roomsetting1.png"));
	private ImageIcon roomsetImg = new ImageIcon(Main.class.getResource("../images/roomsetting0.png"));
	private ImageIcon bg1Img = new ImageIcon(Main.class.getResource("../images/room-bg1.png"));
	private ImageIcon bg2Img = new ImageIcon(Main.class.getResource("../images/room2-bg1.png"));
	private Image feverBar_bg = new ImageIcon(Main.class.getResource("../images/fever-bar-bg.png")).getImage();
	private Image line6_bg_Img = new ImageIcon(Main.class.getResource("../images/6line-bg.png")).getImage();
	private Image fever_line6_bg_Img = new ImageIcon(Main.class.getResource("../images/fever-6line-bg.png")).getImage();
	private Image line4_bg_Img = new ImageIcon(Main.class.getResource("../images/4line-bg.png")).getImage();
	private Image fever_line4_bg_Img = new ImageIcon(Main.class.getResource("../images/fever-4line-bg.png")).getImage();
	
	private JButton startBtn = new JButton(startBtnImg);
	private JButton roomsetBtn = new JButton(roomsetImg);
	private JButton quitBtn = new JButton(quitBtnImg);
	public static JProgressBar feverBar= new JProgressBar();
	public static JProgressBar lifeBar= new JProgressBar();
	public static JLabel scoreLabel = new JLabel();
	public static JLabel maxComboLabel = new JLabel();
	private boolean isMainScreen = true;
	private boolean isGameScreen = false;
	private String musicTitle;
	private Music selectedMusic;

	public static Game game;

	public void paint(Graphics g) { // 컴포넌트 본인만 paint
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw((Graphics2D) screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics2D g) {
		if (isMainScreen) {
			g.drawImage(background, 0, 0, null);
			g.drawImage(gameScreenBg, 12, 100, null);
		}
		if (isGameScreen) {
			g.drawImage(background, 0, 0, null);
			g.drawImage(gameScreenBg, 12, 100, null);
			
			g.drawImage(screenImage, 0, 0, null);
			game.screenDraw(g);
		}

		this.repaint();
		paintComponents(g);

	}

	public GamePanel() {
		setSize(800, 720);
		setLayout(null);
		setGamePanelBg();
		gameScreenBg = gamescreenbgImg.getImage();
		
		standbyMusic.start();

		roomSetPanel.setBounds(12, 100, 780, 442); // 가로위치, 세로위치, 가로길이, 세로길이
		roomSetPanel.setVisible(false);
		add(roomSetPanel);
		
		roomChatPanel.setBounds(50, 540, 550, 180);
		roomChatPanel.setVisible(true);
		add(roomChatPanel);

//		if(방장이면 statBtn, 아니면 readyBtn) {
		startBtn.setBounds(630, 550, 144, 60);
		startBtn.setBorderPainted(false);
		startBtn.setContentAreaFilled(false);
		startBtn.setFocusPainted(false);
		startBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
					startBtn.setIcon(startBtnEnteredImg);
					startBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
					if (Main.SOUND_EFFECT) {
						Music btnEnteredMusic = new Music("btnEnteredSound.mp3", false);
						btnEnteredMusic.start();
					}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				startBtn.setIcon(startBtnImg);
				startBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
					if (Main.SOUND_EFFECT) {
						Music btnPressedMusic = new Music("btnPressedSound.mp3", false);
						btnPressedMusic.start();
					}
					gameStart(roomSetPanel.getNowSelected(), "Easy");
					addKeyListener(new KeyListener());
			}
		});
		add(startBtn);
//		}

		roomsetBtn.setBounds(630, 600, 144, 60);
		roomsetBtn.setBorderPainted(false);
		roomsetBtn.setContentAreaFilled(false);
		roomsetBtn.setFocusPainted(false);
		roomsetBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
					roomsetBtn.setIcon(roomsetEnteredImg);
					roomsetBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
					if (Main.SOUND_EFFECT) {
						Music btnEnteredMusic = new Music("btnEnteredSound.mp3", false);
						btnEnteredMusic.start();
					}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				roomsetBtn.setIcon(roomsetImg);
				roomsetBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
					if (Main.SOUND_EFFECT) {
						Music btnPressedMusic = new Music("btnPressedSound.mp3", false);
						btnPressedMusic.start();
						roomSetPanel.setVisible(true);
					}
			}
		});
		add(roomsetBtn);

		quitBtn.setBounds(630, 650, 144, 60);
		quitBtn.setBorderPainted(false);
		quitBtn.setContentAreaFilled(false);
		quitBtn.setFocusPainted(false);
		quitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				quitBtn.setIcon(quitBtnEnteredImg);
				quitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				if (Main.SOUND_EFFECT) {
					Music btnEnteredMusic = new Music("btnEnteredSound.mp3", false);
					btnEnteredMusic.start();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				quitBtn.setIcon(quitBtnImg);
				quitBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (Main.SOUND_EFFECT) {
					Music btnPressedMusic = new Music("btnPressedSound.mp3", false);
					btnPressedMusic.start();
				}
				// 방나가기
				//game.close();
			}
		});
		add(quitBtn);

	}

	public void gameStart(int nowSelected, String difficulty) {
		selectedMusic = roomSetPanel.getSelectedMusic();
		musicTitle = roomSetPanel.getTrackList().get(nowSelected).getTitleName();
		
		if (selectedMusic != null)
			selectedMusic.close();
		
		isMainScreen = false;
		isGameScreen = true;
		roomChatPanel.setVisible(false);
		startBtn.setVisible(false);
		roomsetBtn.setVisible(false);
		roomSetPanel.setVisible(false);
		feverBar.setVisible(true);
		lifeBar.setVisible(true);
		
		if(roomSetPanel.getLine()==6)
			gameScreenBg = line6_bg_Img;
		else if(roomSetPanel.getLine()==4)
			gameScreenBg = line4_bg_Img;
		standbyMusic.close();
		
		game = new Game(musicTitle, difficulty, roomSetPanel.getTrackList().get(nowSelected).getGameMusic(), roomSetPanel.getLine());
		game.start();
		setFocusable(true);
	}
	
	public void setGamePanelBg() {
		if(roomSetPanel.getBgSet() == 1)
			background = bg1Img.getImage();
		else
			background = bg2Img.getImage();
	}
	
	public Music getBackgroundMusic() {
		return standbyMusic;
	}
}
