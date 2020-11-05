package dynamic_beat_5;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

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
	
	private Image background = new ImageIcon(Main.class.getResource("../images/intro-background.jpg")).getImage();
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar.png")));
	private JButton exitBtn = new JButton(exitBtnImg);
	private JButton startBtn = new JButton(startBtnImg);
	private JButton quitBtn = new JButton(quitBtnImg);
	
	private int mouseX, mouseY;
	
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
				startBtn.setVisible(false);
				quitBtn.setVisible(false);
				background = new ImageIcon(Main.class.getResource("../images/mainBackground.jpg")).getImage();	
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
		
		/* 시작화면에서 음악이 무한 반복됨 */
		Music introMusic = new Music("intro-music.mp3", true);
		introMusic.start();
	}

	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		g.drawImage(background, 0, 0, null);
		/*
		 * 이미지 그리기 이외에도 프레임에 추가된 컴포넌트를 그려줌 menuBar는 항상 존재하는 이미지이고 고정적이기 때문에
		 * paintComponents로 그림
		 */
		paintComponents(g);
		this.repaint();
	}
}
