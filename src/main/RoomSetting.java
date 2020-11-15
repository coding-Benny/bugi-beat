package main;

import java.awt.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
	
	private JButton leftBtn = new JButton(leftBtnImg);
	private JButton rightBtn = new JButton(rightBtnImg);
	private JButton easyBtn;
	private JButton hardBtn;
	private JButton quitBtn = new JButton(quitBtnImg);
	
	public void paint(Graphics g) {//그리는 함수
		g.drawImage(background, 0, 0, null);
		
		g.drawImage(GameRoom.selectedImg, 70, 40, null);
		paintComponents(g);
	}
	
	public RoomSetting() {
		setSize(780, 442);
		setLayout(null);
		background = new ImageIcon(Main.class.getResource("../images/game-bg0.png")).getImage();
		
		//rightBtn.setVisible(false);
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
		
		//leftBtn.setVisible(false);
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
		

		if(GameRoom.level==1) {  //난이도 hard
			easyBtn = new JButton(easyBtnImg);
			hardBtn = new JButton(hardBtnEnteredImg);
		}
		else {     //난이도 easy
			easyBtn = new JButton(easyBtnEnteredImg);
			hardBtn = new JButton(hardBtnImg);
		}
		
		//easyBtn.setVisible(false);
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
				GameRoom.level=0; //난이도 easy로 저장
				easyBtn.setIcon(easyBtnEnteredImg);
				hardBtn.setIcon(hardBtnImg);
			}
		});
		add(easyBtn);
		
		//hardBtn.setVisible(false);
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
				GameRoom.level=1; //난이도 hard로 저장
				easyBtn.setIcon(easyBtnImg);
				hardBtn.setIcon(hardBtnEnteredImg);
			}
		});
		add(hardBtn);
		
		//quitBtn.setVisible(false);
		quitBtn.setBounds(678, 10, 90, 66);
		quitBtn.setBorderPainted(false);
		quitBtn.setContentAreaFilled(false);
		quitBtn.setFocusPainted(false);
		quitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				quitBtn.setIcon(quitBtnEnteredImg);
				quitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				if(Main.soundeffect) {
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
				if(Main.soundeffect) {
					Music btnPressedMusic = new Music("btnPressedSound.mp3", false);
					btnPressedMusic.start();
					setVisible(false);
					//GameRoom.selectedImg.setVisible(false);
				}
			}
		});
		add(quitBtn);
		
	}
	

	public static void selectTrack(int nowSelected) {
		if (GameRoom.selectedMusic != null)
			GameRoom.selectedMusic.close();
		GameRoom.selectedImg = new ImageIcon(Main.class.getResource("../images/" + GameRoom.trackList.get(nowSelected).getStartImage())).getImage();
		GameRoom.selectedMusic = new Music(GameRoom.trackList.get(nowSelected).getStartMusic(), true);
		//GameRoom.selectedMusic.start();
	}
	
	public void selectLeft() {
		if (GameRoom.nowSelected == 0)
			GameRoom.nowSelected = GameRoom.trackList.size() - 1;
		else
			GameRoom.nowSelected--;
		selectTrack(GameRoom.nowSelected);
		//GameRoom.standbyMusic.close();
	}
	
	public void selectRight() {
		if (GameRoom.nowSelected == GameRoom.trackList.size() - 1)
			GameRoom.nowSelected = 0;
		else
			GameRoom.nowSelected++;
		selectTrack(GameRoom.nowSelected);
		//GameRoom.standbyMusic.close();
	}
}
