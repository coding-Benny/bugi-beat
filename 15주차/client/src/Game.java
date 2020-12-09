
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.ImageIcon;

public class Game extends Thread {
	private Image judgementLineImg = new ImageIcon(Main.class.getResource("/images/judgement-line.png")).getImage();
	private Image fever_judgementLineImg = new ImageIcon(Main.class.getResource("/images/fever-judgement-line.png"))
			.getImage();
	private Image noteRouteSImg = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	private Image noteRouteDImg = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	private Image noteRouteFImg = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	private Image noteRouteJImg = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	private Image noteRouteKImg = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	private Image noteRouteLImg = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	private Image line6_noteImg = new ImageIcon(Main.class.getResource("/images/6line-note.png")).getImage();
	private Image line4_noteImg = new ImageIcon(Main.class.getResource("/images/4line-note.png")).getImage();
	private Image fever_line6_noteImg = new ImageIcon(Main.class.getResource("/images/6line-note.png")).getImage();
	private Image fever_line4_noteImg = new ImageIcon(Main.class.getResource("/images/4line-note.png")).getImage();
	private Image lifeBar_bg = new ImageIcon(Main.class.getResource("/images/life-bar-bg.png")).getImage();
	private Image feverBar_bg = new ImageIcon(Main.class.getResource("/images/fever-bar-bg.png")).getImage();
	private Image line4_Pressed = new ImageIcon(Main.class.getResource("/images/4line-p.png")).getImage();
	private Image fever_line4_Pressed = new ImageIcon(Main.class.getResource("/images/fever-4line-p.png")).getImage();
	private Image line6_Pressed = new ImageIcon(Main.class.getResource("/images/6line-p.png")).getImage();
	private Image fever_line6_Pressed = new ImageIcon(Main.class.getResource("/images/fever-6line-p.png")).getImage();
	private Image cloudsendNoti0Img = new ImageIcon(Main.class.getResource("/images/cloouds-send0.png")).getImage();
	private Image cloudnothing = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	private Image attacknoti = new ImageIcon(Main.class.getResource("/images/attack.png")).getImage();
	private Image cloudrecv0Img = new ImageIcon(Main.class.getResource("/images/clouds-item.png")).getImage();
	private Image cloudrecv1Img = new ImageIcon(Main.class.getResource("/images/clouds-item1.png")).getImage();
	public Image itemNotiImg = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	public Image attackNotiImg = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	public static Image gameScreenBg;
	private Image linePressedImg;
	private Image judgeImg;
	private Image note_Img;

	private String titleName;
	private String difficulty;
	private String musicTitle;
	private int line;
	private int cnt = 0;
	private int cnt2 = 0;
	private Music gameMusic;
	private Music itemsendcount = new Music("itemcount.mp3", false);
	private static Music itemrecvcount = new Music("itemcount.mp3", false);
	private EndingResult ending;
	public static boolean showingResult;
	public static int isSendItem = 0;
	public static int isRecvItem = 0;
	public static boolean isItemOn = false;

	ArrayList<Note> noteList = new ArrayList<Note>();
	private CaptureTool captureTool = new CaptureTool(GameRoom.getGamePanel());

	private ObjectOutputStream oos;

	public Game(String titleName, String difficulty, String musicTitle, int line) {
		this.titleName = titleName;
		this.difficulty = difficulty;
		this.musicTitle = musicTitle;
		this.line = line;
		gameMusic = new Music(this.musicTitle, false);
	}

