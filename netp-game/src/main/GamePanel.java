package main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GamePanel extends JPanel {
	private Image screenImage;
	private Graphics screenGraphic;

	private RoomSetting roomsetPanel = new RoomSetting();
	private RoomChat roomchatPanel = new RoomChat();
	private Image background;
	private Image gamescreenbg;
	private ImageIcon gamescreenImg4 = new ImageIcon(Main.class.getResource("../images/4line-bg.png"));
	private ImageIcon gamescreenImg6 = new ImageIcon(Main.class.getResource("../images/6line-bg.png"));
	private ImageIcon startBtnEnteredImg = new ImageIcon(Main.class.getResource("../images/start1.png"));
	private ImageIcon startBtnImg = new ImageIcon(Main.class.getResource("../images/start0.png"));
	private ImageIcon quitBtnEnteredImg = new ImageIcon(Main.class.getResource("../images/roomquit1.png"));
	private ImageIcon quitBtnImg = new ImageIcon(Main.class.getResource("../images/roomquit0.png"));
	private ImageIcon roomsetEnteredImg = new ImageIcon(Main.class.getResource("../images/roomsetting1.png"));
	private ImageIcon roomsetImg = new ImageIcon(Main.class.getResource("../images/roomsetting0.png"));

	private JButton startBtn = new JButton(startBtnImg);
	private JButton roomsetBtn = new JButton(roomsetImg);
	private JButton quitBtn = new JButton(quitBtnImg);
	private boolean isMainScreen = true;
	private boolean isGameScreen = false;
	public static String musictitle;

	public static StartGame game;

	public void paint(Graphics g) { // 컴포넌트 본인만 paint
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw((Graphics2D) screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics2D g) {
		if (isMainScreen) {
			g.drawImage(background, 0, 0, null);
			g.drawImage(gamescreenbg, 12, 100, null);
		}
		if (isGameScreen) {
			g.drawImage(background, 0, 0, null);
			g.drawImage(gamescreenbg, 12, 100, null);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.setFont(new Font("산돌수필B", Font.PLAIN, 22));
			g.setColor(Color.WHITE);
			g.drawString(musictitle, 40, 40);
			g.drawImage(screenImage, 0, 0, null);
			// game.screenDraw(g);
		}

		this.repaint();
		paintComponents(g);

	}

	public GamePanel() {
		setSize(800, 720);
		setLayout(null);
		background = new ImageIcon(Main.class.getResource("../images/room-bg1.png")).getImage();
		gamescreenbg = new ImageIcon(Main.class.getResource("../images/game-bg0.png")).getImage();

		roomsetPanel.setBounds(12, 100, 780, 442); // 가로위치, 세로위치, 가로길이, 세로길이
		roomsetPanel.setVisible(false);
		add(roomsetPanel);
		
		roomchatPanel.setBounds(50, 540, 550, 180);
		roomchatPanel.setVisible(true);
		add(roomchatPanel);

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
					if (Main.soundeffect) {
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
					if (Main.soundeffect) {
						Music btnPressedMusic = new Music("btnPressedSound.mp3", false);
						btnPressedMusic.start();
					}
					gameStart(GameRoom.nowSelected, "Easy");
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
					if (Main.soundeffect) {
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
					if (Main.soundeffect) {
						Music btnPressedMusic = new Music("btnPressedSound.mp3", false);
						btnPressedMusic.start();

						RoomSetting.selectTrack(GameRoom.nowSelected);
						roomsetPanel.setVisible(true);
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
				if (Main.soundeffect) {
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
				if (Main.soundeffect) {
					Music btnPressedMusic = new Music("btnPressedSound.mp3", false);
					btnPressedMusic.start();
				}
				// 방나가기
			}
		});
		add(quitBtn);
		
		// 대기상태, 방설정 game-bg0.png
		// 게임시작 game-bg1.png

	}

	public void gameStart(int nowSelected, String difficulty) {
		if (GameRoom.selectedMusic != null)
			GameRoom.selectedMusic.close();
		isMainScreen = false;
		isGameScreen = true;
		roomchatPanel.setVisible(false);
		startBtn.setVisible(false);
		roomsetBtn.setVisible(false);
		gamescreenbg = new ImageIcon(Main.class.getResource("../images/6line-bg.png")).getImage();
		GameRoom.standbyMusic.close();
		// game = new StartGame(GameRoom.trackList.get(nowSelected).getTitleName(),
		// difficulty, GameRoom.trackList.get(nowSelected).getGameMusic());
		// game.start();
		setFocusable(true);

		musictitle = GameRoom.trackList.get(GameRoom.nowSelected).getTitleName();
	}
}
