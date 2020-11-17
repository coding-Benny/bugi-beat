package client;

import java.awt.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

public class RoomSetting extends JPanel {

	private Image background;
	private ImageIcon leftBtnEnteredImg = new ImageIcon(Main.class.getResource("../images/leftBtn0.png"));
	private ImageIcon leftBtnImg = new ImageIcon(Main.class.getResource("../images/leftBtn1.png"));
	private ImageIcon rightBtnEnteredImg = new ImageIcon(Main.class.getResource("../images/rightBtn0.png"));
	private ImageIcon rightBtnImg = new ImageIcon(Main.class.getResource("../images/rightBtn1.png"));
	private ImageIcon easyBtnEnteredImg = new ImageIcon(Main.class.getResource("../images/easyBtnEntered.png"));
	private ImageIcon easyBtnImg = new ImageIcon(Main.class.getResource("../images/easyBtn.png"));
	private ImageIcon hardBtnEnteredImg = new ImageIcon(Main.class.getResource("../images/hardBtnEntered.png"));
	private ImageIcon hardBtnImg = new ImageIcon(Main.class.getResource("../images/hardBtn.png"));
	private ImageIcon quitBtnEnteredImg = new ImageIcon(Main.class.getResource("../images/quitBtnEntered.png"));
	private ImageIcon quitBtnImg = new ImageIcon(Main.class.getResource("../images/quitBtn.png"));
	private ImageIcon bgImg = new ImageIcon(Main.class.getResource("../images/game-bg0.png"));
	
	private JButton leftBtn = new JButton(leftBtnImg);
	private JButton rightBtn = new JButton(rightBtnImg);
	private JButton easyBtn;
	private JButton hardBtn;
	private JButton quitBtn = new JButton(quitBtnImg);

	private ArrayList<Track> trackList = new ArrayList<Track>();
	
	private String difficulty = "Easy";
	private Music selectedMusic;
	private Image selectedImg;
	public int nowSelected = 0;
	public int line = 4;  // 기본(easy) 칸 수 
	public int bgSet = 2; // 배경 이미지
	
	public void paint(Graphics g) { // 그리는 함수
		g.drawImage(background, 0, 0, null);
		g.drawImage(selectedImg, 70, 40, null);
		paintComponents(g);
	}
	
	public RoomSetting() {
		setSize(780, 442);
		setLayout(null);
		
		trackList.add(new Track("onion-start-image.png", "onion-highlight.mp3", "Onion.mp3", "Onion - Lukrembo"));
		trackList.add(new Track("alien-start-image.png", "alien-highlight.mp3", "LEE SUHYUN-ALIEN.mp3", "Alien - Lee SuHyun"));
		
		background = bgImg.getImage();
		selectedImg = new ImageIcon(Main.class.getResource("../images/" + trackList.get(0).getStartImage())).getImage();

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
		else {     // 난이도 easy
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
				line = 4;
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
				line = 6;
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
			}
		});
		add(quitBtn);
		
	}
	
	public void selectTrack(int nowSelected) {
		if (selectedMusic != null)
			selectedMusic.close();
		selectedImg = new ImageIcon(Main.class.getResource("../images/" + trackList.get(nowSelected).getStartImage())).getImage();
		selectedMusic = new Music(trackList.get(nowSelected).getStartMusic(), true);
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
}
