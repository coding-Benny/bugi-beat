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
		g.drawImage(feverBar_bg, 570, 10, null);
		g.drawImage(lifeBar_bg, 570, 50, null);
		g.drawImage(judgementLineImg, 11, 500, null);
		if (line == 6) {
			g.drawImage(noteRouteSImg, 45, 80, null);
			g.drawImage(noteRouteDImg, 160, 80, null);
			g.drawImage(noteRouteFImg, 276, 80, null);
			g.drawImage(noteRouteJImg, 391, 80, null);
			g.drawImage(noteRouteKImg, 506, 80, null);
			g.drawImage(noteRouteLImg, 622, 80, null);
			
			for (int i = 0; i < noteList.size(); i++) {
				Note note = noteList.get(i);
				if (note.getY() > 560)
					judgeImg = new ImageIcon(Main.class.getResource("../images/miss.png")).getImage();
				if (!note.isProceeded()) { /* 사용하지 않는 노트는 화면에서 삭제 */
					noteList.remove(i);
					i--;
				} else {
					note.screenDraw(g);
				}
			}
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
			//g.drawImage(judgeImg, 220, 250, null);
		}
		
		else if (line == 4) {
			g.drawImage(noteRouteSImg, 48, 80, null);
			g.drawImage(noteRouteDImg, 221, 80, null);
			g.drawImage(noteRouteKImg, 392, 80, null);
			g.drawImage(noteRouteLImg, 566, 80, null);
			
			for (int i = 0; i < noteList.size(); i++) {
				Note note = noteList.get(i);
				if (note.getY() > 560)
					judgeImg = new ImageIcon(Main.class.getResource("../images/miss.png")).getImage();
				if (!note.isProceeded()) { /* 사용하지 않는 노트는 화면에서 삭제 */
					noteList.remove(i);
					i--;
				} else {
					note.screenDraw(g);
				}
			}
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

			// g.drawImage(blueFlareImg, 150, 200, null);
			//g.drawImage(judgeImg, 220, 250, null);
		}
		g.drawImage(judgeImg, 220, 250, null);
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
		if (titleName.equals("미행 - f(x)") && difficulty.equals("Easy") && line == 4) {  //s d k l
			int startTime = 0; //1000 - Main.REACH_TIME * 1000;
			int gap = 114; /* 박자 계산 */
			beats = new Beat[] { 
					new Beat(startTime + gap * 12, "L"),
					new Beat(startTime + gap * 13, "K"),
					new Beat(startTime + gap * 15, "D"),
					new Beat(startTime + gap * 25, "S"),
					new Beat(startTime + gap * 27, "D"),
					new Beat(startTime + gap * 29, "K"),
					new Beat(startTime + gap * 32, "L"),
					new Beat(startTime + gap * 47, "K"),
					new Beat(startTime + gap * 52, "L"),
					new Beat(startTime + gap * 56, "S"),
					new Beat(startTime + gap * 61, "D"),
					new Beat(startTime + gap * 64, "K"),
					new Beat(startTime + gap * 66, "L"),
					new Beat(startTime + gap * 68, "K"),
					new Beat(startTime + gap * 70, "D"),
					new Beat(startTime + gap * 84, "S"),
					new Beat(startTime + gap * 86, "D"),
					new Beat(startTime + gap * 89, "K"),
					new Beat(startTime + gap * 97, "L"),
					new Beat(startTime + gap * 99, "K"),
					new Beat(startTime + gap *  102, "D"),
					new Beat(startTime + gap * 104, "S"),
					new Beat(startTime + gap * 120, "K"),
					new Beat(startTime + gap * 124, "L"),
					new Beat(startTime + gap * 129, "S"),
					new Beat(startTime + gap * 139, "S"),
					new Beat(startTime + gap * 141, "D"),
					new Beat(startTime + gap * 143, "K"),
					new Beat(startTime + gap * 145, "L"),
					new Beat(startTime + gap * 147, "K"),
					new Beat(startTime + gap * 150, "K"),
					new Beat(startTime + gap * 152, "K"),
					new Beat(startTime + gap * 154, "D"),
					new Beat(startTime + gap * 156, "L"),
					new Beat(startTime + gap * 159, "S"),
					new Beat(startTime + gap * 161, "D"),
					new Beat(startTime + gap *  163, "K"),
					new Beat(startTime + gap * 165, "S"),
					new Beat(startTime + gap * 174, "S"),
					new Beat(startTime + gap * 176, "D"),
					new Beat(startTime + gap * 179, "K"),
					new Beat(startTime + gap * 181, "K"),
					new Beat(startTime + gap * 184, "K"),
					new Beat(startTime + gap * 186, "D"),
					new Beat(startTime + gap * 188, "K"),
					new Beat(startTime + gap * 190, "D"),
					new Beat(startTime + gap * 193, "K"),
					new Beat(startTime + gap * 195, "S"),
					new Beat(startTime + gap * 197, "L"),
					new Beat(startTime + gap * 201, "D"),
					new Beat(startTime + gap * 208, "D"),
					new Beat(startTime + gap * 210, "K"),
					new Beat(startTime + gap * 212, "S"),
					new Beat(startTime + gap * 215, "D"),
					new Beat(startTime + gap * 217, "K"),
					new Beat(startTime + gap * 219, "L"),
					new Beat(startTime + gap *  222, "K"),
					new Beat(startTime + gap * 224, "S"),
					new Beat(startTime + gap * 226, "D"),
					new Beat(startTime + gap * 229, "K"),
					new Beat(startTime + gap * 230, "L"),
					new Beat(startTime + gap * 233, "K"),
					new Beat(startTime + gap * 235, "D"),
					new Beat(startTime + gap * 237, "S"),
					new Beat(startTime + gap * 245, "D"),
					new Beat(startTime + gap * 247, "K"),
					new Beat(startTime + gap * 249, "S"),
					new Beat(startTime + gap * 251, "D"),
					new Beat(startTime + gap * 253, "K"),
					new Beat(startTime + gap * 256, "L"),
					new Beat(startTime + gap * 268, "S"),
					new Beat(startTime + gap * 270, "K"),
					new Beat(startTime + gap *  272, "K"),
					new Beat(startTime + gap * 274, "K"),
					new Beat(startTime + gap * 277, "K"),
					new Beat(startTime + gap * 279, "D"),
					new Beat(startTime + gap * 283, "K"),
					new Beat(startTime + gap * 288, "L"),
					new Beat(startTime + gap * 291, "K"),
					new Beat(startTime + gap * 293, "K"),
					new Beat(startTime + gap * 297, "D"),
					new Beat(startTime + gap * 302, "S"),
					new Beat(startTime + gap * 304, "K"),
					new Beat(startTime + gap * 307, "K"),
					new Beat(startTime + gap * 309, "K"),
					new Beat(startTime + gap * 311, "D"),
					new Beat(startTime + gap * 313, "K"),
					new Beat(startTime + gap * 315, "S"),
					new Beat(startTime + gap * 326, "D"),
					new Beat(startTime + gap * 329, "D"),
					new Beat(startTime + gap * 331, "D"),
					new Beat(startTime + gap * 335, "K"),
					new Beat(startTime + gap * 337, "D"),
					new Beat(startTime + gap * 339, "K"),
					new Beat(startTime + gap * 342, "D"),
					new Beat(startTime + gap * 344, "K"),
					new Beat(startTime + gap * 346, "S"),
					new Beat(startTime + gap * 349, "K"),
					new Beat(startTime + gap * 351, "D"),
					new Beat(startTime + gap * 356, "K"),
					new Beat(startTime + gap * 360, "L"),
					new Beat(startTime + gap * 363, "K"),
					new Beat(startTime + gap * 365, "D"),
					new Beat(startTime + gap * 369, "K"),
					new Beat(startTime + gap * 374, "S"),
					new Beat(startTime + gap * 377, "D"),
					new Beat(startTime + gap * 379, "K"),
					new Beat(startTime + gap * 381, "D"),
					new Beat(startTime + gap * 383, "K"),

			};
		} 
		else if (titleName.equals("미행 - f(x)") && difficulty.equals("Hard") && line == 4) {
			int startTime = 1000 - Main.REACH_TIME * 1000;
			int gap = 114; /* 박자 계산 */
			beats = new Beat[] { 
					new Beat(startTime + gap * 10, "S"),
			};
		}
		else if (titleName.equals("미행 - f(x)") && difficulty.equals("Easy") && line == 6) {//s d f j k l
			int startTime = 1000 - Main.REACH_TIME * 1000;
			int gap = 114; /* 박자 계산 */
			beats = new Beat[] {
					new Beat(startTime, "S"),
					new Beat(startTime + gap * 4, "F"),
					new Beat(startTime + gap * 8, "K"),
					new Beat(startTime + gap * 12, "D"),
					new Beat(startTime + gap * 16, "L"),
					new Beat(startTime + gap * 20, "D"),
					new Beat(startTime + gap * 24, "F"),
					new Beat(startTime + gap * 28, "K"),
					new Beat(startTime + gap * 32, "K"),
					new Beat(startTime + gap * 36, "J"),
			};
		}
		else if (titleName.equals("미행 - f(x)") && difficulty.equals("Hard") && line == 6) {
			int startTime = 1000 - Main.REACH_TIME * 1000;
			int gap = 114; /* 박자 계산 */
			beats = new Beat[] {
					new Beat(startTime, "S"),
					new Beat(startTime + gap * 4, "F"),
					new Beat(startTime + gap * 8, "K"),
					new Beat(startTime + gap * 12, "D"),
					new Beat(startTime + gap * 16, "L"),
					new Beat(startTime + gap * 20, "D"),
					new Beat(startTime + gap * 24, "F"),
					new Beat(startTime + gap * 28, "K"),
					new Beat(startTime + gap * 32, "K"),
					new Beat(startTime + gap * 36, "J"),
			};
		}else if (titleName.equals("Onion - Lukrembo") && difficulty.equals("Easy")) {
			int startTime = 1000;
			beats = new Beat[] { new Beat(startTime, "S"), };
		} else if (titleName.equals("Onion - Lukrembo") && difficulty.equals("Hard")) {
			int startTime = 1000;
			beats = new Beat[] { new Beat(startTime, "S"), };
		}
		int i = 0;
		gameMusic.start();
		while (i < beats.length && !isInterrupted()) {
			boolean dropped = false;
			if (beats[i].getTime() <= gameMusic.getTime()) {
				Note note = new Note(beats[i].getNoteName(), line);
				note.start();
				//System.out.print(gameMusic.getTime());
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
		//System.out.print("\n");
	}

	public void judgeEvent(String judge) {
		if (!judge.equals("None"))
			judgeImg = new ImageIcon(Main.class.getResource("../images/noteRoute.png")).getImage();
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