	public void screenDraw(Graphics2D g) {
		g.drawImage(gameScreenBg, 12, 100, null);
		g.drawImage(feverBar_bg, 570, 10, null);
		g.drawImage(lifeBar_bg, 570, 50, null);
		if (!Note.isFever)
			g.drawImage(judgementLineImg, 11, 500, null);
		else
			g.drawImage(fever_judgementLineImg, 11, 500, null);

		g.drawImage(itemNotiImg, 300, 10, null);

		if (line == 6) {
			g.drawImage(noteRouteSImg, 45, 80, null);
			g.drawImage(noteRouteDImg, 160, 80, null);
			g.drawImage(noteRouteFImg, 276, 80, null);
			g.drawImage(noteRouteJImg, 391, 80, null);
			g.drawImage(noteRouteKImg, 506, 80, null);
			g.drawImage(noteRouteLImg, 622, 80, null);

			for (int i = 0; i < noteList.size(); i++) {
				Note note = noteList.get(i);
				note.screenDraw(g);
				if (note.getY() > 560)
					judgeImg = new ImageIcon(Main.class.getResource("/images/miss.png")).getImage();

				if (!note.isProceeded()) { /* 사용하지 않는 노트는 화면에서 삭제 */
					noteList.remove(i);
					i--;
				}
			}
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.setFont(new Font("산돌수필B", Font.PLAIN, 24));
			g.setColor(Color.WHITE);
			g.drawString(titleName, 40, 38);
			g.setFont(new Font("산돌수필B", Font.PLAIN, 30));
			g.setColor(Color.YELLOW);
			g.drawString("Score : " + Note.score, 40, 70); // 92, 70
			g.setFont(new Font("산돌수필B", Font.PLAIN, 28));
			g.setColor(Color.ORANGE);
			g.drawString("Max Combo: " + Note.maxCombo, 40, 100);

			g.setFont(new Font("산돌수필B", Font.PLAIN, 30));
			g.setColor(Color.WHITE);
			g.drawString("S", 110, 536);
			g.drawString("D", 225, 536);
			g.drawString("F", 340, 536);
			g.drawString("J", 455, 536);
			g.drawString("K", 570, 536);
			g.drawString("L", 690, 536);
		}

		else if (line == 4) {
			g.drawImage(noteRouteSImg, 48, 80, null);
			g.drawImage(noteRouteDImg, 221, 80, null);
			g.drawImage(noteRouteKImg, 392, 80, null);
			g.drawImage(noteRouteLImg, 566, 80, null);

			for (int i = 0; i < noteList.size(); i++) {
				Note note = noteList.get(i);
				if (note.getY() > 560)
					judgeImg = new ImageIcon(Main.class.getResource("/images/miss.png")).getImage();
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
			g.drawString("Score : " + Note.score, 40, 70); // 92, 70
			g.setFont(new Font("산돌수필B", Font.PLAIN, 28));
			g.setColor(Color.ORANGE);
			g.drawString("Max Combo: " + Note.maxCombo, 40, 100);

			g.setFont(new Font("산돌수필B", Font.PLAIN, 30));
			g.setColor(Color.WHITE);
			g.drawString("S", 140, 536);
			g.drawString("D", 315, 536);
			g.drawString("K", 485, 536);
			g.drawString("L", 660, 536);
		}
		if (Note.combo != 0 && !showingResult) {
			g.setFont(new Font("산돌수필B", Font.PLAIN, 80));
			g.setColor(Color.BLACK);
			g.drawString(Note.combo + "", 380, 402);
			g.setFont(new Font("산돌수필B", Font.PLAIN, 70));
			g.setColor(Color.WHITE);
			g.drawString(Note.combo + "", 380, 400);
		}
		g.drawImage(attackNotiImg, 0, 87, null); // 먹구름

		if (showingResult) { // result
			judgeImg = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
			ending.draw(g);
		}
		g.drawImage(judgeImg, 220, 250, null);
	}

	public Image getPressNoteRoute() {
		if (line == 6 && !Note.isFever)
			linePressedImg = line6_Pressed;
		else if (line == 6 && Note.isFever)
			linePressedImg = fever_line6_Pressed;
		else if (line == 4 && !Note.isFever)
			linePressedImg = line4_Pressed;
		else if (line == 4 && Note.isFever)
			linePressedImg = fever_line4_Pressed;
		return linePressedImg;
	}

	public void pressS() {
		judge("S");
		noteRouteSImg = getPressNoteRoute();
		new Music("drumSmall1.mp3", false).start();
	}

	public void releaseS() {
		noteRouteSImg = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	}

	public void pressD() {
		judge("D");
		noteRouteDImg = getPressNoteRoute();
		new Music("drumSmall1.mp3", false).start();
	}

	public void releaseD() {
		noteRouteDImg = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	}

	public void pressF() {
		judge("F");
		noteRouteFImg = getPressNoteRoute();
		new Music("drumSmall1.mp3", false).start();
	}

	public void releaseF() {
		noteRouteFImg = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	}

	public void pressSpace() { // 아이템보내기
		if (isItemOn) {
			isSendItem = 2; // 모니터패널 전송아이콘
			itemNotiImg = cloudnothing;
			isItemOn = false;
			new Music("senditem.mp3", false).start();
			if (cnt != 0) {
				itemsendcount.stop();
				itemsendcount = new Music("itemcount.mp3", false);
			}
			itemsendcount.start();
			cnt++;

			try {
				oos = WaitingRoom.oos;
				oos.flush();
				ChatMsg obcm = new ChatMsg(WaitingRoom.user, "500", "item1");
				try {
					oos.writeObject(obcm);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void releaseSpace() {
		// 스페이스바 뗐을때 이펙트
	}

	public void releaseEnter() {
		// 엔터키
	}

	public void pressEnter() {
		if (showingResult) // 결과창에서만 입력받음
			GamePanel.game.close(); // 방 나가기
	}

	public void pressJ() {
		judge("J");
		noteRouteJImg = getPressNoteRoute();
		new Music("drumSmall1.mp3", false).start();
	}

	public void releaseJ() {
		noteRouteJImg = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	}

	public void pressK() {
		judge("K");
		noteRouteKImg = getPressNoteRoute();
		new Music("drumSmall1.mp3", false).start();
	}

	public void releaseK() {
		noteRouteKImg = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	}

	public void pressL() {
		judge("L");
		noteRouteLImg = getPressNoteRoute();
		new Music("drumSmall1.mp3", false).start();
	}

	public void releaseL() {
		noteRouteLImg = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
	}

	@Override
	public void run() {
		dropNotes(this.titleName);
	}

	public void close() {
		gameMusic.close();
		captureTool.close();
		this.interrupt();
	}

	public void dropNotes(String titleName) {
		Beat[] beats = null;
		if (titleName.equals("미행 - f(x)") && difficulty.equals("Easy") && line == 4) { // s d k l
			int startTime = 0; // 1000 - Main.REACH_TIME * 1000;
			int gap = 114; /* 박자 계산 */
			beats = new Beat[] { new Beat(startTime + gap * 11, "L"), new Beat(startTime + gap * 13, "K"),
					new Beat(startTime + gap * 15, "D"), new Beat(startTime + gap * 24, "S"),
					new Beat(startTime + gap * 27, "D"), new Beat(startTime + gap * 29, "K"),
					new Beat(startTime + gap * 31, "L"), new Beat(startTime + gap * 47, "K"),
					new Beat(startTime + gap * 51, "L"), new Beat(startTime + gap * 56, "D"),
					new Beat(startTime + gap * 61, "S"), new Beat(startTime + gap * 63, "D"),
					new Beat(startTime + gap * 65, "K"), new Beat(startTime + gap * 68, "L"),
					new Beat(startTime + gap * 70, "K"), new Beat(startTime + gap * 83, "L"),
					new Beat(startTime + gap * 85, "K"), new Beat(startTime + gap * 88, "D"),
					new Beat(startTime + gap * 97, "L"), new Beat(startTime + gap * 99, "K"),
					new Beat(startTime + gap * 101, "S"), new Beat(startTime + gap * 103, "D"),
					new Beat(startTime + gap * 119, "K"), new Beat(startTime + gap * 124, "L"),
					new Beat(startTime + gap * 128, "D"), new Beat(startTime + gap * 138, "S"),
					new Beat(startTime + gap * 140, "K"), new Beat(startTime + gap * 142, "D"),
					new Beat(startTime + gap * 145, "L"), new Beat(startTime + gap * 147, "D"),
					new Beat(startTime + gap * 150, "L"), new Beat(startTime + gap * 152, "S"),
					new Beat(startTime + gap * 154, "K"), new Beat(startTime + gap * 156, "D"),
					new Beat(startTime + gap * 159, "L"), new Beat(startTime + gap * 161, "K"),
					new Beat(startTime + gap * 163, "L"), new Beat(startTime + gap * 165, "K"),
					new Beat(startTime + gap * 174, "S"), new Beat(startTime + gap * 177, "D"),
					new Beat(startTime + gap * 179, "K"), new Beat(startTime + gap * 181, "L"),
					new Beat(startTime + gap * 183, "K"), new Beat(startTime + gap * 186, "D"),
					new Beat(startTime + gap * 188, "S"), new Beat(startTime + gap * 191, "D"),
					new Beat(startTime + gap * 193, "S"), new Beat(startTime + gap * 195, "D"),
					new Beat(startTime + gap * 197, "K"), new Beat(startTime + gap * 200, "D"),
					new Beat(startTime + gap * 208, "L"), new Beat(startTime + gap * 211, "K"),
					new Beat(startTime + gap * 213, "S"), new Beat(startTime + gap * 215, "D"),
					new Beat(startTime + gap * 218, "K"), new Beat(startTime + gap * 220, "L"),
					new Beat(startTime + gap * 222, "K"), new Beat(startTime + gap * 224, "S"),
					new Beat(startTime + gap * 227, "D"), new Beat(startTime + gap * 229, "K"),
					new Beat(startTime + gap * 231, "K"), new Beat(startTime + gap * 233, "D"),
					new Beat(startTime + gap * 237, "S"), new Beat(startTime + gap * 245, "K"),
					new Beat(startTime + gap * 247, "D"), new Beat(startTime + gap * 249, "S"),
					new Beat(startTime + gap * 252, "K"), new Beat(startTime + gap * 254, "D"),
					new Beat(startTime + gap * 256, "S"), new Beat(startTime + gap * 267, "K"),
					new Beat(startTime + gap * 269, "D"), new Beat(startTime + gap * 271, "D"),
					new Beat(startTime + gap * 274, "D"), new Beat(startTime + gap * 279, "K"),
					new Beat(startTime + gap * 283, "S"), new Beat(startTime + gap * 288, "L"),
					new Beat(startTime + gap * 291, "K"), new Beat(startTime + gap * 293, "D"),
					new Beat(startTime + gap * 297, "S"), new Beat(startTime + gap * 302, "L"),
					new Beat(startTime + gap * 304, "K"), new Beat(startTime + gap * 306, "L"),
					new Beat(startTime + gap * 309, "K"), new Beat(startTime + gap * 311, "L"),
					new Beat(startTime + gap * 313, "K"), new Beat(startTime + gap * 315, "S"),
					new Beat(startTime + gap * 327, "D"), new Beat(startTime + gap * 329, "K"),
					new Beat(startTime + gap * 332, "S"), new Beat(startTime + gap * 335, "K"),
					new Beat(startTime + gap * 337, "D"), new Beat(startTime + gap * 339, "K"),
					new Beat(startTime + gap * 342, "S"), new Beat(startTime + gap * 344, "K"),
					new Beat(startTime + gap * 346, "D"), new Beat(startTime + gap * 349, "K"),
					new Beat(startTime + gap * 352, "L"), new Beat(startTime + gap * 356, "K"),
					new Beat(startTime + gap * 361, "L"), new Beat(startTime + gap * 363, "K"),
					new Beat(startTime + gap * 365, "S"), new Beat(startTime + gap * 369, "D"),
					new Beat(startTime + gap * 374, "L"), new Beat(startTime + gap * 377, "K"),
					new Beat(startTime + gap * 379, "S"), new Beat(startTime + gap * 381, "D"),
					new Beat(startTime + gap * 383, "K"), new Beat(startTime + gap * 385, "K"),
					new Beat(startTime + gap * 388, "K"), new Beat(startTime + gap * 399, "K"),
					new Beat(startTime + gap * 401, "L"), new Beat(startTime + gap * 403, "K"),
					new Beat(startTime + gap * 407, "D"), new Beat(startTime + gap * 410, "S"),
					new Beat(startTime + gap * 413, "D"), new Beat(startTime + gap * 415, "K"),
					new Beat(startTime + gap * 418, "K"), new Beat(startTime + gap * 420, "L"),
					new Beat(startTime + gap * 422, "K"), new Beat(startTime + gap * 424, "L"),
					new Beat(startTime + gap * 426, "K"), new Beat(startTime + gap * 428, "L"),
					new Beat(startTime + gap * 434, "L"), new Beat(startTime + gap * 437, "K"),
					new Beat(startTime + gap * 438, "D"), new Beat(startTime + gap * 440, "S"),
					new Beat(startTime + gap * 442, "D"), new Beat(startTime + gap * 445, "D"),
					new Beat(startTime + gap * 449, "D"), new Beat(startTime + gap * 451, "S"),
					new Beat(startTime + gap * 453, "D"), new Beat(startTime + gap * 455, "K"),
					new Beat(startTime + gap * 457, "L"), new Beat(startTime + gap * 459, "K"),
					new Beat(startTime + gap * 462, "S"), new Beat(startTime + gap * 464, "D"),
					new Beat(startTime + gap * 466, "K"), new Beat(startTime + gap * 469, "L"),
					new Beat(startTime + gap * 471, "K"), new Beat(startTime + gap * 473, "L"),
					new Beat(startTime + gap * 478, "K"), new Beat(startTime + gap * 480, "S"),
					new Beat(startTime + gap * 482, "D"), new Beat(startTime + gap * 485, "K"),
					new Beat(startTime + gap * 487, "L"), new Beat(startTime + gap * 491, "K"),
					new Beat(startTime + gap * 493, "S"), new Beat(startTime + gap * 495, "D"),
					new Beat(startTime + gap * 497, "K"), new Beat(startTime + gap * 499, "L"),
					new Beat(startTime + gap * 501, "K"), new Beat(startTime + gap * 503, "S"),
					new Beat(startTime + gap * 506, "D"), new Beat(startTime + gap * 510, "K"),
					new Beat(startTime + gap * 511, "L"), new Beat(startTime + gap * 514, "D"),
					new Beat(startTime + gap * 521, "K"), new Beat(startTime + gap * 523, "S"),
					new Beat(startTime + gap * 526, "D"), new Beat(startTime + gap * 528, "K"),
					new Beat(startTime + gap * 530, "L"), new Beat(startTime + gap * 532, "K"),
					new Beat(startTime + gap * 534, "D"), new Beat(startTime + gap * 537, "S"),
					new Beat(startTime + gap * 539, "K"), new Beat(startTime + gap * 541, "K"),
					new Beat(startTime + gap * 543, "D"), new Beat(startTime + gap * 546, "L"),
					new Beat(startTime + gap * 553, "K"), new Beat(startTime + gap * 555, "K"),
					new Beat(startTime + gap * 558, "S"), new Beat(startTime + gap * 563, "L"),
					new Beat(startTime + gap * 569, "K"), new Beat(startTime + gap * 571, "D"),
					new Beat(startTime + gap * 574, "S"), new Beat(startTime + gap * 576, "L"),
					new Beat(startTime + gap * 578, "K"), new Beat(startTime + gap * 592, "L"),
					new Beat(startTime + gap * 594, "K"), new Beat(startTime + gap * 596, "K"),
					new Beat(startTime + gap * 605, "L"), new Beat(startTime + gap * 607, "D"),
					new Beat(startTime + gap * 609, "S"), new Beat(startTime + gap * 640, "end"), };
		} else if (titleName.equals("미행 - f(x)") && difficulty.equals("Hard") && line == 4) {
			int startTime = 0;
			int gap = 114; /* 박자 계산 */
			beats = new Beat[] { new Beat(startTime + gap * 10, "L"), new Beat(startTime + gap * 12, "K"),
					new Beat(startTime + gap * 15, "D"), new Beat(startTime + gap * 24, "L"),
					new Beat(startTime + gap * 26, "K"), new Beat(startTime + gap * 28, "S"),
					new Beat(startTime + gap * 30, "D"), new Beat(startTime + gap * 47, "D"),
					new Beat(startTime + gap * 47, "K"), new Beat(startTime + gap * 52, "S"),
					new Beat(startTime + gap * 52, "L"), new Beat(startTime + gap * 56, "D"),
					new Beat(startTime + gap * 56, "K"), new Beat(startTime + gap * 61, "K"),
					new Beat(startTime + gap * 63, "L"), new Beat(startTime + gap * 65, "K"),
					new Beat(startTime + gap * 67, "D"), new Beat(startTime + gap * 70, "S"),
					new Beat(startTime + gap * 83, "L"), new Beat(startTime + gap * 85, "K"),
					new Beat(startTime + gap * 87, "D"), new Beat(startTime + gap * 97, "L"),
					new Beat(startTime + gap * 99, "S"), new Beat(startTime + gap * 101, "D"),
					new Beat(startTime + gap * 103, "K"), new Beat(startTime + gap * 120, "L"),
					new Beat(startTime + gap * 124, "S"), new Beat(startTime + gap * 124, "K"),
					new Beat(startTime + gap * 129, "L"), new Beat(startTime + gap * 129, "D"),
					new Beat(startTime + gap * 138, "K"), new Beat(startTime + gap * 140, "S"),
					new Beat(startTime + gap * 143, "D"), new Beat(startTime + gap * 145, "K"),
					new Beat(startTime + gap * 147, "K"), new Beat(startTime + gap * 149, "K"),
					new Beat(startTime + gap * 152, "D"), new Beat(startTime + gap * 154, "S"),
					new Beat(startTime + gap * 156, "K"), new Beat(startTime + gap * 158, "D"),
					new Beat(startTime + gap * 161, "L"), new Beat(startTime + gap * 163, "S"),
					new Beat(startTime + gap * 165, "K"), new Beat(startTime + gap * 174, "D"),
					new Beat(startTime + gap * 176, "K"), new Beat(startTime + gap * 179, "S"),
					new Beat(startTime + gap * 181, "L"), new Beat(startTime + gap * 183, "D"),
					new Beat(startTime + gap * 185, "K"), new Beat(startTime + gap * 187, "D"),
					new Beat(startTime + gap * 190, "K"), new Beat(startTime + gap * 192, "S"),
					new Beat(startTime + gap * 194, "D"), new Beat(startTime + gap * 196, "K"),
					new Beat(startTime + gap * 198, "K"), new Beat(startTime + gap * 201, "D"),
					new Beat(startTime + gap * 208, "L"), new Beat(startTime + gap * 211, "K"),
					new Beat(startTime + gap * 213, "D"), new Beat(startTime + gap * 216, "S"),
					new Beat(startTime + gap * 218, "D"), new Beat(startTime + gap * 220, "K"),
					new Beat(startTime + gap * 222, "K"), new Beat(startTime + gap * 224, "D"),
					new Beat(startTime + gap * 226, "K"), new Beat(startTime + gap * 229, "S"),
					new Beat(startTime + gap * 231, "D"), new Beat(startTime + gap * 233, "K"),
					new Beat(startTime + gap * 235, "D"), new Beat(startTime + gap * 237, "D"),
					new Beat(startTime + gap * 244, "D"), new Beat(startTime + gap * 246, "K"),
					new Beat(startTime + gap * 248, "S"), new Beat(startTime + gap * 250, "D"),
					new Beat(startTime + gap * 252, "K"), new Beat(startTime + gap * 256, "D"),
					new Beat(startTime + gap * 256, "K"), new Beat(startTime + gap * 267, "S"),
					new Beat(startTime + gap * 267, "L"), new Beat(startTime + gap * 270, "D"),
					new Beat(startTime + gap * 270, "K"), new Beat(startTime + gap * 272, "K"),
					new Beat(startTime + gap * 274, "D"), new Beat(startTime + gap * 274, "K"),
					new Beat(startTime + gap * 278, "D"), new Beat(startTime + gap * 283, "S"),
					new Beat(startTime + gap * 288, "L"), new Beat(startTime + gap * 290, "K"),
					new Beat(startTime + gap * 292, "D"), new Beat(startTime + gap * 297, "S"),
					new Beat(startTime + gap * 301, "D"), new Beat(startTime + gap * 301, "K"),
					new Beat(startTime + gap * 304, "L"), new Beat(startTime + gap * 306, "K"),
					new Beat(startTime + gap * 308, "D"), new Beat(startTime + gap * 310, "S"),
					new Beat(startTime + gap * 313, "D"), new Beat(startTime + gap * 315, "D"),
					new Beat(startTime + gap * 326, "D"), new Beat(startTime + gap * 329, "S"),
					new Beat(startTime + gap * 331, "D"), new Beat(startTime + gap * 335, "K"),
					new Beat(startTime + gap * 337, "L"), new Beat(startTime + gap * 339, "K"),
					new Beat(startTime + gap * 341, "D"), new Beat(startTime + gap * 343, "S"),
					new Beat(startTime + gap * 345, "D"), new Beat(startTime + gap * 348, "K"),
					new Beat(startTime + gap * 351, "K"), new Beat(startTime + gap * 355, "D"),
					new Beat(startTime + gap * 355, "K"), new Beat(startTime + gap * 360, "S"),
					new Beat(startTime + gap * 361, "L"), new Beat(startTime + gap * 363, "D"),
					new Beat(startTime + gap * 365, "D"), new Beat(startTime + gap * 370, "D"),
					new Beat(startTime + gap * 370, "K"), new Beat(startTime + gap * 374, "S"),
					new Beat(startTime + gap * 374, "L"), new Beat(startTime + gap * 376, "K"),
					new Beat(startTime + gap * 378, "D"), new Beat(startTime + gap * 381, "D"),
					new Beat(startTime + gap * 383, "D"), new Beat(startTime + gap * 385, "S"),
					new Beat(startTime + gap * 387, "K"), new Beat(startTime + gap * 398, "K"),
					new Beat(startTime + gap * 401, "L"), new Beat(startTime + gap * 403, "K"),
					new Beat(startTime + gap * 407, "D"), new Beat(startTime + gap * 409, "S"),
					new Beat(startTime + gap * 412, "D"), new Beat(startTime + gap * 415, "K"),
					new Beat(startTime + gap * 417, "L"), new Beat(startTime + gap * 419, "K"),
					new Beat(startTime + gap * 421, "L"), new Beat(startTime + gap * 423, "K"),
					new Beat(startTime + gap * 425, "L"), new Beat(startTime + gap * 427, "K"),
					new Beat(startTime + gap * 435, "S"), new Beat(startTime + gap * 436, "D"),
					new Beat(startTime + gap * 439, "S"), new Beat(startTime + gap * 441, "D"),
					new Beat(startTime + gap * 448, "K"), new Beat(startTime + gap * 451, "L"),
					new Beat(startTime + gap * 453, "K"), new Beat(startTime + gap * 455, "D"),
					new Beat(startTime + gap * 457, "S"), new Beat(startTime + gap * 459, "D"),
					new Beat(startTime + gap * 462, "D"), new Beat(startTime + gap * 464, "D"),
					new Beat(startTime + gap * 467, "S"), new Beat(startTime + gap * 469, "D"),
					new Beat(startTime + gap * 471, "K"), new Beat(startTime + gap * 473, "L"),
					new Beat(startTime + gap * 477, "D"), new Beat(startTime + gap * 479, "S"),
					new Beat(startTime + gap * 482, "D"), new Beat(startTime + gap * 484, "K"),
					new Beat(startTime + gap * 486, "L"), new Beat(startTime + gap * 490, "K"),
					new Beat(startTime + gap * 495, "D"), new Beat(startTime + gap * 497, "S"),
					new Beat(startTime + gap * 499, "D"), new Beat(startTime + gap * 502, "K"),
					new Beat(startTime + gap * 504, "L"), new Beat(startTime + gap * 506, "K"),
					new Beat(startTime + gap * 510, "L"), new Beat(startTime + gap * 512, "K"),
					new Beat(startTime + gap * 514, "D"), new Beat(startTime + gap * 516, "S"),
					new Beat(startTime + gap * 518, "D"), new Beat(startTime + gap * 521, "K"),
					new Beat(startTime + gap * 523, "L"), new Beat(startTime + gap * 525, "K"),
					new Beat(startTime + gap * 527, "D"), new Beat(startTime + gap * 529, "S"),
					new Beat(startTime + gap * 532, "D"), new Beat(startTime + gap * 535, "K"),
					new Beat(startTime + gap * 537, "L"), new Beat(startTime + gap * 539, "K"),
					new Beat(startTime + gap * 541, "D"), new Beat(startTime + gap * 543, "D"),
					new Beat(startTime + gap * 545, "K"), new Beat(startTime + gap * 550, "D"),
					new Beat(startTime + gap * 552, "K"), new Beat(startTime + gap * 554, "S"),
					new Beat(startTime + gap * 557, "D"), new Beat(startTime + gap * 559, "L"),
					new Beat(startTime + gap * 564, "K"), new Beat(startTime + gap * 569, "L"),
					new Beat(startTime + gap * 572, "K"), new Beat(startTime + gap * 574, "D"),
					new Beat(startTime + gap * 575, "D"), new Beat(startTime + gap * 577, "D"),
					new Beat(startTime + gap * 591, "S"), new Beat(startTime + gap * 593, "D"),
					new Beat(startTime + gap * 596, "K"), new Beat(startTime + gap * 605, "L"),
					new Beat(startTime + gap * 607, "K"), new Beat(startTime + gap * 609, "D"),
					new Beat(startTime + gap * 611, "S"), new Beat(startTime + gap * 640, "end"), };
		} else if (titleName.equals("미행 - f(x)") && difficulty.equals("Easy") && line == 6) {// s d f j k l
			int startTime = 0;
			int gap = 114; /* 박자 계산 */
			beats = new Beat[] { new Beat(startTime + gap * 11, "S"), new Beat(startTime + gap * 13, "D"),
					new Beat(startTime + gap * 15, "F"), new Beat(startTime + gap * 24, "L"),
					new Beat(startTime + gap * 27, "K"), new Beat(startTime + gap * 29, "J"),
					new Beat(startTime + gap * 31, "F"), new Beat(startTime + gap * 48, "S"),
					new Beat(startTime + gap * 52, "L"), new Beat(startTime + gap * 57, "J"),
					new Beat(startTime + gap * 62, "S"), new Beat(startTime + gap * 64, "D"),
					new Beat(startTime + gap * 66, "F"), new Beat(startTime + gap * 68, "J"),
					new Beat(startTime + gap * 70, "K"), new Beat(startTime + gap * 84, "L"),
					new Beat(startTime + gap * 86, "K"), new Beat(startTime + gap * 88, "J"),
					new Beat(startTime + gap * 97, "S"), new Beat(startTime + gap * 100, "D"),
					new Beat(startTime + gap * 102, "F"), new Beat(startTime + gap * 104, "J"),
					new Beat(startTime + gap * 120, "J"), new Beat(startTime + gap * 125, "L"),
					new Beat(startTime + gap * 129, "K"), new Beat(startTime + gap * 138, "S"),
					new Beat(startTime + gap * 141, "L"), new Beat(startTime + gap * 143, "S"),
					new Beat(startTime + gap * 145, "L"), new Beat(startTime + gap * 148, "S"),
					new Beat(startTime + gap * 150, "L"), new Beat(startTime + gap * 152, "D"),
					new Beat(startTime + gap * 154, "K"), new Beat(startTime + gap * 156, "D"),
					new Beat(startTime + gap * 159, "K"), new Beat(startTime + gap * 161, "D"),
					new Beat(startTime + gap * 163, "K"), new Beat(startTime + gap * 165, "D"),
					new Beat(startTime + gap * 174, "F"), new Beat(startTime + gap * 177, "J"),
					new Beat(startTime + gap * 179, "F"), new Beat(startTime + gap * 182, "J"),
					new Beat(startTime + gap * 184, "F"), new Beat(startTime + gap * 186, "J"),
					new Beat(startTime + gap * 189, "D"), new Beat(startTime + gap * 191, "K"),
					new Beat(startTime + gap * 193, "S"), new Beat(startTime + gap * 195, "L"),
					new Beat(startTime + gap * 197, "D"), new Beat(startTime + gap * 200, "K"),
					new Beat(startTime + gap * 202, "F"), new Beat(startTime + gap * 209, "K"),
					new Beat(startTime + gap * 211, "J"), new Beat(startTime + gap * 214, "K"),
					new Beat(startTime + gap * 215, "J"), new Beat(startTime + gap * 218, "K"),
					new Beat(startTime + gap * 220, "J"), new Beat(startTime + gap * 222, "S"),
					new Beat(startTime + gap * 225, "D"), new Beat(startTime + gap * 227, "F"),
					new Beat(startTime + gap * 229, "F"), new Beat(startTime + gap * 231, "F"),
					new Beat(startTime + gap * 233, "D"), new Beat(startTime + gap * 236, "S"),
					new Beat(startTime + gap * 238, "J"), new Beat(startTime + gap * 245, "J"),
					new Beat(startTime + gap * 247, "K"), new Beat(startTime + gap * 249, "J"),
					new Beat(startTime + gap * 251, "K"), new Beat(startTime + gap * 254, "L"),
					new Beat(startTime + gap * 256, "J"), new Beat(startTime + gap * 268, "J"),
					new Beat(startTime + gap * 270, "J"), new Beat(startTime + gap * 272, "J"),
					new Beat(startTime + gap * 274, "K"), new Beat(startTime + gap * 279, "F"),
					new Beat(startTime + gap * 283, "D"), new Beat(startTime + gap * 288, "L"),
					new Beat(startTime + gap * 293, "J"), new Beat(startTime + gap * 297, "S"),
					new Beat(startTime + gap * 302, "L"), new Beat(startTime + gap * 306, "F"),
					new Beat(startTime + gap * 308, "F"), new Beat(startTime + gap * 311, "F"),
					new Beat(startTime + gap * 313, "D"), new Beat(startTime + gap * 315, "K"),
					new Beat(startTime + gap * 327, "S"), new Beat(startTime + gap * 329, "F"),
					new Beat(startTime + gap * 331, "D"), new Beat(startTime + gap * 335, "K"),
					new Beat(startTime + gap * 338, "J"), new Beat(startTime + gap * 340, "L"),
					new Beat(startTime + gap * 342, "K"), new Beat(startTime + gap * 345, "J"),
					new Beat(startTime + gap * 347, "F"), new Beat(startTime + gap * 349, "D"),
					new Beat(startTime + gap * 351, "S"), new Beat(startTime + gap * 356, "F"),
					new Beat(startTime + gap * 361, "L"), new Beat(startTime + gap * 365, "J"),
					new Beat(startTime + gap * 370, "K"), new Beat(startTime + gap * 374, "S"),
					new Beat(startTime + gap * 378, "J"), new Beat(startTime + gap * 380, "K"),
					new Beat(startTime + gap * 383, "J"), new Beat(startTime + gap * 386, "F"),
					new Beat(startTime + gap * 388, "L"), new Beat(startTime + gap * 399, "L"),
					new Beat(startTime + gap * 402, "K"), new Beat(startTime + gap * 404, "J"),
					new Beat(startTime + gap * 408, "L"), new Beat(startTime + gap * 410, "S"),
					new Beat(startTime + gap * 412, "D"), new Beat(startTime + gap * 415, "F"),
					new Beat(startTime + gap * 435, "L"), new Beat(startTime + gap * 437, "K"),
					new Beat(startTime + gap * 439, "J"), new Beat(startTime + gap * 441, "S"),
					new Beat(startTime + gap * 444, "D"), new Beat(startTime + gap * 449, "F"),
					new Beat(startTime + gap * 451, "D"), new Beat(startTime + gap * 453, "S"),
					new Beat(startTime + gap * 455, "D"), new Beat(startTime + gap * 458, "K"),
					new Beat(startTime + gap * 460, "L"), new Beat(startTime + gap * 463, "J"),
					new Beat(startTime + gap * 465, "F"), new Beat(startTime + gap * 467, "K"),
					new Beat(startTime + gap * 470, "D"), new Beat(startTime + gap * 472, "S"),
					new Beat(startTime + gap * 474, "L"), new Beat(startTime + gap * 478, "F"),
					new Beat(startTime + gap * 481, "J"), new Beat(startTime + gap * 483, "S"),
					new Beat(startTime + gap * 485, "K"), new Beat(startTime + gap * 488, "F"),
					new Beat(startTime + gap * 492, "D"), new Beat(startTime + gap * 496, "L"),
					new Beat(startTime + gap * 499, "K"), new Beat(startTime + gap * 501, "J"),
					new Beat(startTime + gap * 503, "L"), new Beat(startTime + gap * 506, "K"),
					new Beat(startTime + gap * 510, "S"), new Beat(startTime + gap * 512, "D"),
					new Beat(startTime + gap * 514, "F"), new Beat(startTime + gap * 517, "J"),
					new Beat(startTime + gap * 519, "K"), new Beat(startTime + gap * 521, "S"),
					new Beat(startTime + gap * 524, "L"), new Beat(startTime + gap * 527, "J"),
					new Beat(startTime + gap * 528, "D"), new Beat(startTime + gap * 531, "K"),
					new Beat(startTime + gap * 533, "L"), new Beat(startTime + gap * 536, "J"),
					new Beat(startTime + gap * 538, "F"), new Beat(startTime + gap * 540, "K"),
					new Beat(startTime + gap * 542, "D"), new Beat(startTime + gap * 544, "L"),
					new Beat(startTime + gap * 546, "S"), new Beat(startTime + gap * 551, "F"),
					new Beat(startTime + gap * 553, "D"), new Beat(startTime + gap * 555, "S"),
					new Beat(startTime + gap * 558, "J"), new Beat(startTime + gap * 560, "F"),
					new Beat(startTime + gap * 564, "K"), new Beat(startTime + gap * 569, "L"),
					new Beat(startTime + gap * 572, "K"), new Beat(startTime + gap * 574, "F"),
					new Beat(startTime + gap * 576, "K"), new Beat(startTime + gap * 578, "D"),
					new Beat(startTime + gap * 592, "L"), new Beat(startTime + gap * 594, "S"),
					new Beat(startTime + gap * 596, "D"), new Beat(startTime + gap * 605, "K"),
					new Beat(startTime + gap * 607, "D"), new Beat(startTime + gap * 610, "S"),
					new Beat(startTime + gap * 613, "J"), new Beat(startTime + gap * 593, "K"),
					new Beat(startTime + gap * 596, "D"), new Beat(startTime + gap * 605, "F"),
					new Beat(startTime + gap * 607, "D"), new Beat(startTime + gap * 609, "S"),
					new Beat(startTime + gap * 612, "J"), new Beat(startTime + gap * 640, "end"), };
		} else if (titleName.equals("미행 - f(x)") && difficulty.equals("Hard") && line == 6) {
			int startTime = 0;
			int gap = 114; /* 박자 계산 */
			beats = new Beat[] { new Beat(startTime + gap * 10, "L"), new Beat(startTime + gap * 12, "K"),
					new Beat(startTime + gap * 14, "S"), new Beat(startTime + gap * 16, "D"),
					new Beat(startTime + gap * 24, "L"), new Beat(startTime + gap * 26, "K"),
					new Beat(startTime + gap * 28, "F"), new Beat(startTime + gap * 30, "D"),
					new Beat(startTime + gap * 32, "S"), new Beat(startTime + gap * 47, "F"),
					new Beat(startTime + gap * 47, "J"), new Beat(startTime + gap * 51, "D"),
					new Beat(startTime + gap * 52, "K"), new Beat(startTime + gap * 56, "S"),
					new Beat(startTime + gap * 56, "L"), new Beat(startTime + gap * 61, "L"),
					new Beat(startTime + gap * 63, "K"), new Beat(startTime + gap * 65, "J"),
					new Beat(startTime + gap * 67, "K"), new Beat(startTime + gap * 70, "L"),
					new Beat(startTime + gap * 83, "S"), new Beat(startTime + gap * 85, "D"),
					new Beat(startTime + gap * 88, "F"), new Beat(startTime + gap * 97, "L"),
					new Beat(startTime + gap * 99, "K"), new Beat(startTime + gap * 101, "J"),
					new Beat(startTime + gap * 103, "D"), new Beat(startTime + gap * 120, "F"),
					new Beat(startTime + gap * 120, "J"), new Beat(startTime + gap * 124, "S"),
					new Beat(startTime + gap * 124, "L"), new Beat(startTime + gap * 128, "D"),
					new Beat(startTime + gap * 129, "K"), new Beat(startTime + gap * 138, "L"),
					new Beat(startTime + gap * 140, "K"), new Beat(startTime + gap * 142, "F"),
					new Beat(startTime + gap * 144, "D"), new Beat(startTime + gap * 146, "S"),
					new Beat(startTime + gap * 149, "F"), new Beat(startTime + gap * 151, "K"),
					new Beat(startTime + gap * 153, "D"), new Beat(startTime + gap * 155, "S"),
					new Beat(startTime + gap * 157, "L"), new Beat(startTime + gap * 159, "K"),
					new Beat(startTime + gap * 161, "F"), new Beat(startTime + gap * 163, "L"),
					new Beat(startTime + gap * 165, "J"), new Beat(startTime + gap * 174, "K"),
					new Beat(startTime + gap * 176, "L"), new Beat(startTime + gap * 178, "K"),
					new Beat(startTime + gap * 180, "S"), new Beat(startTime + gap * 182, "D"),
					new Beat(startTime + gap * 184, "K"), new Beat(startTime + gap * 186, "L"),
					new Beat(startTime + gap * 188, "F"), new Beat(startTime + gap * 189, "K"),
					new Beat(startTime + gap * 192, "S"), new Beat(startTime + gap * 194, "J"),
					new Beat(startTime + gap * 197, "L"), new Beat(startTime + gap * 199, "K"),
					new Beat(startTime + gap * 201, "J"), new Beat(startTime + gap * 209, "F"),
					new Beat(startTime + gap * 209, "K"), new Beat(startTime + gap * 211, "D"),
					new Beat(startTime + gap * 213, "S"), new Beat(startTime + gap * 216, "F"),
					new Beat(startTime + gap * 218, "L"), new Beat(startTime + gap * 220, "K"),
					new Beat(startTime + gap * 222, "D"), new Beat(startTime + gap * 222, "K"),
					new Beat(startTime + gap * 224, "D"), new Beat(startTime + gap * 224, "K"),
					new Beat(startTime + gap * 229, "L"), new Beat(startTime + gap * 229, "S"),
					new Beat(startTime + gap * 231, "D"), new Beat(startTime + gap * 231, "K"),
					new Beat(startTime + gap * 233, "F"), new Beat(startTime + gap * 233, "J"),
					new Beat(startTime + gap * 235, "S"), new Beat(startTime + gap * 235, "L"),
					new Beat(startTime + gap * 237, "F"), new Beat(startTime + gap * 237, "J"),
					new Beat(startTime + gap * 245, "L"), new Beat(startTime + gap * 247, "K"),
					new Beat(startTime + gap * 249, "J"), new Beat(startTime + gap * 251, "S"),
					new Beat(startTime + gap * 251, "L"), new Beat(startTime + gap * 254, "D"),
					new Beat(startTime + gap * 254, "K"), new Beat(startTime + gap * 256, "F"),
					new Beat(startTime + gap * 256, "L"), new Beat(startTime + gap * 267, "F"),
					new Beat(startTime + gap * 267, "J"), new Beat(startTime + gap * 269, "D"),
					new Beat(startTime + gap * 269, "K"), new Beat(startTime + gap * 271, "S"),
					new Beat(startTime + gap * 272, "L"), new Beat(startTime + gap * 274, "F"),
					new Beat(startTime + gap * 274, "J"), new Beat(startTime + gap * 276, "K"),
					new Beat(startTime + gap * 278, "L"), new Beat(startTime + gap * 283, "J"),
					new Beat(startTime + gap * 289, "S"), new Beat(startTime + gap * 289, "L"),
					new Beat(startTime + gap * 292, "K"), new Beat(startTime + gap * 297, "J"),
					new Beat(startTime + gap * 301, "S"), new Beat(startTime + gap * 301, "L"),
					new Beat(startTime + gap * 304, "K"), new Beat(startTime + gap * 306, "J"),
					new Beat(startTime + gap * 308, "K"), new Beat(startTime + gap * 310, "D"),
					new Beat(startTime + gap * 313, "F"), new Beat(startTime + gap * 315, "L"),
					new Beat(startTime + gap * 327, "S"), new Beat(startTime + gap * 329, "D"),
					new Beat(startTime + gap * 331, "F"), new Beat(startTime + gap * 334, "J"),
					new Beat(startTime + gap * 337, "K"), new Beat(startTime + gap * 339, "J"),
					new Beat(startTime + gap * 342, "D"), new Beat(startTime + gap * 344, "S"),
					new Beat(startTime + gap * 347, "F"), new Beat(startTime + gap * 349, "D"),
					new Beat(startTime + gap * 352, "K"), new Beat(startTime + gap * 356, "J"),
					new Beat(startTime + gap * 360, "S"), new Beat(startTime + gap * 360, "L"),
					new Beat(startTime + gap * 363, "K"), new Beat(startTime + gap * 365, "J"),
					new Beat(startTime + gap * 369, "F"), new Beat(startTime + gap * 374, "D"),
					new Beat(startTime + gap * 374, "K"), new Beat(startTime + gap * 376, "L"),
					new Beat(startTime + gap * 378, "K"), new Beat(startTime + gap * 381, "J"),
					new Beat(startTime + gap * 382, "S"), new Beat(startTime + gap * 385, "F"),
					new Beat(startTime + gap * 387, "L"), new Beat(startTime + gap * 399, "J"),
					new Beat(startTime + gap * 401, "K"), new Beat(startTime + gap * 404, "D"),
					new Beat(startTime + gap * 407, "S"), new Beat(startTime + gap * 409, "F"),
					new Beat(startTime + gap * 412, "K"), new Beat(startTime + gap * 414, "J"),
					new Beat(startTime + gap * 417, "L"), new Beat(startTime + gap * 419, "K"),
					new Beat(startTime + gap * 421, "J"), new Beat(startTime + gap * 423, "S"),
					new Beat(startTime + gap * 426, "D"), new Beat(startTime + gap * 428, "F"),
					new Beat(startTime + gap * 431, "D"), new Beat(startTime + gap * 433, "S"),
					new Beat(startTime + gap * 437, "L"), new Beat(startTime + gap * 439, "K"),
					new Beat(startTime + gap * 441, "J"), new Beat(startTime + gap * 443, "L"),
					new Beat(startTime + gap * 445, "K"), new Beat(startTime + gap * 449, "S"),
					new Beat(startTime + gap * 451, "D"), new Beat(startTime + gap * 453, "F"),
					new Beat(startTime + gap * 455, "S"), new Beat(startTime + gap * 457, "D"),
					new Beat(startTime + gap * 460, "K"), new Beat(startTime + gap * 462, "J"),
					new Beat(startTime + gap * 465, "L"), new Beat(startTime + gap * 467, "K"),
					new Beat(startTime + gap * 469, "J"), new Beat(startTime + gap * 471, "L"),
					new Beat(startTime + gap * 473, "S"), new Beat(startTime + gap * 478, "D"),
					new Beat(startTime + gap * 480, "F"), new Beat(startTime + gap * 482, "K"),
					new Beat(startTime + gap * 484, "L"), new Beat(startTime + gap * 487, "J"),
					new Beat(startTime + gap * 491, "K"), new Beat(startTime + gap * 496, "S"),
					new Beat(startTime + gap * 498, "D"), new Beat(startTime + gap * 500, "K"),
					new Beat(startTime + gap * 500, "F"), new Beat(startTime + gap * 503, "L"),
					new Beat(startTime + gap * 506, "J"), new Beat(startTime + gap * 509, "L"),
					new Beat(startTime + gap * 511, "K"), new Beat(startTime + gap * 513, "S"),
					new Beat(startTime + gap * 515, "D"), new Beat(startTime + gap * 517, "F"),
					new Beat(startTime + gap * 521, "L"), new Beat(startTime + gap * 523, "K"),
					new Beat(startTime + gap * 526, "J"), new Beat(startTime + gap * 528, "K"),
					new Beat(startTime + gap * 530, "L"), new Beat(startTime + gap * 532, "S"),
					new Beat(startTime + gap * 535, "F"), new Beat(startTime + gap * 538, "J"),
					new Beat(startTime + gap * 540, "K"), new Beat(startTime + gap * 542, "L"),
					new Beat(startTime + gap * 544, "J"), new Beat(startTime + gap * 546, "D"),
					new Beat(startTime + gap * 550, "L"), new Beat(startTime + gap * 553, "K"),
					new Beat(startTime + gap * 555, "L"), new Beat(startTime + gap * 557, "K"),
					new Beat(startTime + gap * 560, "L"), new Beat(startTime + gap * 564, "J"),
					new Beat(startTime + gap * 570, "S"), new Beat(startTime + gap * 570, "L"),
					new Beat(startTime + gap * 572, "D"), new Beat(startTime + gap * 572, "K"),
					new Beat(startTime + gap * 574, "F"), new Beat(startTime + gap * 574, "J"),
					new Beat(startTime + gap * 576, "S"), new Beat(startTime + gap * 576, "L"),
					new Beat(startTime + gap * 578, "D"), new Beat(startTime + gap * 578, "K"),
					new Beat(startTime + gap * 591, "L"), new Beat(startTime + gap * 594, "K"),
					new Beat(startTime + gap * 596, "J"), new Beat(startTime + gap * 606, "F"),
					new Beat(startTime + gap * 608, "D"), new Beat(startTime + gap * 610, "S"),
					new Beat(startTime + gap * 612, "S"), new Beat(startTime + gap * 640, "end"), };
		} else if (titleName.equals("All I Want for Christmas Is You") && difficulty.equals("Easy") && line == 4) {
			int startTime = 0;
			int gap = 114; /* 박자 계산 */
			beats = new Beat[] { new Beat(startTime + gap * 2, "S"), new Beat(startTime + gap * 5, "D"),
					new Beat(startTime + gap * 9, "K"), new Beat(startTime + gap * 12, "L"),
					new Beat(startTime + gap * 14, "K"), new Beat(startTime + gap * 18, "D"),
					new Beat(startTime + gap * 21, "S"), new Beat(startTime + gap * 25, "D"),
					new Beat(startTime + gap * 29, "L"), new Beat(startTime + gap * 33, "K"),
					new Beat(startTime + gap * 36, "D"), new Beat(startTime + gap * 39, "L"),
					new Beat(startTime + gap * 42, "S"), new Beat(startTime + gap * 45, "D"),
					new Beat(startTime + gap * 49, "K"), new Beat(startTime + gap * 54, "L"),
					new Beat(startTime + gap * 56, "K"), new Beat(startTime + gap * 61, "S"),
					new Beat(startTime + gap * 65, "D"), new Beat(startTime + gap * 68, "K"),
					new Beat(startTime + gap * 69, "D"), new Beat(startTime + gap * 73, "S"),
					new Beat(startTime + gap * 77, "D"), new Beat(startTime + gap * 80, "K"),
					new Beat(startTime + gap * 85, "K"), new Beat(startTime + gap * 88, "L"),
					new Beat(startTime + gap * 90, "K"), new Beat(startTime + gap * 94, "D"),
					new Beat(startTime + gap * 98, "L"), new Beat(startTime + gap * 100, "K"),
					new Beat(startTime + gap * 104, "D"), new Beat(startTime + gap * 113, "S"),
					new Beat(startTime + gap * 117, "L"), new Beat(startTime + gap * 118, "D"),
					new Beat(startTime + gap * 122, "K"), new Beat(startTime + gap * 124, "S"),
					new Beat(startTime + gap * 128, "D"), new Beat(startTime + gap * 132, "D"),
					new Beat(startTime + gap * 135, "K"), new Beat(startTime + gap * 140, "S"),
					new Beat(startTime + gap * 143, "D"), new Beat(startTime + gap * 145, "K"),
					new Beat(startTime + gap * 147, "L"), new Beat(startTime + gap * 150, "K"),
					new Beat(startTime + gap * 154, "D"), new Beat(startTime + gap * 157, "S"),
					new Beat(startTime + gap * 160, "D"), new Beat(startTime + gap * 172, "L"),
					new Beat(startTime + gap * 174, "K"), new Beat(startTime + gap * 176, "L"),
					new Beat(startTime + gap * 179, "K"), new Beat(startTime + gap * 181, "L"),
					new Beat(startTime + gap * 185, "K"), new Beat(startTime + gap * 188, "S"),
					new Beat(startTime + gap * 191, "D"), new Beat(startTime + gap * 196, "S"),
					new Beat(startTime + gap * 200, "D"), new Beat(startTime + gap * 202, "K"),
					new Beat(startTime + gap * 205, "L"), new Beat(startTime + gap * 209, "K"),
					new Beat(startTime + gap * 212, "D"), new Beat(startTime + gap * 216, "S"),
					new Beat(startTime + gap * 224, "S"), new Beat(startTime + gap * 227, "D"),
					new Beat(startTime + gap * 231, "K"), new Beat(startTime + gap * 234, "L"),
					new Beat(startTime + gap * 236, "K"), new Beat(startTime + gap * 240, "D"),
					new Beat(startTime + gap * 243, "S"), new Beat(startTime + gap * 251, "K"),
					new Beat(startTime + gap * 255, "L"), new Beat(startTime + gap * 258, "D"),
					new Beat(startTime + gap * 262, "S"), new Beat(startTime + gap * 264, "K"),
					new Beat(startTime + gap * 267, "L"), new Beat(startTime + gap * 271, "D"),
					new Beat(startTime + gap * 280, "K"), new Beat(startTime + gap * 283, "L"),
					new Beat(startTime + gap * 286, "K"), new Beat(startTime + gap * 288, "L"),
					new Beat(startTime + gap * 292, "L"), new Beat(startTime + gap * 307, "S"),
					new Beat(startTime + gap * 310, "D"), new Beat(startTime + gap * 314, "K"),
					new Beat(startTime + gap * 317, "D"), new Beat(startTime + gap * 322, "S"),
					new Beat(startTime + gap * 325, "L"), new Beat(startTime + gap * 331, "K"),
					new Beat(startTime + gap * 335, "D"), new Beat(startTime + gap * 350, "S"),
					new Beat(startTime + gap * 363, "L"), new Beat(startTime + gap * 372, "K"),
					new Beat(startTime + gap * 373, "L"), new Beat(startTime + gap * 377, "K"),
					new Beat(startTime + gap * 383, "D"), new Beat(startTime + gap * 386, "S"),
					new Beat(startTime + gap * 388, "D"), new Beat(startTime + gap * 390, "D"),
					new Beat(startTime + gap * 394, "K"), new Beat(startTime + gap * 398, "K"),
					new Beat(startTime + gap * 401, "L"), new Beat(startTime + gap * 403, "K"),
					new Beat(startTime + gap * 407, "K"), new Beat(startTime + gap * 411, "D"),
					new Beat(startTime + gap * 414, "S"), new Beat(startTime + gap * 418, "L"),
					new Beat(startTime + gap * 422, "K"), new Beat(startTime + gap * 425, "L"),
					new Beat(startTime + gap * 429, "K"), new Beat(startTime + gap * 431, "D"),
					new Beat(startTime + gap * 434, "S"), new Beat(startTime + gap * 438, "D"),
					new Beat(startTime + gap * 443, "L"), new Beat(startTime + gap * 445, "K"),
					new Beat(startTime + gap * 450, "L"), new Beat(startTime + gap * 454, "K"),
					new Beat(startTime + gap * 457, "L"), new Beat(startTime + gap * 460, "K"),
					new Beat(startTime + gap * 464, "D"), new Beat(startTime + gap * 467, "S"),
					new Beat(startTime + gap * 471, "D"), new Beat(startTime + gap * 474, "K"),
					new Beat(startTime + gap * 520, "end"),
					// "end"노트는 +50
			};
		} else if (titleName.equals("All I Want for Christmas Is You") && difficulty.equals("Hard") && line == 4) {
			int startTime = 0;
			int gap = 114; /* 박자 계산 */
			beats = new Beat[] { new Beat(startTime + gap * 2, "L"), new Beat(startTime + gap * 5, "K"),
					new Beat(startTime + gap * 8, "D"), new Beat(startTime + gap * 12, "S"),
					new Beat(startTime + gap * 15, "D"), new Beat(startTime + gap * 19, "K"),
					new Beat(startTime + gap * 22, "D"), new Beat(startTime + gap * 25, "S"),
					new Beat(startTime + gap * 29, "L"), new Beat(startTime + gap * 33, "K"),
					new Beat(startTime + gap * 36, "L"), new Beat(startTime + gap * 39, "K"),
					new Beat(startTime + gap * 42, "L"), new Beat(startTime + gap * 46, "K"),
					new Beat(startTime + gap * 49, "D"), new Beat(startTime + gap * 53, "S"),
					new Beat(startTime + gap * 54, "L"), new Beat(startTime + gap * 55, "D"),
					new Beat(startTime + gap * 56, "K"), new Beat(startTime + gap * 61, "K"),
					new Beat(startTime + gap * 65, "L"), new Beat(startTime + gap * 68, "K"),
					new Beat(startTime + gap * 70, "L"), new Beat(startTime + gap * 73, "K"),
					new Beat(startTime + gap * 77, "D"), new Beat(startTime + gap * 80, "S"),
					new Beat(startTime + gap * 86, "K"), new Beat(startTime + gap * 89, "K"),
					new Beat(startTime + gap * 91, "K"), new Beat(startTime + gap * 95, "D"),
					new Beat(startTime + gap * 97, "S"), new Beat(startTime + gap * 100, "D"),
					new Beat(startTime + gap * 104, "S"), new Beat(startTime + gap * 113, "D"),
					new Beat(startTime + gap * 113, "L"), new Beat(startTime + gap * 116, "S"),
					new Beat(startTime + gap * 117, "K"), new Beat(startTime + gap * 120, "D"),
					new Beat(startTime + gap * 120, "L"), new Beat(startTime + gap * 124, "S"),
					new Beat(startTime + gap * 124, "K"), new Beat(startTime + gap * 127, "D"),
					new Beat(startTime + gap * 127, "L"), new Beat(startTime + gap * 130, "S"),
					new Beat(startTime + gap * 130, "K"), new Beat(startTime + gap * 134, "D"),
					new Beat(startTime + gap * 134, "L"), new Beat(startTime + gap * 137, "S"),
					new Beat(startTime + gap * 137, "K"), new Beat(startTime + gap * 141, "L"),
					new Beat(startTime + gap * 144, "K"), new Beat(startTime + gap * 146, "L"),
					new Beat(startTime + gap * 149, "K"), new Beat(startTime + gap * 153, "D"),
					new Beat(startTime + gap * 157, "S"), new Beat(startTime + gap * 160, "D"),
					new Beat(startTime + gap * 172, "L"), new Beat(startTime + gap * 174, "K"),
					new Beat(startTime + gap * 176, "L"), new Beat(startTime + gap * 179, "K"),
					new Beat(startTime + gap * 181, "L"), new Beat(startTime + gap * 184, "K"),
					new Beat(startTime + gap * 188, "D"), new Beat(startTime + gap * 190, "S"),
					new Beat(startTime + gap * 195, "D"), new Beat(startTime + gap * 199, "K"),
					new Beat(startTime + gap * 201, "L"), new Beat(startTime + gap * 205, "K"),
					new Beat(startTime + gap * 209, "S"), new Beat(startTime + gap * 211, "D"),
					new Beat(startTime + gap * 215, "S"), new Beat(startTime + gap * 224, "S"),
					new Beat(startTime + gap * 224, "L"), new Beat(startTime + gap * 227, "D"),
					new Beat(startTime + gap * 228, "K"), new Beat(startTime + gap * 231, "L"),
					new Beat(startTime + gap * 234, "K"), new Beat(startTime + gap * 236, "S"),
					new Beat(startTime + gap * 239, "D"), new Beat(startTime + gap * 243, "S"),
					new Beat(startTime + gap * 251, "L"), new Beat(startTime + gap * 255, "D"),
					new Beat(startTime + gap * 255, "K"), new Beat(startTime + gap * 259, "L"),
					new Beat(startTime + gap * 262, "K"), new Beat(startTime + gap * 263, "S"),
					new Beat(startTime + gap * 267, "D"), new Beat(startTime + gap * 268, "K"),
					new Beat(startTime + gap * 272, "L"), new Beat(startTime + gap * 279, "K"),
					new Beat(startTime + gap * 283, "L"), new Beat(startTime + gap * 286, "K"),
					new Beat(startTime + gap * 288, "D"), new Beat(startTime + gap * 292, "S"),
					new Beat(startTime + gap * 292, "L"), new Beat(startTime + gap * 307, "L"),
					new Beat(startTime + gap * 310, "K"), new Beat(startTime + gap * 314, "D"),
					new Beat(startTime + gap * 317, "S"), new Beat(startTime + gap * 321, "D"),
					new Beat(startTime + gap * 321, "K"), new Beat(startTime + gap * 324, "D"),
					new Beat(startTime + gap * 324, "L"), new Beat(startTime + gap * 332, "D"),
					new Beat(startTime + gap * 332, "K"), new Beat(startTime + gap * 335, "S"),
					new Beat(startTime + gap * 335, "K"), new Beat(startTime + gap * 349, "D"),
					new Beat(startTime + gap * 349, "K"), new Beat(startTime + gap * 363, "S"),
					new Beat(startTime + gap * 363, "L"), new Beat(startTime + gap * 372, "D"),
					new Beat(startTime + gap * 372, "K"), new Beat(startTime + gap * 374, "S"),
					new Beat(startTime + gap * 375, "L"), new Beat(startTime + gap * 377, "D"),
					new Beat(startTime + gap * 377, "K"), new Beat(startTime + gap * 382, "K"),
					new Beat(startTime + gap * 386, "D"), new Beat(startTime + gap * 387, "D"),
					new Beat(startTime + gap * 390, "K"), new Beat(startTime + gap * 392, "S"),
					new Beat(startTime + gap * 395, "L"), new Beat(startTime + gap * 397, "K"),
					new Beat(startTime + gap * 401, "K"), new Beat(startTime + gap * 402, "D"),
					new Beat(startTime + gap * 406, "K"), new Beat(startTime + gap * 410, "S"),
					new Beat(startTime + gap * 413, "K"), new Beat(startTime + gap * 417, "L"),
					new Beat(startTime + gap * 421, "K"), new Beat(startTime + gap * 423, "D"),
					new Beat(startTime + gap * 427, "S"), new Beat(startTime + gap * 431, "L"),
					new Beat(startTime + gap * 435, "K"), new Beat(startTime + gap * 438, "D"),
					new Beat(startTime + gap * 443, "L"), new Beat(startTime + gap * 446, "K"),
					new Beat(startTime + gap * 450, "S"), new Beat(startTime + gap * 453, "D"),
					new Beat(startTime + gap * 456, "K"), new Beat(startTime + gap * 459, "L"),
					new Beat(startTime + gap * 463, "K"), new Beat(startTime + gap * 467, "D"),
					new Beat(startTime + gap * 470, "S"), new Beat(startTime + gap * 474, "D"),
					new Beat(startTime + gap * 478, "K"), new Beat(startTime + gap * 479, "L"),
					new Beat(startTime + gap * 483, "K"), new Beat(startTime + gap * 486, "D"),
					new Beat(startTime + gap * 490, "S"), new Beat(startTime + gap * 493, "D"),
					new Beat(startTime + gap * 530, "end"),

			};
		} else if (titleName.equals("All I Want for Christmas Is You") && difficulty.equals("Easy") && line == 6) {
			int startTime = 0;
			int gap = 114; /* 박자 계산 */
			beats = new Beat[] { new Beat(startTime + gap * 2, "F"), new Beat(startTime + gap * 5, "K"),
					new Beat(startTime + gap * 9, "D"), new Beat(startTime + gap * 12, "L"),
					new Beat(startTime + gap * 14, "K"), new Beat(startTime + gap * 17, "J"),
					new Beat(startTime + gap * 21, "D"), new Beat(startTime + gap * 24, "S"),
					new Beat(startTime + gap * 29, "L"), new Beat(startTime + gap * 32, "K"),
					new Beat(startTime + gap * 36, "J"), new Beat(startTime + gap * 39, "L"),
					new Beat(startTime + gap * 42, "K"), new Beat(startTime + gap * 45, "D"),
					new Beat(startTime + gap * 49, "S"), new Beat(startTime + gap * 53, "L"),
					new Beat(startTime + gap * 55, "K"), new Beat(startTime + gap * 61, "D"),
					new Beat(startTime + gap * 64, "S"), new Beat(startTime + gap * 67, "F"),
					new Beat(startTime + gap * 69, "D"), new Beat(startTime + gap * 73, "F"),
					new Beat(startTime + gap * 76, "S"), new Beat(startTime + gap * 80, "F"),
					new Beat(startTime + gap * 85, "J"), new Beat(startTime + gap * 88, "K"),
					new Beat(startTime + gap * 91, "L"), new Beat(startTime + gap * 95, "J"),
					new Beat(startTime + gap * 97, "K"), new Beat(startTime + gap * 100, "J"),
					new Beat(startTime + gap * 104, "D"), new Beat(startTime + gap * 113, "L"),
					new Beat(startTime + gap * 116, "K"), new Beat(startTime + gap * 119, "J"),
					new Beat(startTime + gap * 122, "K"), new Beat(startTime + gap * 124, "D"),
					new Beat(startTime + gap * 128, "S"), new Beat(startTime + gap * 132, "F"),
					new Beat(startTime + gap * 135, "J"), new Beat(startTime + gap * 140, "J"),
					new Beat(startTime + gap * 144, "K"), new Beat(startTime + gap * 146, "J"),
					new Beat(startTime + gap * 150, "K"), new Beat(startTime + gap * 153, "D"),
					new Beat(startTime + gap * 156, "F"), new Beat(startTime + gap * 159, "S"),
					new Beat(startTime + gap * 172, "L"), new Beat(startTime + gap * 173, "K"),
					new Beat(startTime + gap * 175, "J"), new Beat(startTime + gap * 178, "K"),
					new Beat(startTime + gap * 180, "S"), new Beat(startTime + gap * 184, "F"),
					new Beat(startTime + gap * 187, "D"), new Beat(startTime + gap * 191, "J"),
					new Beat(startTime + gap * 195, "F"), new Beat(startTime + gap * 199, "K"),
					new Beat(startTime + gap * 201, "L"), new Beat(startTime + gap * 205, "K"),
					new Beat(startTime + gap * 208, "D"), new Beat(startTime + gap * 211, "F"),
					new Beat(startTime + gap * 215, "S"), new Beat(startTime + gap * 223, "L"),
					new Beat(startTime + gap * 227, "K"), new Beat(startTime + gap * 230, "F"),
					new Beat(startTime + gap * 234, "D"), new Beat(startTime + gap * 237, "F"),
					new Beat(startTime + gap * 240, "D"), new Beat(startTime + gap * 243, "S"),
					new Beat(startTime + gap * 251, "J"), new Beat(startTime + gap * 255, "K"),
					new Beat(startTime + gap * 258, "J"), new Beat(startTime + gap * 262, "L"),
					new Beat(startTime + gap * 264, "K"), new Beat(startTime + gap * 267, "J"),
					new Beat(startTime + gap * 271, "F"), new Beat(startTime + gap * 280, "F"),
					new Beat(startTime + gap * 283, "J"), new Beat(startTime + gap * 286, "F"),
					new Beat(startTime + gap * 288, "L"), new Beat(startTime + gap * 292, "K"),
					new Beat(startTime + gap * 307, "L"), new Beat(startTime + gap * 310, "K"),
					new Beat(startTime + gap * 314, "J"), new Beat(startTime + gap * 317, "D"),
					new Beat(startTime + gap * 321, "S"), new Beat(startTime + gap * 324, "F"),
					new Beat(startTime + gap * 331, "L"), new Beat(startTime + gap * 335, "J"),
					new Beat(startTime + gap * 349, "S"), new Beat(startTime + gap * 363, "K"),
					new Beat(startTime + gap * 372, "L"), new Beat(startTime + gap * 373, "K"),
					new Beat(startTime + gap * 377, "D"), new Beat(startTime + gap * 382, "F"),
					new Beat(startTime + gap * 387, "J"), new Beat(startTime + gap * 387, "K"),
					new Beat(startTime + gap * 389, "K"), new Beat(startTime + gap * 391, "K"),
					new Beat(startTime + gap * 394, "S"), new Beat(startTime + gap * 397, "D"),
					new Beat(startTime + gap * 401, "K"), new Beat(startTime + gap * 403, "L"),
					new Beat(startTime + gap * 407, "K"), new Beat(startTime + gap * 410, "D"),
					new Beat(startTime + gap * 413, "S"), new Beat(startTime + gap * 418, "L"),
					new Beat(startTime + gap * 422, "K"), new Beat(startTime + gap * 424, "F"),
					new Beat(startTime + gap * 428, "J"), new Beat(startTime + gap * 430, "F"),
					new Beat(startTime + gap * 433, "K"), new Beat(startTime + gap * 437, "D"),
					new Beat(startTime + gap * 442, "J"), new Beat(startTime + gap * 444, "J"),
					new Beat(startTime + gap * 449, "K"), new Beat(startTime + gap * 452, "J"),
					new Beat(startTime + gap * 453, "D"), new Beat(startTime + gap * 457, "K"),
					new Beat(startTime + gap * 460, "L"), new Beat(startTime + gap * 463, "K"),
					new Beat(startTime + gap * 466, "S"), new Beat(startTime + gap * 470, "D"),
					new Beat(startTime + gap * 474, "F"), new Beat(startTime + gap * 478, "D"),
					new Beat(startTime + gap * 480, "S"), new Beat(startTime + gap * 520, "end"),
					// "end"노트는 +50
			};
		} else if (titleName.equals("All I Want for Christmas Is You") && difficulty.equals("Hard") && line == 6) {
			int startTime = 0;
			int gap = 114; /* 박자 계산 */
			beats = new Beat[] { new Beat(startTime + gap * 2, "S"), new Beat(startTime + gap * 5, "F"),
					new Beat(startTime + gap * 8, "J"), new Beat(startTime + gap * 12, "L"),
					new Beat(startTime + gap * 14, "K"), new Beat(startTime + gap * 17, "J"),
					new Beat(startTime + gap * 21, "K"), new Beat(startTime + gap * 24, "J"),
					new Beat(startTime + gap * 29, "F"), new Beat(startTime + gap * 32, "K"),
					new Beat(startTime + gap * 36, "J"), new Beat(startTime + gap * 39, "K"),
					new Beat(startTime + gap * 42, "D"), new Beat(startTime + gap * 46, "F"),
					new Beat(startTime + gap * 49, "D"), new Beat(startTime + gap * 53, "L"),
					new Beat(startTime + gap * 55, "J"), new Beat(startTime + gap * 61, "F"),
					new Beat(startTime + gap * 64, "D"), new Beat(startTime + gap * 67, "K"),
					new Beat(startTime + gap * 69, "L"), new Beat(startTime + gap * 73, "J"),
					new Beat(startTime + gap * 76, "K"), new Beat(startTime + gap * 80, "J"),
					new Beat(startTime + gap * 85, "F"), new Beat(startTime + gap * 88, "D"),
					new Beat(startTime + gap * 91, "F"), new Beat(startTime + gap * 95, "K"),
					new Beat(startTime + gap * 97, "L"), new Beat(startTime + gap * 101, "J"),
					new Beat(startTime + gap * 104, "K"), new Beat(startTime + gap * 112, "L"),
					new Beat(startTime + gap * 116, "K"), new Beat(startTime + gap * 118, "S"),
					new Beat(startTime + gap * 122, "D"), new Beat(startTime + gap * 126, "F"),
					new Beat(startTime + gap * 129, "K"), new Beat(startTime + gap * 132, "J"),
					new Beat(startTime + gap * 136, "D"), new Beat(startTime + gap * 140, "J"),
					new Beat(startTime + gap * 144, "K"), new Beat(startTime + gap * 146, "L"),
					new Beat(startTime + gap * 149, "K"), new Beat(startTime + gap * 153, "F"),
					new Beat(startTime + gap * 156, "L"), new Beat(startTime + gap * 160, "J"),
					new Beat(startTime + gap * 172, "S"), new Beat(startTime + gap * 174, "D"),
					new Beat(startTime + gap * 176, "F"), new Beat(startTime + gap * 179, "K"),
					new Beat(startTime + gap * 181, "J"), new Beat(startTime + gap * 184, "L"),
					new Beat(startTime + gap * 188, "K"), new Beat(startTime + gap * 191, "J"),
					new Beat(startTime + gap * 196, "F"), new Beat(startTime + gap * 200, "D"),
					new Beat(startTime + gap * 202, "S"), new Beat(startTime + gap * 206, "F"),
					new Beat(startTime + gap * 207, "K"), new Beat(startTime + gap * 209, "L"),
					new Beat(startTime + gap * 212, "J"), new Beat(startTime + gap * 216, "K"),
					new Beat(startTime + gap * 224, "F"), new Beat(startTime + gap * 224, "L"),
					new Beat(startTime + gap * 227, "D"), new Beat(startTime + gap * 227, "K"),
					new Beat(startTime + gap * 231, "F"), new Beat(startTime + gap * 231, "L"),
					new Beat(startTime + gap * 234, "S"), new Beat(startTime + gap * 236, "D"),
					new Beat(startTime + gap * 241, "F"), new Beat(startTime + gap * 243, "S"),
					new Beat(startTime + gap * 252, "S"), new Beat(startTime + gap * 252, "J"),
					new Beat(startTime + gap * 255, "D"), new Beat(startTime + gap * 255, "K"),
					new Beat(startTime + gap * 258, "S"), new Beat(startTime + gap * 258, "J"),
					new Beat(startTime + gap * 262, "K"), new Beat(startTime + gap * 264, "J"),
					new Beat(startTime + gap * 267, "L"), new Beat(startTime + gap * 271, "J"),
					new Beat(startTime + gap * 280, "S"), new Beat(startTime + gap * 280, "K"),
					new Beat(startTime + gap * 283, "D"), new Beat(startTime + gap * 283, "L"),
					new Beat(startTime + gap * 286, "F"), new Beat(startTime + gap * 286, "K"),
					new Beat(startTime + gap * 289, "S"), new Beat(startTime + gap * 289, "L"),
					new Beat(startTime + gap * 293, "F"), new Beat(startTime + gap * 293, "J"),
					new Beat(startTime + gap * 307, "L"), new Beat(startTime + gap * 310, "K"),
					new Beat(startTime + gap * 314, "J"), new Beat(startTime + gap * 318, "L"),
					new Beat(startTime + gap * 321, "S"), new Beat(startTime + gap * 324, "F"),
					new Beat(startTime + gap * 331, "L"), new Beat(startTime + gap * 335, "D"),
					new Beat(startTime + gap * 349, "D"), new Beat(startTime + gap * 349, "K"),
					new Beat(startTime + gap * 363, "F"), new Beat(startTime + gap * 363, "L"),
					new Beat(startTime + gap * 372, "K"), new Beat(startTime + gap * 374, "L"),
					new Beat(startTime + gap * 376, "K"), new Beat(startTime + gap * 382, "J"),
					new Beat(startTime + gap * 385, "K"), new Beat(startTime + gap * 387, "J"),
					new Beat(startTime + gap * 389, "K"), new Beat(startTime + gap * 390, "S"),
					new Beat(startTime + gap * 394, "D"), new Beat(startTime + gap * 397, "F"),
					new Beat(startTime + gap * 401, "F"), new Beat(startTime + gap * 404, "K"),
					new Beat(startTime + gap * 406, "J"), new Beat(startTime + gap * 410, "K"),
					new Beat(startTime + gap * 414, "J"), new Beat(startTime + gap * 418, "L"),
					new Beat(startTime + gap * 421, "D"), new Beat(startTime + gap * 424, "K"),
					new Beat(startTime + gap * 428, "L"), new Beat(startTime + gap * 430, "J"),
					new Beat(startTime + gap * 434, "K"), new Beat(startTime + gap * 437, "F"),
					new Beat(startTime + gap * 443, "L"), new Beat(startTime + gap * 445, "J"),
					new Beat(startTime + gap * 450, "F"), new Beat(startTime + gap * 452, "D"),
					new Beat(startTime + gap * 454, "F"), new Beat(startTime + gap * 459, "K"),
					new Beat(startTime + gap * 462, "L"), new Beat(startTime + gap * 466, "K"),
					new Beat(startTime + gap * 469, "J"), new Beat(startTime + gap * 520, "end"), };
		}
		int i = 0;
		int j = 0;
		gameMusic.start();
		captureTool.start();
		showingResult = false;
		ending = new EndingResult();
		while (i < beats.length && !isInterrupted()) {
			boolean dropped = false;
			if (beats[i].getTime() <= gameMusic.getTime()) {
				Note note = new Note(beats[i].getNoteName(), line, note_Img);
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
			if (itemsendcount.getTime() >= 3000) { // 아이템 보내고 3초뒤에
				itemNotiImg = cloudnothing;
				isSendItem = 0; // 아이콘 지우기
				j = 0;
				isItemOn = false;
			}
			if (itemrecvcount.getTime() >= 5000) { // 아이템 받고 5초뒤에
				attackNotiImg = cloudnothing;
				isRecvItem = 0; // 아이콘 지우기
				itemNotiImg = cloudnothing;
			}
			if (gameMusic.getTime() > 71000) {
				isItemOn = false;
				itemNotiImg = cloudnothing;
				j++;
			} else if (!isItemOn && gameMusic.getTime() > 10000 && Note.score >= 100
					|| !isItemOn && gameMusic.getTime() > 40000 && Note.score >= 500
					|| !isItemOn && gameMusic.getTime() > 70000 && Note.score >= 1000) {
				if (gameMusic.getTime() > 71000)
					break;
				if (j == 0)
					isItemOn = true;
				else
					isItemOn = false;
				if (isSendItem != 0)
					itemNotiImg = cloudnothing;
				else
					itemNotiImg = cloudsendNoti0Img;
				j++;
			}

			if (showingResult) {
				gameMusic.close();
				ending.playBgm();
				ending.takeScore(Note.score);
				ending.calRank();
				ending.writeScore(titleName);
				captureTool.close();
			}
			ending.update();
		}
	}

	public synchronized void sendCapture(ImageIcon capture) {
		try {
			oos = WaitingRoom.oos;
			oos.flush();

			ChatMsg obcm = new ChatMsg(WaitingRoom.user, "570", "CAPTURE");
			obcm.setImg(capture);
			oos.writeObject(capture);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 큐처럼 먼저 떨어지는 노트에 대해서 입력 정확도 검사

	public void judge(String input) {
		for (int i = 0; i < noteList.size(); i++) {
			Note note = noteList.get(i);
			if (input.equals("space")) {
				break;
			} else if (input.equals(note.getNoteType())) {
				judgeEvent(note.judge());
				break;
			}
			// note.getNoteImg()
		}
	}

	public void judgeEvent(String judge) {
		judgeImg = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();

		if (judge.equals("Miss"))
			judgeImg = new ImageIcon(Main.class.getResource("/images/miss.png")).getImage();
		else if (judge.equals("Good"))
			judgeImg = new ImageIcon(Main.class.getResource("/images/good.png")).getImage();
		else if (judge.equals("Great"))
			judgeImg = new ImageIcon(Main.class.getResource("/images/great.png")).getImage();
		else if (judge.equals("Perfect"))
			judgeImg = new ImageIcon(Main.class.getResource("/images/perfect.png")).getImage();

	}

	public synchronized void recvItem() {
		isRecvItem = 2;
		itemNotiImg = attacknoti;
		System.out.println("아이템 받음");
		// attackNotiImg=cloudrecv0Img;
		new Music("attack.mp3", false).start();
		if (cnt2 != 0) {
			itemrecvcount.stop();
			itemrecvcount = new Music("itemcount.mp3", false);
		}
		itemrecvcount.start();
		cnt2++;
		recvItemRunnable runnable = new recvItemRunnable();
		Thread thread = new Thread(runnable);
		thread.setDaemon(true);
		thread.start();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
		}
		new Music("attack close.mp3", false).start();
		runnable.stop();
	}

	public class recvItemRunnable implements Runnable {

		private final AtomicBoolean running = new AtomicBoolean(false);

		public recvItemRunnable() {
		}

		public void stop() {
			running.set(false);
		}

		public void run() {
			int i = 0;
			running.set(true);
			while (running.get()) {
				try {
					if (i % 2 == 0)
						attackNotiImg = cloudrecv0Img;
					else
						attackNotiImg = cloudrecv1Img;
					i++;
					Thread.sleep(100);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}
}