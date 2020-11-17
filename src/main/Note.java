package main;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;


public class Note extends Thread {
	private Image line6_noteImg = new ImageIcon(Main.class.getResource("../images/6line-note.png")).getImage();
	private Image line4_noteImg = new ImageIcon(Main.class.getResource("../images/4line-note.png")).getImage();
	private Image fever_line6_noteImg = new ImageIcon(Main.class.getResource("../images/6line-note.png")).getImage();
	private Image fever_line4_noteImg = new ImageIcon(Main.class.getResource("../images/4line-note.png")).getImage();
	
	private RoomSetting roomSetting = new RoomSetting();
	
	private int x, y = 500 - (1000 / Main.SLEEP_TIME * Main.NOTE_SPEED) * Main.REACH_TIME;	/* Note 생성 후 1초 뒤에 판정 라인에 다다름 */
	private String noteType;
	private String difficulty;
	private boolean proceeded = true;
	
	public String getNoteType() {
		return noteType;
	}
	
	public boolean isProceeded() {
		return proceeded;
	}
	
	/*
	 * 노트를 성공적으로 입력해서 해당 노트가 더 이상 이동하지 않도록 함
	 */
	public void close() {
		proceeded = false;
	}
	
	public Note(String noteType, String difficulty) {
		if (difficulty.equals("Easy")) {
			if (noteType.equals("D")) {
				x = 48;
			}
			else if (noteType.equals("F")) {
				x = 221;
			}
			else if (noteType.equals("J")) {
				x = 392;
			}
			else if (noteType.equals("K")) {
				x = 566;
			}
		}
		else if (difficulty.equals("Hard")) {
			if (noteType.equals("S")) {
				x = 46;
			}
			else if (noteType.equals("D")) {
				x = 160;
			}
			else if (noteType.equals("F")) {
				x = 278;
			}
			else if (noteType.equals("J")) {
				x = 391;
			}
			else if (noteType.equals("K")) {
				x = 506;
			}
			else if (noteType.equals("L")) {
				x = 622;
			}
		}
		this.noteType = noteType;
		this.difficulty = difficulty;
	}
	
	public void screenDraw(Graphics2D g) {
		if (!noteType.equals("Space")) {
			if (difficulty.equals("Easy"))
				g.drawImage(line4_noteImg, x, y, null);
			else if (difficulty.equals("Hard")) {
				g.drawImage(line6_noteImg, x, y, null);
			}
		}
		else {
			// 아이템 시각 효과 그리기
		}
	}
	
	public void drop() {
		y += Main.NOTE_SPEED;
		if (y > 560) {	/* 판정바를 지나친 경우 */
			System.out.println("Miss");
			close();
		}
	}
	
	@Override
	public void run() {
		try {
			while (true) {	/* 1초에 100번 실행 = 1초에 700px만큼 note가 떨어짐 */
				drop();
				if (proceeded) {
					Thread.sleep(Main.SLEEP_TIME);
				}
				else {
					interrupt();
					break;
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public String judge() {
		if (y >= 545) {
			System.out.println("Good");
			close();
			return "Good";
		}
		else if (y >= 530) {
			System.out.println("Great");
			close();
			return "Great";
		}
		else if (y >= 470) {
			System.out.println("Perfect");
			close();
			return "Perfect";
		}
		else if (y >= 440) {
			System.out.println("Great");
			close();
			return "Great";
		}
		else if (y >= 400) {
			System.out.println("Good");
			close();
			return "Good";
		}
		return "None";
	}
	
	public int getY() {
		return y;
	}
}
