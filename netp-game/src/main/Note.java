package main;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;


public class Note extends Thread {
	private Image noteImg = new ImageIcon(Main.class.getResource("../images/note.png")).getImage();
	private int x, y = 580 - (1000 / Main.SLEEP_TIME * Main.NOTE_SPEED) * Main.REACH_TIME;	/* Note 생성 후 1초 뒤에 판정 라인에 다다름 */
	private String noteType;
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
	
	public Note(String noteType) {
		if (noteType.equals("S")) {
			x = 228;
		}
		else if (noteType.equals("D")) {
			x = 346;
		}
		else if (noteType.equals("F")) {
			x = 464;
		}
		else if (noteType.equals("Space")) {
			x = 582;
		}
		else if (noteType.equals("J")) {
			x = 824;
		}
		else if (noteType.equals("K")) {
			x = 942;
		}
		else if (noteType.equals("L")) {
			x = 1096;
		}
		this.noteType = noteType;
	}
	
	public void screenDraw(Graphics2D g) {
		if (!noteType.equals("Space")) {
			g.drawImage(noteImg, x, y, null);
		}
		else {
			g.drawImage(noteImg, x, y, null);
			g.drawImage(noteImg, x + 100, y, null);
		}
	}
	
	public void drop() {
		y += Main.NOTE_SPEED;
		if (y > 620) {	/* 판정바를 지나친 경우 */
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
		if (y >= 613) {
			System.out.println("Late");
			close();
			return "Late";
		}
		else if (y >= 600) {
			System.out.println("Good");
			close();
			return "Good";
		}
		else if (y >= 587) {
			System.out.println("Great");
			close();
			return "Great";
		}
		else if (y >= 573) {
			System.out.println("Perfect");
			close();
			return "Perfect";
		}
		else if (y >= 565) {
			System.out.println("Great");
			close();
			return "Great";
		}
		else if (y >= 550) {
			System.out.println("Good");
			close();
			return "Good";
		}
		else if (y >= 535) {
			System.out.println("Early");
			close();
			return "Early";
		}
		return "None";
	}
	
	public int getY() {
		return y;
	}
}
