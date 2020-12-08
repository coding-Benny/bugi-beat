import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

public class GamePanel extends JPanel {
	private Image screenImage;
	private Graphics screenGraphic;

	private ObjectOutputStream oos;

	public static Music standbyMusic = new Music("stand by beat.mp3", true);

	private RoomSetting roomSetPanel = new RoomSetting();
	private RoomChat roomChatPanel = new RoomChat();
	public static Image background;
	public Image gameScreenBg;

	private Image line4_bg_Img = new ImageIcon(Main.class.getResource("/images/4line-bg.png")).getImage();
	private Image line6_bg_Img = new ImageIcon(Main.class.getResource("/images/6line-bg.png")).getImage();
	private Image gamescreenbgImg = new ImageIcon(Main.class.getResource("/images/game-bg0.png")).getImage();
	private ImageIcon startBtnEnteredImg = new ImageIcon(Main.class.getResource("/images/start1.png"));
	private ImageIcon startBtnImg = new ImageIcon(Main.class.getResource("/images/start0.png"));
	private ImageIcon quitBtnEnteredImg = new ImageIcon(Main.class.getResource("/images/roomquit1.png"));
	private ImageIcon quitBtnImg = new ImageIcon(Main.class.getResource("/images/roomquit0.png"));
	private ImageIcon roomsetEnteredImg = new ImageIcon(Main.class.getResource("/images/roomsetting1.png"));
	private ImageIcon roomsetImg = new ImageIcon(Main.class.getResource("/images/roomsetting0.png"));

	private JButton startBtn = new JButton(startBtnImg);
	private JButton readyBtn = new JButton("준비");
	private JButton roomsetBtn = new JButton(roomsetImg);
	private JButton quitBtn = new JButton(quitBtnImg);
	private JLabel roomInfo = new JLabel("");
	public static JProgressBar feverBar = new JProgressBar();
	public static JProgressBar lifeBar = new JProgressBar();
	private boolean isMainScreen = true;
	private boolean isGameScreen = false;
	private boolean isReady = false;
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
		paintComponents(g);
		try {
			Thread.sleep(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.repaint();

	}

	public GamePanel() {
		setSize(800, 720);
		setLayout(null);
		background = roomSetPanel.getGamePanelBg();
		gameScreenBg = gamescreenbgImg;

		standbyMusic.start();

		roomSetPanel.setBounds(12, 100, 780, 442); // 가로위치, 세로위치, 가로길이, 세로길이
		roomSetPanel.setVisible(false);
		add(roomSetPanel);

		roomChatPanel.setBounds(50, 540, 550, 180);
		roomChatPanel.setVisible(true);
		add(roomChatPanel);

		if (WaitingRoom.owner.equals(WaitingRoom.user)) {
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
					try {
						oos = WaitingRoom.oos;
						oos.flush();
						ChatMsg obcm = new ChatMsg(WaitingRoom.user, "450",
								roomSetPanel.getNowSelected() + "#" + roomSetPanel.getDifficulty());
						try {
							oos.writeObject(obcm);
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			add(startBtn);
		}
		else {
			readyBtn.setBounds(630, 550, 144, 60);
			readyBtn.setBorderPainted(false);
			readyBtn.setContentAreaFilled(false);
			readyBtn.setFocusPainted(false);
			readyBtn.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					//readyBtn.setIcon(readyBtnEnteredImg);
					readyBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
					if (Main.SOUND_EFFECT) {
						Music btnEnteredMusic = new Music("btnEnteredSound.mp3", false);
						btnEnteredMusic.start();
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
					//readyBtn.setIcon(readyBtnImg);
					readyBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

				@Override
				public void mousePressed(MouseEvent e) {
					if (Main.SOUND_EFFECT) {
						Music btnPressedMusic = new Music("btnPressedSound.mp3", false);
						btnPressedMusic.start();
					}
					ChatMsg obcm = null;
					if (!isReady) {
						isReady = true;
						obcm = new ChatMsg(WaitingRoom.user, "430", "READY");
					}
					else {
						isReady = false;
						obcm = new ChatMsg(WaitingRoom.user, "440", "UNREADY");
					}
					try {
						oos = WaitingRoom.oos;
						oos.flush();
						oos.writeObject(obcm);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			add(readyBtn);
		}

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
					standbyMusic.close();
					roomSetPanel.selectTrack(0);
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
				// dispose();
				if (isGameScreen) {
					isGameScreen = false;
					isMainScreen = true;
					roomChatPanel.setVisible(true);
					startBtn.setVisible(true);
					roomsetBtn.setVisible(true);
					roomSetPanel.setVisible(true);
					feverBar.setVisible(false);
					lifeBar.setVisible(false);
					gameScreenBg = null;
					game.close();
				}
				// else if (isMainScreen)
				// 대기실로 가도록 구현
			}
		});
		add(quitBtn);

		feverBar.setBackground(new Color(255, 230, 153));
		feverBar.setForeground(new Color(255, 192, 0));
		feverBar.setBorder(new LineBorder(new Color(255, 255, 255), 0, true));
		feverBar.setBounds(594, 21, 153, 19);
		feverBar.setVisible(false);
		feverBar.setValue(Note.fever);
		feverBar.setMaximum(10);
		add(feverBar);

		lifeBar.setBackground(new Color(246, 160, 160));
		lifeBar.setForeground(new Color(234, 46, 46));
		lifeBar.setBorder(new LineBorder(new Color(255, 255, 255), 0, true));
		lifeBar.setBounds(594, 61, 153, 19);
		lifeBar.setVisible(false);
		lifeBar.setValue(Note.life);
		lifeBar.setMaximum(10);
		add(lifeBar);

		roomInfo.setText("방 이름: " + WaitingRoom.roomTitle + " - 난이도: " + WaitingRoom.difficulty);
		roomInfo.setBounds(35, 30, 500, 30);
		roomInfo.setForeground(Color.WHITE);
		roomInfo.setFont(new Font("산돌수필B", Font.PLAIN, 28));
		add(roomInfo);
	}

	public void gameStart(int nowSelected, String difficulty) {
		selectedMusic = roomSetPanel.getSelectedMusic();
		musicTitle = roomSetPanel.getTrackList().get(nowSelected).getTitleName();

		if (selectedMusic != null)
			selectedMusic.close();

		game = new Game(musicTitle, difficulty, roomSetPanel.getTrackList().get(nowSelected).getGameMusic(),
				roomSetPanel.getLine());

		isMainScreen = false;
		isGameScreen = true;
		roomChatPanel.setVisible(false);
		startBtn.setVisible(false);
		roomsetBtn.setVisible(false);
		roomSetPanel.setVisible(false);
		roomInfo.setVisible(false);
		feverBar.setVisible(true);
		lifeBar.setVisible(true);

		if (roomSetPanel.getLine() == 6)
			gameScreenBg = line6_bg_Img;
		else if (roomSetPanel.getLine() == 4)
			gameScreenBg = line4_bg_Img;
		standbyMusic.close();

		game.start();
		setFocusable(true);
	}

	public Music getBackgroundMusic() {
		return standbyMusic;
	}

	public boolean isPlaying() {
		return isGameScreen;
	}
}