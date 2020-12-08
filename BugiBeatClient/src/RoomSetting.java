import java.awt.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

public class RoomSetting extends JPanel {

	private Image background;
	private Image bgImg = new ImageIcon(Main.class.getResource("/images/game-bg0.png")).getImage();
	private Image gmbg1Img = new ImageIcon(Main.class.getResource("/images/room-bg1.png")).getImage();
	private Image gmbg2Img = new ImageIcon(Main.class.getResource("/images/room2-bg1.png")).getImage();
	private Image mnbg1Img = new ImageIcon(Main.class.getResource("/images/room-bg2.png")).getImage();
	private Image mnbg2Img = new ImageIcon(Main.class.getResource("/images/room2-bg2.png")).getImage();
	private ImageIcon leftBtnEnteredImg = new ImageIcon(Main.class.getResource("/images/leftBtn0.png"));
	private ImageIcon leftBtnImg = new ImageIcon(Main.class.getResource("/images/leftBtn1.png"));
	private ImageIcon rightBtnEnteredImg = new ImageIcon(Main.class.getResource("/images/rightBtn0.png"));
	private ImageIcon rightBtnImg = new ImageIcon(Main.class.getResource("/images/rightBtn1.png"));
	private ImageIcon easyBtnEnteredImg = new ImageIcon(Main.class.getResource("/images/easyBtnEntered.png"));
	private ImageIcon easyBtnImg = new ImageIcon(Main.class.getResource("/images/easyBtn.png"));
	private ImageIcon hardBtnEnteredImg = new ImageIcon(Main.class.getResource("/images/hardBtnEntered.png"));
	private ImageIcon hardBtnImg = new ImageIcon(Main.class.getResource("/images/hardBtn.png"));
	private ImageIcon quitBtnEnteredImg = new ImageIcon(Main.class.getResource("/images/quitBtnEntered.png"));
	private ImageIcon quitBtnImg = new ImageIcon(Main.class.getResource("/images/quitBtn.png"));
	private ImageIcon setbg11Img = new ImageIcon(Main.class.getResource("/images/roomset_bg1-1.png"));
	private ImageIcon setbg12Img = new ImageIcon(Main.class.getResource("/images/roomset_bg1-2.png"));
	private ImageIcon setbg21Img = new ImageIcon(Main.class.getResource("/images/roomset_bg2-1.png"));
	private ImageIcon setbg22Img = new ImageIcon(Main.class.getResource("/images/roomset_bg2-2.png"));
	
	private JButton easyBtn;
	private JButton hardBtn;
	private JButton leftBtn = new JButton(leftBtnImg);
	private JButton rightBtn = new JButton(rightBtnImg);
	private JButton quitBtn = new JButton(quitBtnImg);
	private JButton setBg1Btn= new JButton(setbg11Img);
	private JButton setBg2Btn= new JButton(setbg21Img);

	private ArrayList<Track> trackList = new ArrayList<Track>();
	
	private String difficulty = "Easy";
	private Music selectedMusic;
	private Image selectedImg;
	private int nowSelected = 0;
	private int line = 4;	// 기본(easy) 칸 수 
	private int bgSet = 2;	// 배경 이미지 번호
	
	public void paint(Graphics g) { //그리는 함수
		g.drawImage(background, 0, 0, null);
		g.drawImage(selectedImg, 70, 40, null);
		paintComponents(g);
	}
	
