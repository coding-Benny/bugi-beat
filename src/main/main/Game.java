package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Game extends Thread {
	private Image judgementLineImg = new ImageIcon(Main.class.getResource("../images/judgement-line.png")).getImage();
	private Image noteRouteSImg = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteDImg = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteFImg = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteJImg = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteKImg = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image noteRouteLImg = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	private Image lifeBar_bg = new ImageIcon(Main.class.getResource("../images/life-bar-bg.png")).getImage();
	private Image feverBar_bg = new ImageIcon(Main.class.getResource("../images/fever-bar-bg.png")).getImage();
	private Image line4_Pressed = new ImageIcon(Main.class.getResource("../images/4line-p.png")).getImage();
	private Image fever_line4_Pressed = new ImageIcon(Main.class.getResource("../images/fever-4line-p.png")).getImage();
	private Image line6_Pressed = new ImageIcon(Main.class.getResource("../images/6line-p.png")).getImage();
	private Image fever_line6_Pressed = new ImageIcon(Main.class.getResource("../images/fever-6line-p.png")).getImage();
	private Image blueFlareImg;
	private Image linePressedImg;
	private Image judgeImg;

	private String titleName;
	private String difficulty;
	private String musicTitle;
	private int line;
	private boolean isFever = false;
	private Music gameMusic;

	ArrayList<Note> noteList = new ArrayList<Note>();

	public Game(String titleName, String difficulty, String musicTitle, int line) {
		this.titleName = titleName;
		this.difficulty = difficulty;
		this.musicTitle = musicTitle;
		this.line = line;
		gameMusic = new Music(this.musicTitle, false);
	}

	public void screenDraw(Graphics2D g) {
		if (line == 6) {
			g.drawImage(judgementLineImg, 11, 500, null);
			g.drawImage(noteRouteSImg, 10, 100, null);
			g.drawImage(noteRouteDImg, 20, 100, null);
			g.drawImage(noteRouteFImg, 30, 100, null);
			g.drawImage(noteRouteJImg, 40, 100, null);
			g.drawImage(noteRouteKImg, 50, 100, null);
			g.drawImage(noteRouteLImg, 60, 100, null);
			g.drawImage(noteRouteLImg, 60, 100, null);
			
			g.drawImage(feverBar_bg, 570, 10, null);
			g.drawImage(lifeBar_bg, 570, 50, null);
			
			for (int i = 0; i < noteList.size(); i++) {
				Note note = noteList.get(i);
				if (note.getY() > 500)
					judgeImg = new ImageIcon(Main.class.getResource("../images/miss.png")).getImage();
				if (!note.isProceeded()) { /* 사용하지 않는 노트는 화면에서 삭제 */
					noteList.remove(i);
					i--;
				} else {
					note.screenDraw(g);
				}
			}
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.setFont(new Font("산돌수필B", Font.PLAIN, 24));
			g.setColor(Color.WHITE);
			g.drawString(titleName, 40, 38);
			g.setFont(new Font("산돌수필B", Font.PLAIN, 30));
			g.setColor(Color.YELLOW);
			g.drawString("Score : ", 40, 70); // 92, 70
			g.setFont(new Font("산돌수필B", Font.PLAIN, 28));
			g.setColor(Color.ORANGE);
			g.drawString("Max Combo: ", 40, 100);

			g.setFont(new Font("산돌수필B", Font.PLAIN, 30));
			g.setColor(Color.WHITE);
			g.drawString("S", 110, 536);
			g.drawString("D", 225, 536);
			g.drawString("F", 340, 536);
			g.drawString("J", 455, 536);
			g.drawString("K", 570, 536);
			g.drawString("L", 690, 536);

			// g.drawImage(blueFlareImg, 150, 200, null);
			g.drawImage(judgeImg, 190, 270, null);
		}
		
		else if (line == 4) {
			g.drawImage(judgementLineImg, 11, 500, null);
			g.drawImage(noteRouteSImg, 155, 100, null);
			g.drawImage(noteRouteDImg, 330, 100, null);
			g.drawImage(noteRouteKImg, 500, 100, null);
			g.drawImage(noteRouteLImg, 675, 100, null);

			g.drawImage(feverBar_bg, 570, 20, null);
			g.drawImage(lifeBar_bg, 570, 60, null);
			
			for (int i = 0; i < noteList.size(); i++) {
				Note note = noteList.get(i);
				if (note.getY() > 620)
					judgeImg = new ImageIcon(Main.class.getResource("../images/miss.png")).getImage();
				if (!note.isProceeded()) { /* 사용하지 않는 노트는 화면에서 삭제 */
					noteList.remove(i);
					i--;
				} else {
					note.screenDraw(g);
				}
			}
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.setFont(new Font("산돌수필B", Font.PLAIN, 24));
			g.setColor(Color.WHITE);
			g.drawString(titleName, 40, 38);
			g.setFont(new Font("산돌수필B", Font.PLAIN, 30));
			g.setColor(Color.YELLOW);
			g.drawString("Score : ", 40, 70); // 92, 70
			g.setFont(new Font("산돌수필B", Font.PLAIN, 28));
			g.setColor(Color.ORANGE);
			g.drawString("Max Combo: ", 40, 100);

			g.setFont(new Font("산돌수필B", Font.PLAIN, 30));
			g.setColor(Color.WHITE);
			g.drawString("S", 140, 536);
			g.drawString("D", 315, 536);
			g.drawString("K", 485, 536);
			g.drawString("L", 660, 536);
		}
		// g.drawImage(blueFlareImg, 150, 200, null);
		g.drawImage(judgeImg, 190, 270, null);
	}

	public Image setPressNoteRoute() {
		if (line == 6 && !isFever)
			linePressedImg = line6_Pressed;
		else if (line == 6 && isFever)
			linePressedImg = fever_line6_Pressed;
		else if (line == 4 && !isFever)
			linePressedImg = line4_Pressed;
		else if (line == 4 && isFever)
			linePressedImg = fever_line4_Pressed;
		return linePressedImg;
	}

	public void pressS() {
		judge("S");
		noteRouteSImg = setPressNoteRoute();
		new Music("drumSmall1.mp3", false).start();
	}

	public void releaseS() {
		noteRouteSImg = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	}

	public void pressD() {
		judge("D");
		noteRouteDImg = setPressNoteRoute();
		new Music("drumSmall1.mp3", false).start();
	}

	public void releaseD() {
		noteRouteDImg = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	}

	public void pressF() {
		judge("F");
		noteRouteFImg = setPressNoteRoute();
		new Music("drumSmall1.mp3", false).start();
	}

	public void releaseF() {
		noteRouteFImg = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	}

	public void pressSpace() {
		judge("Space"); // 아이템
		new Music("senditem.mp3", false).start();
	}

	public void releaseSpace() {
		// 스페이스바 뗐을때 이펙트
	}

	public void pressJ() {
		judge("J");
		noteRouteJImg = setPressNoteRoute();
		new Music("drumSmall1.mp3", false).start();
	}

	public void releaseJ() {
		noteRouteJImg = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	}

	public void pressK() {
		judge("K");
		noteRouteKImg = setPressNoteRoute();
		new Music("drumSmall1.mp3", false).start();
	}

	public void releaseK() {
		noteRouteKImg = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
	}

	public void pressL() {
		judge("L");
		noteRouteLImg = setPressNoteRoute();
		new Music("drumSmall1.mp3", false).start();
	}

	public void releaseL() {
		noteRouteLImg = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
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
		if (titleName.equals("Alien - Lee SuHyun") && difficulty.equals("Easy") && line == 6) {
			int startTime = 4460 - Main.REACH_TIME * 1000;
			int gap = 125; /* 박자 계산 */
			beats = new Beat[] { new Beat(startTime, "Space"), new Beat(startTime + gap * 2, "D"),
					new Beat(startTime + gap * 4, "F"), new Beat(startTime + gap * 6, "K"),
					new Beat(startTime + gap * 8, "J"), new Beat(startTime + gap * 10, "Space"),
					new Beat(startTime + gap * 12, "S"), new Beat(startTime + gap * 14, "L"),
					new Beat(startTime + gap * 16, "F"), new Beat(startTime + gap * 18, "D"),
					new Beat(startTime + gap * 20, "K"), new Beat(startTime + gap * 22, "J"),
					new Beat(startTime + gap * 24, "Space"), new Beat(startTime + gap * 26, "F"),
					new Beat(startTime + gap * 28, "J"), new Beat(startTime + gap * 30, "D"),
					new Beat(startTime + gap * 32, "L"), new Beat(startTime + gap * 34, "K"),
					new Beat(startTime + gap * 36, "S"), new Beat(startTime + gap * 38, "Space"), };
		} else if (titleName.equals("Alien - Lee SuHyun") && difficulty.equals("Hard") && line == 6) {
			int startTime = 4460 - Main.REACH_TIME * 1000;
			int gap = 125; /* 박자 계산 */
			beats = new Beat[] { new Beat(startTime, "Space"), new Beat(startTime + gap * 2, "D"),
					new Beat(startTime + gap * 4, "F"), new Beat(startTime + gap * 6, "K"),
					new Beat(startTime + gap * 8, "J"), new Beat(startTime + gap * 10, "Space"),
					new Beat(startTime + gap * 12, "S"), new Beat(startTime + gap * 14, "L"),
					new Beat(startTime + gap * 16, "F"), new Beat(startTime + gap * 18, "D"),
					new Beat(startTime + gap * 20, "K"), new Beat(startTime + gap * 22, "J"),
					new Beat(startTime + gap * 24, "Space"), new Beat(startTime + gap * 26, "F"),
					new Beat(startTime + gap * 28, "J"), new Beat(startTime + gap * 30, "D"),
					new Beat(startTime + gap * 32, "L"), new Beat(startTime + gap * 34, "K"),
					new Beat(startTime + gap * 36, "S"), new Beat(startTime + gap * 38, "Space"), };
		} else if (titleName.equals("Kitchen - Lukrembo") && difficulty.equals("Easy")) {
			int startTime = 1000;
			int gap = 125;
			beats = new Beat[] { new Beat(startTime, "D"), };
		} else if (titleName.equals("Kitchen - Lukrembo") && difficulty.equals("Hard")) {
			int startTime = 1000;
			int gap = 125;
			beats = new Beat[] { new Beat(startTime, "D"), };
		} else if (titleName.equals("Biscuit - Lukrembo") && difficulty.equals("Easy")) {
			int startTime = 1000;
			beats = new Beat[] { new Beat(startTime, "S"), };
		} else if (titleName.equals("Biscuit - Lukrembo") && difficulty.equals("Hard")) {
			int startTime = 1000;
			beats = new Beat[] { new Beat(startTime, "Space"), };
		} else if (titleName.equals("Cafe - Lukrembo") && difficulty.equals("Easy")) {
			int startTime = 1000;
			beats = new Beat[] { new Beat(startTime, "F"), };
		} else if (titleName.equals("Cafe - Lukrembo") && difficulty.equals("Hard")) {
			int startTime = 1000;
			beats = new Beat[] { new Beat(startTime, "F"), };
		} else if (titleName.equals("Onion - Lukrembo") && difficulty.equals("Easy")) {
			int startTime = 1000;
			beats = new Beat[] { new Beat(startTime, "J"), };
		} else if (titleName.equals("Onion - Lukrembo") && difficulty.equals("Hard")) {
			int startTime = 1000;
			beats = new Beat[] { new Beat(startTime, "J"), };
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
		for (int i = 0; i < noteList.size(); i++) {
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
		else if (judge.equals("Good"))
			judgeImg = new ImageIcon(Main.class.getResource("../images/good.png")).getImage();
		else if (judge.equals("Great"))
			judgeImg = new ImageIcon(Main.class.getResource("../images/great.png")).getImage();
		else if (judge.equals("Perfect"))
			judgeImg = new ImageIcon(Main.class.getResource("../images/perfect.png")).getImage();
	}
}