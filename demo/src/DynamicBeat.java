package dynamic_beat_8;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DynamicBeat extends JFrame {

	private Image screenImage;
	private Graphics screenGraphic;
	
	private ImageIcon exitBtnEnteredImg = new ImageIcon(Main.class.getResource("../images/exitEntered.png"));
	private ImageIcon exitBtnImg = new ImageIcon(Main.class.getResource("../images/exit.png"));
	
	private ImageIcon startBtnEnteredImg = new ImageIcon(Main.class.getResource("../images/btnStartEntered.png"));
	private ImageIcon startBtnImg = new ImageIcon(Main.class.getResource("../images/btnStart.png"));
	private ImageIcon quitBtnEnteredImg = new ImageIcon(Main.class.getResource("../images/btnQuitEntered.png"));
	private ImageIcon quitBtnImg = new ImageIcon(Main.class.getResource("../images/btnQuit.png"));
	private ImageIcon leftBtnEnteredImg = new ImageIcon(Main.class.getResource("../images/left-entered.png"));
	private ImageIcon leftBtnImg = new ImageIcon(Main.class.getResource("../images/left.png"));
	private ImageIcon rightBtnEnteredImg = new ImageIcon(Main.class.getResource("../images/right-entered.png"));
	private ImageIcon rightBtnImg = new ImageIcon(Main.class.getResource("../images/right.png"));
	
	private ImageIcon easyBtnEnteredImg = new ImageIcon(Main.class.getResource("../images/easyBtnEntered.png"));
	private ImageIcon easyBtnImg = new ImageIcon(Main.class.getResource("../images/easyBtn.png"));
	private ImageIcon hardBtnEnteredImg = new ImageIcon(Main.class.getResource("../images/hardBtnEntered.png"));
	private ImageIcon hardBtnImg = new ImageIcon(Main.class.getResource("../images/hardBtn.png"));
	
	private Image background = new ImageIcon(Main.class.getResource("../images/intro-background.jpg")).getImage();
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar.png")));
	private JButton exitBtn = new JButton(exitBtnImg);
	private JButton startBtn = new JButton(startBtnImg);
	private JButton quitBtn = new JButton(quitBtnImg);
	private JButton leftBtn = new JButton(leftBtnImg);
	private JButton rightBtn = new JButton(rightBtnImg);
	private JButton easyBtn = new JButton(easyBtnImg);
	private JButton hardBtn = new JButton(hardBtnImg);
	
	private int mouseX, mouseY;
	
	private boolean isMainScreen = false;
	
	ArrayList<Track> trackList = new ArrayList<Track>();
	
	private Image titleImg;
	private Image selectedImg;
	private Music selectedMusic;
	private int nowSelected = 0;
	
	public DynamicBeat() {
		setUndecorated(true); /* 실행 시 menuBar가 보이지 않음 */
		setTitle("Dynamic Beat");
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setBackground(new Color(0, 0, 0, 0));
		setLayout(null);
		
		/* 시작화면에서 음악이 무한 반복됨 */
		Music introMusic = new Music("intro-music.mp3", true);
		introMusic.start();
		
		trackList.add(new Track("kitchen-title-image.png", "kitchen-start-image.png",
				"kitchen-game-image.png", "kitchen-highlight.mp3", "Kitchen.mp3"));
		trackList.add(new Track("biscuit-title-image.png", "biscuit-start-image.png",
				"biscuit-game-image.png", "biscuit-highlight.mp3", "Biscuit.mp3"));
		trackList.add(new Track("cafe-title-image.png", "cafe-start-image.png",
				"cafe-game-image.png", "cafe-highlight.mp3", "Cafe.mp3"));
		trackList.add(new Track("onion-title-image.png", "onion-start-image.png",
				"onion-game-image.png", "onion-highlight.mp3", "Onion.mp3"));
		trackList.add(new Track("alien-title-image.png", "alien-start-image.png",
				"alien-game-image.png", "alien-highlight.mp3", "LEE SUHYUN-ALIEN.mp3"));

		exitBtn.setBounds(1245, 0, 32, 32);
		exitBtn.setBorderPainted(false);
		exitBtn.setContentAreaFilled(false);
		exitBtn.setFocusPainted(false);
		exitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitBtn.setIcon(exitBtnEnteredImg);
				exitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music btnEnteredMusic = new Music("btnEnteredSound.mp3", false);
				btnEnteredMusic.start();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				exitBtn.setIcon(exitBtnImg);
				exitBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				Music btnPressedMusic = new Music("btnPressedSound.mp3", false);
				btnPressedMusic.start();
				try {
					Thread.sleep(1000);	/* 효과음이 정상적으로 나타날 수 있도록 sleep */
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		});
		add(exitBtn);
		
		startBtn.setBounds(40, 200, 400, 100);
		startBtn.setBorderPainted(false);
		startBtn.setContentAreaFilled(false);
		startBtn.setFocusPainted(false);
		startBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				startBtn.setIcon(startBtnEnteredImg);
				startBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music btnEnteredMusic = new Music("btnEnteredSound.mp3", false);
				btnEnteredMusic.start();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				startBtn.setIcon(startBtnImg);
				startBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				Music btnPressedMusic = new Music("btnPressedSound.mp3", false);
				btnPressedMusic.start();
				introMusic.close();
				selectTrack(0);
				startBtn.setVisible(false);
				quitBtn.setVisible(false);
				leftBtn.setVisible(true);
				rightBtn.setVisible(true);
				easyBtn.setVisible(true);
				hardBtn.setVisible(true);
				background = new ImageIcon(Main.class.getResource("../images/mainBackground.jpg")).getImage();
				isMainScreen = true;
			}
		});
		add(startBtn);
		
		quitBtn.setBounds(40, 330, 400, 100);
		quitBtn.setBorderPainted(false);
		quitBtn.setContentAreaFilled(false);
		quitBtn.setFocusPainted(false);
		quitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				quitBtn.setIcon(quitBtnEnteredImg);
				quitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music btnEnteredMusic = new Music("btnEnteredSound.mp3", false);
				btnEnteredMusic.start();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				quitBtn.setIcon(quitBtnImg);
				quitBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				Music btnPressedMusic = new Music("btnPressedSound.mp3", false);
				btnPressedMusic.start();
				try {
					Thread.sleep(1000);	/* 효과음이 정상적으로 나타날 수 있도록 sleep */
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		});
		add(quitBtn);
		
		leftBtn.setVisible(false);
		leftBtn.setBounds(140, 310, 60, 60);
		leftBtn.setBorderPainted(false);
		leftBtn.setContentAreaFilled(false);
		leftBtn.setFocusPainted(false);
		leftBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				leftBtn.setIcon(leftBtnEnteredImg);
				leftBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music btnEnteredMusic = new Music("btnEnteredSound.mp3", false);
				btnEnteredMusic.start();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				leftBtn.setIcon(leftBtnImg);
				leftBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				Music btnPressedMusic = new Music("btnPressedSound.mp3", false);
				btnPressedMusic.start();
				selectLeft();
			}
		});
		add(leftBtn);
		
		rightBtn.setVisible(false);
		rightBtn.setBounds(1080, 310, 60, 60);
		rightBtn.setBorderPainted(false);
		rightBtn.setContentAreaFilled(false);
		rightBtn.setFocusPainted(false);
		rightBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				rightBtn.setIcon(rightBtnEnteredImg);
				rightBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music btnEnteredMusic = new Music("btnEnteredSound.mp3", false);
				btnEnteredMusic.start();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				rightBtn.setIcon(rightBtnImg);
				rightBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				Music btnPressedMusic = new Music("btnPressedSound.mp3", false);
				btnPressedMusic.start();
				selectRight();
			}
		});
		add(rightBtn);
		
		easyBtn.setVisible(false);
		easyBtn.setBounds(375, 580, 250, 67);
		easyBtn.setBorderPainted(false);
		easyBtn.setContentAreaFilled(false);
		easyBtn.setFocusPainted(false);
		easyBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				easyBtn.setIcon(easyBtnEnteredImg);
				easyBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music btnEnteredMusic = new Music("btnEnteredSound.mp3", false);
				btnEnteredMusic.start();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				easyBtn.setIcon(easyBtnImg);
				easyBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				Music btnPressedMusic = new Music("btnPressedSound.mp3", false);
				btnPressedMusic.start();
				gameStart(nowSelected, "easy");
			}
		});
		add(easyBtn);
		
		hardBtn.setVisible(false);
		hardBtn.setBounds(655, 580, 250, 67);
		hardBtn.setBorderPainted(false);
		hardBtn.setContentAreaFilled(false);
		hardBtn.setFocusPainted(false);
		hardBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				hardBtn.setIcon(hardBtnEnteredImg);
				hardBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music btnEnteredMusic = new Music("btnEnteredSound.mp3", false);
				btnEnteredMusic.start();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				hardBtn.setIcon(hardBtnImg);
				hardBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				Music btnPressedMusic = new Music("btnPressedSound.mp3", false);
				btnPressedMusic.start();
				gameStart(nowSelected, "hard");
			}
		});
		add(hardBtn);
		
		menuBar.setBounds(0, 0, 1280, 32);
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
		add(menuBar);
	}

	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		g.drawImage(background, 0, 0, null);
		if (isMainScreen) {
			g.drawImage(selectedImg, 340, 100, null);
			g.drawImage(titleImg, 320, 15, null);
		}
		/*
		 * 이미지 그리기 이외에도 프레임에 추가된 컴포넌트를 그려줌 menuBar는 항상 존재하는 이미지이고 고정적이기 때문에
		 * paintComponents로 그림
		 */
		paintComponents(g);
		this.repaint();
	}
	
	public void selectTrack(int nowSelected) {
		if (selectedMusic != null)
			selectedMusic.close();
		titleImg = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getTitleImage())).getImage();
		selectedImg = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getStartImage())).getImage();
		selectedMusic = new Music(trackList.get(nowSelected).getStartMusic(), true);
		selectedMusic.start();
	}
	
	public void selectLeft() {
		if (nowSelected == 0)
			nowSelected = trackList.size() - 1;
		else
			nowSelected--;
		selectTrack(nowSelected);
	}
	
	public void selectRight() {
		if (nowSelected == trackList.size() - 1)
			nowSelected = 0;
		else
			nowSelected++;
		selectTrack(nowSelected);
	}
	
	public void gameStart(int nowSelected, String difficulty) {
		if (selectedMusic != null)
			selectedMusic.close();
		isMainScreen = false;
		leftBtn.setVisible(false);
		rightBtn.setVisible(false);
		easyBtn.setVisible(false);
		hardBtn.setVisible(false);
		background = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getGameImage())).getImage();
	}
}