	public RoomSetting() {
		setSize(780, 442);
		setLayout(null);
		background = bgImg;

		trackList.add(new Track("onion-start-image.png", "onion-highlight.mp3", "Onion.mp3", "Onion - Lukrembo"));
		trackList.add(new Track("alien-start-image.png", "alien-highlight.mp3", "LEE SUHYUN-ALIEN.mp3", "Alien - Lee SuHyun"));
		trackList.add(new Track("shadow-start-image.png", "shadow - f(x)-highlight.mp3", "shadow - f(x).mp3", "미행 - f(x)"));

		selectedImg = new ImageIcon(Main.class.getResource("/images/" + trackList.get(0).getStartImage())).getImage();
		
		rightBtn.setBounds(500, 100, 45, 75);
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
		
		leftBtn.setBounds(20, 100, 45, 75);
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
		

		if(difficulty.equals("Hard")) {  // 난이도 hard
			easyBtn = new JButton(easyBtnImg);
			hardBtn = new JButton(hardBtnEnteredImg);
		}
		else {     //난이도 easy
			easyBtn = new JButton(easyBtnEnteredImg);
			hardBtn = new JButton(hardBtnImg);
		}
		
		easyBtn.setBounds(110, 340, 150, 66);
		easyBtn.setBorderPainted(false);
		easyBtn.setContentAreaFilled(false);
		easyBtn.setFocusPainted(false);
		easyBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				easyBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music btnEnteredMusic = new Music("btnEnteredSound.mp3", false);
				btnEnteredMusic.start();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				easyBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				Music btnPressedMusic = new Music("btnPressedSound.mp3", false);
				btnPressedMusic.start();
				difficulty = "Easy";
				easyBtn.setIcon(easyBtnEnteredImg);
				hardBtn.setIcon(hardBtnImg);
			}
		});
		add(easyBtn);
		
		hardBtn.setBounds(305, 340, 150, 66);
		hardBtn.setBorderPainted(false);
		hardBtn.setContentAreaFilled(false);
		hardBtn.setFocusPainted(false);
		hardBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				hardBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music btnEnteredMusic = new Music("btnEnteredSound.mp3", false);
				btnEnteredMusic.start();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				hardBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				Music btnPressedMusic = new Music("btnPressedSound.mp3", false);
				btnPressedMusic.start();
				difficulty = "Hard";
				easyBtn.setIcon(easyBtnImg);
				hardBtn.setIcon(hardBtnEnteredImg);
			}
		});
		add(hardBtn);
		
		quitBtn.setBounds(678, 10, 90, 66);
		quitBtn.setBorderPainted(false);
		quitBtn.setContentAreaFilled(false);
		quitBtn.setFocusPainted(false);
		quitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				quitBtn.setIcon(quitBtnEnteredImg);
				quitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				if(Main.SOUND_EFFECT) {
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
				if(Main.SOUND_EFFECT) {
					Music btnPressedMusic = new Music("btnPressedSound.mp3", false);
					btnPressedMusic.start();
					setVisible(false);
				}
				// standbyMusic 볼륨 키우는 코드 추가 예정
				if (selectedMusic != null)
					selectedMusic.close();
			}
		});
		add(quitBtn);
		
		if(bgSet == 1) {
			setBg1Btn.setIcon(setbg12Img);
			setBg2Btn.setIcon(setbg21Img);
		}
		else {
			setBg1Btn.setIcon(setbg11Img);
			setBg2Btn.setIcon(setbg22Img);
		}
		
		setBg1Btn.setBounds(556, 86, 192, 167);
		setBg1Btn.setBorderPainted(false);
		setBg1Btn.setContentAreaFilled(false);
		setBg1Btn.setFocusPainted(false);
		setBg1Btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setBg1Btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music btnEnteredMusic = new Music("btnEnteredSound.mp3", false);
				btnEnteredMusic.start();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				setBg1Btn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				Music btnPressedMusic = new Music("btnPressedSound.mp3", false);
				btnPressedMusic.start();
				setBg1Btn.setIcon(setbg12Img);
				setBg2Btn.setIcon(setbg21Img);
				bgSet=1;
				GamePanel.background = getGamePanelBg();
				MonitorPanel.background = getMonitorPanelBg();
			}
		});
		add(setBg1Btn);
		
		setBg2Btn.setBounds(556, 252, 192, 167);
		setBg2Btn.setBorderPainted(false);
		setBg2Btn.setContentAreaFilled(false);
		setBg2Btn.setFocusPainted(false);
		setBg2Btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setBg2Btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music btnEnteredMusic = new Music("btnEnteredSound.mp3", false);
				btnEnteredMusic.start();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				setBg2Btn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				Music btnPressedMusic = new Music("btnPressedSound.mp3", false);
				btnPressedMusic.start();
				setBg1Btn.setIcon(setbg11Img);
				setBg2Btn.setIcon(setbg22Img);
				bgSet=2;
				GamePanel.background = getGamePanelBg();
				MonitorPanel.background = getMonitorPanelBg();
			}
		});
		add(setBg2Btn);
		
	}

	public void selectTrack(int nowSelected) {
		if (selectedMusic != null)
			selectedMusic.close();
		// standByMusic 볼륨 줄이기 코드 추가 예정
		selectedImg = new ImageIcon(Main.class.getResource("/images/" + trackList.get(nowSelected).getStartImage())).getImage();
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
	
	public int getNowSelected() {
		return nowSelected;
	}
	
	public ArrayList<Track> getTrackList() {
		return trackList;
	}
	
	public Music getSelectedMusic() {
		return selectedMusic;
	}
	
	public int getLine() {
		return line;
	}
	
	public int getBgSet() {
		return bgSet;
	}
	
	public String getDifficulty() {
		return difficulty;
	}
	
	public Image getGamePanelBg() {
		if (bgSet == 1)
			return gmbg1Img;
		else
			return gmbg2Img;
	}
	
	public Image getMonitorPanelBg() {
		if (bgSet == 1)
			return mnbg1Img;
		else
			return mnbg2Img;
	}
}