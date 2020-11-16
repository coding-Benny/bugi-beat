package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Game extends Thread {
	private Image noteRouteLineImg = new ImageIcon(Main.class.getResource("../images/noteRouteLine.png")).getImage();
	private Image judgementLineImg = new ImageIcon(Main.class.getResource("../images/judgement-line.png")).getImage();
	private Image gameInfoImg = new ImageIcon(Main.class.getResource("../images/gameInfo.png")).getImage();
	private Image noteRouteSImg = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteDImg = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteFImg = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteSpace1Img = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteSpace2Img = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteJImg = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteKImg = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteLImg = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image blueFlareImg;
	private Image judgeImg;
	private Image keypadSImg = new ImageIcon(Main.class.getResource("../images/keypad.png")).getImage();
	private Image keypadDImg = new ImageIcon(Main.class.getResource("../images/keypad.png")).getImage();
	private Image keypadFImg = new ImageIcon(Main.class.getResource("../images/keypad.png")).getImage();
	private Image keypadSpace1Img = new ImageIcon(Main.class.getResource("../images/keypad.png")).getImage();
	private Image keypadSpace2Img = new ImageIcon(Main.class.getResource("../images/keypad.png")).getImage();
	private Image keypadJImg = new ImageIcon(Main.class.getResource("../images/keypad.png")).getImage();
	private Image keypadKImg = new ImageIcon(Main.class.getResource("../images/keypad.png")).getImage();
	private Image keypadLImg = new ImageIcon(Main.class.getResource("../images/keypad.png")).getImage();
	
	private String titleName;
	private String difficulty;
	private String musicTitle;
	private Music gameMusic;
	
	ArrayList<Note> noteList = new ArrayList<Note>();
	
	public Game(String titleName, String difficulty, String musicTitle) {
		this.titleName = titleName;
		this.difficulty = difficulty;
		this.musicTitle = musicTitle;
		gameMusic = new Music(this.musicTitle, false);
	}
	
	public void screenDraw(Graphics2D g) {
		g.drawImage(noteRouteSImg, 228, 30, null);
		g.drawImage(noteRouteDImg, 346, 30, null);
		g.drawImage(noteRouteFImg, 464, 30, null);
		g.drawImage(noteRouteSpace1Img, 582, 30, null);
		g.drawImage(noteRouteSpace2Img, 706, 30, null);
		g.drawImage(noteRouteJImg, 824, 30, null);
		g.drawImage(noteRouteKImg, 942, 30, null);
		g.drawImage(noteRouteLImg, 1060, 30, null);
		g.drawImage(noteRouteLineImg, 228, 30, null);
		g.drawImage(noteRouteLineImg, 346, 30, null);
		g.drawImage(noteRouteLineImg, 464, 30, null);
		g.drawImage(noteRouteLineImg, 582, 30, null);
		g.drawImage(noteRouteLineImg, 824, 30, null);
		g.drawImage(noteRouteLineImg, 942, 30, null);
		g.drawImage(noteRouteLineImg, 1060, 30, null);
		g.drawImage(noteRouteLineImg, 1178, 30, null);
		g.drawImage(gameInfoImg, 0, 660, null);
		g.drawImage(judgementLineImg, 0, 580, null);
		for (int i = 0; i < noteList.size(); i++) {
			Note note = noteList.get(i);
			if (note.getY() > 620)
				judgeImg = new ImageIcon(Main.class.getResource("../images/miss.png")).getImage();
			if (!note.isProceeded()) {	/* 사용하지 않는 노트는 화면에서 삭제 */
				noteList.remove(i);
				i--;
			}
			else {
				note.screenDraw(g);
			}
		}		
		g.setColor(Color.WHITE);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString(titleName, 20, 702);
		g.drawString(difficulty, 1190, 702);
		g.setFont(new Font("Arial", Font.PLAIN, 26));
		g.setColor(Color.DARK_GRAY);
		g.drawString("S", 280, 615);
		g.drawString("D", 398, 615);
		g.drawString("F", 516, 615);
		g.drawString("Space Bar", 641, 615);
		g.drawString("J", 876, 615);
		g.drawString("K", 994, 615);
		g.drawString("L", 1110, 615);
		g.setColor(Color.LIGHT_GRAY);
		g.setFont(new Font("Elephant", Font.BOLD, 30));
		g.drawString("000000", 565, 702);
		g.drawImage(blueFlareImg, 150, 200, null);
		g.drawImage(judgeImg, 410, 350, null);
		g.drawImage(keypadSImg, 228, 580, null);
		g.drawImage(keypadDImg, 346, 580, null);
		g.drawImage(keypadFImg, 464, 580, null);
		g.drawImage(keypadSpace1Img, 582, 580, null);
		g.drawImage(keypadSpace2Img, 706, 580, null);
		g.drawImage(keypadJImg, 824, 580, null);
		g.drawImage(keypadKImg, 942, 580, null);
		g.drawImage(keypadLImg, 1060, 580, null);
	}
	
	public void pressS() {
		judge("S");
		noteRouteSImg = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		keypadSImg = new ImageIcon(Main.class.getResource("../images/keypadPressed.png")).getImage();
		new Music("drumSmall1.mp3", false).start();
	}
	
	public void releaseS() {
		noteRouteSImg = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		keypadSImg = new ImageIcon(Main.class.getResource("../images/keypad.png")).getImage();
	}
	
	public void pressD() {
		judge("D");
		noteRouteDImg = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		keypadDImg = new ImageIcon(Main.class.getResource("../images/keypadPressed.png")).getImage();
		new Music("drumSmall1.mp3", false).start();
	}
	
	public void releaseD() {
		noteRouteDImg = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		keypadDImg = new ImageIcon(Main.class.getResource("../images/keypad.png")).getImage();
	}
	
	public void pressF() {
		judge("F");
		noteRouteFImg = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		keypadFImg = new ImageIcon(Main.class.getResource("../images/keypadPressed.png")).getImage();
		new Music("drumSmall1.mp3", false).start();
	}
	
	public void releaseF() {
		noteRouteFImg = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		keypadFImg = new ImageIcon(Main.class.getResource("../images/keypad.png")).getImage();
	}
	
	public void pressSpace() {
		judge("Space");
		noteRouteSpace1Img = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		noteRouteSpace2Img = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		keypadSpace1Img = new ImageIcon(Main.class.getResource("../images/keypadPressed.png")).getImage();
		keypadSpace2Img = new ImageIcon(Main.class.getResource("../images/keypadPressed.png")).getImage();
		new Music("drumBig1.mp3", false).start();
	}
	
	public void releaseSpace() {
		noteRouteSpace1Img = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		noteRouteSpace2Img = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		keypadSpace1Img = new ImageIcon(Main.class.getResource("../images/keypad.png")).getImage();
		keypadSpace2Img = new ImageIcon(Main.class.getResource("../images/keypad.png")).getImage();
	}
	
	public void pressJ() {
		judge("J");
		noteRouteJImg = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		keypadJImg = new ImageIcon(Main.class.getResource("../images/keypadPressed.png")).getImage();
		new Music("drumSmall1.mp3", false).start();
	}
	
	public void releaseJ() {
		noteRouteJImg = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		keypadJImg = new ImageIcon(Main.class.getResource("../images/keypad.png")).getImage();
	}
	
	public void pressK() {
		judge("K");
		noteRouteKImg = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		keypadKImg = new ImageIcon(Main.class.getResource("../images/keypadPressed.png")).getImage();
		new Music("drumSmall1.mp3", false).start();
	}
	
	public void releaseK() {
		noteRouteKImg = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		keypadKImg = new ImageIcon(Main.class.getResource("../images/keypad.png")).getImage();
	}
	
	public void pressL() {
		judge("L");
		noteRouteLImg = new ImageIcon(Main.class.getResource("../images/noteRoutePressed.png")).getImage();
		keypadLImg = new ImageIcon(Main.class.getResource("../images/keypadPressed.png")).getImage();
		new Music("drumSmall1.mp3", false).start();
	}
	
	public void releaseL() {
		noteRouteLImg = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
		keypadLImg = new ImageIcon(Main.class.getResource("../images/keypad.png")).getImage();
	}
	
	@Override
	public void run() {
		dropNotes(this.titleName);
	}
	
	public void close() {
		gameMusic.close();
		this.interrupt();
	}
	
	public void dropNotes(String titleName) {
		Beat[] beats = null;
		if (titleName.equals("Alien - Lee SuHyun") && difficulty.equals("Easy")) {
			int startTime = 4460 - Main.REACH_TIME * 1000;
			int gap = 125;	/* 박자 계산 */
			beats = new Beat[] {
					new Beat(startTime, "Space"),
					new Beat(startTime + gap * 2, "D"),
					new Beat(startTime + gap * 4, "F"),
					new Beat(startTime + gap * 6, "K"),
					new Beat(startTime + gap * 8, "J"),
					new Beat(startTime + gap * 10, "Space"),
					new Beat(startTime + gap * 12, "S"),
					new Beat(startTime + gap * 14, "L"),
					new Beat(startTime + gap * 16, "F"),
					new Beat(startTime + gap * 18, "D"),
					new Beat(startTime + gap * 20, "K"),
					new Beat(startTime + gap * 22, "J"),
					new Beat(startTime + gap * 24, "Space"),
					new Beat(startTime + gap * 26, "F"),
					new Beat(startTime + gap * 28, "J"),
					new Beat(startTime + gap * 30, "D"),
					new Beat(startTime + gap * 32, "L"),
					new Beat(startTime + gap * 34, "K"),
					new Beat(startTime + gap * 36, "S"),
					new Beat(startTime + gap * 38, "Space"),
			};
		}
		else if (titleName.equals("Alien - Lee SuHyun") && difficulty.equals("Hard")) {
			int startTime = 4460 - Main.REACH_TIME * 1000;
			int gap = 125;	/* 박자 계산 */
			beats = new Beat[] {
					new Beat(startTime, "Space"),
					new Beat(startTime + gap * 2, "D"),
					new Beat(startTime + gap * 4, "F"),
					new Beat(startTime + gap * 6, "K"),
					new Beat(startTime + gap * 8, "J"),
					new Beat(startTime + gap * 10, "Space"),
					new Beat(startTime + gap * 12, "S"),
					new Beat(startTime + gap * 14, "L"),
					new Beat(startTime + gap * 16, "F"),
					new Beat(startTime + gap * 18, "D"),
					new Beat(startTime + gap * 20, "K"),
					new Beat(startTime + gap * 22, "J"),
					new Beat(startTime + gap * 24, "Space"),
					new Beat(startTime + gap * 26, "F"),
					new Beat(startTime + gap * 28, "J"),
					new Beat(startTime + gap * 30, "D"),
					new Beat(startTime + gap * 32, "L"),
					new Beat(startTime + gap * 34, "K"),
					new Beat(startTime + gap * 36, "S"),
					new Beat(startTime + gap * 38, "Space"),
			};
		}
		else if (titleName.equals("Kitchen - Lukrembo") && difficulty.equals("Easy")) {
			int startTime = 1000;
			int gap = 125;
			beats = new Beat[] {
					new Beat(startTime, "D"),
			};
		}
		else if (titleName.equals("Kitchen - Lukrembo") && difficulty.equals("Hard")) {
			int startTime = 1000;
			int gap = 125;
			beats = new Beat[] {
					new Beat(startTime, "D"),
			};
		}
		else if (titleName.equals("Biscuit - Lukrembo") && difficulty.equals("Easy")) {
			int startTime = 1000;
			beats = new Beat[] {
					new Beat(startTime, "S"),
			};
		}
		else if (titleName.equals("Biscuit - Lukrembo") && difficulty.equals("Hard")) {
			int startTime = 1000;
			beats = new Beat[] {
					new Beat(startTime, "Space"),
			};
		}
		else if (titleName.equals("Cafe - Lukrembo") && difficulty.equals("Easy")) {
			int startTime = 1000;
			beats = new Beat[] {
					new Beat(startTime, "F"),
			};
		}
		else if (titleName.equals("Cafe - Lukrembo") && difficulty.equals("Hard")) {
			int startTime = 1000;
			beats = new Beat[] {
					new Beat(startTime, "F"),
			};
		}
		else if (titleName.equals("Onion - Lukrembo") && difficulty.equals("Easy")) {
			int startTime = 1000;
			beats = new Beat[] {
					new Beat(startTime, "J"),
			};
		}
		else if (titleName.equals("Onion - Lukrembo") && difficulty.equals("Hard")) {
			int startTime = 1000;
			beats = new Beat[] {
					new Beat(startTime, "J"),
			};
		}
		int i = 0;
		gameMusic.start();
		while (i < beats.length && !isInterrupted()) {
			boolean dropped = false;
			if (beats[i].getTime() <= gameMusic.getTime()) {
				Note note = new Note(beats[i].getNoteName());
				note.start();
				noteList.add(note);
				i++;
				dropped = true;
			}
			if (!dropped) {
				try {
					Thread.sleep(5);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/*
	 * 큐처럼 먼저 떨어지는 노트에 대해서 입력 정확도 검사
	 */
	public void judge(String input) {
		for(int i = 0; i < noteList.size(); i++) {
			Note note = noteList.get(i);
			if (input.equals(note.getNoteType())) {
				judgeEvent(note.judge());
				break;
			}
		}
	}
	
	public void judgeEvent(String judge) {
		if (!judge.equals("None"))
			blueFlareImg = new ImageIcon(Main.class.getResource("../images/blueFlare.png")).getImage();
		if (judge.equals("Miss"))
			judgeImg = new ImageIcon(Main.class.getResource("../images/miss.png")).getImage();
		else if (judge.equals("Late"))
			judgeImg = new ImageIcon(Main.class.getResource("../images/late.png")).getImage();
		else if (judge.equals("Good"))
			judgeImg = new ImageIcon(Main.class.getResource("../images/good.png")).getImage();
		else if (judge.equals("Great"))
			judgeImg = new ImageIcon(Main.class.getResource("../images/great.png")).getImage();
		else if (judge.equals("Perfect"))
			judgeImg = new ImageIcon(Main.class.getResource("../images/perfect.png")).getImage();
		else if (judge.equals("Early"))
			judgeImg = new ImageIcon(Main.class.getResource("../images/early.png")).getImage();
	}
}