package dynamic_beat_13;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Note extends Thread {
	private Image noteImg = new ImageIcon(Main.class.getResource("../images/note.png")).getImage();
	private int x, y = 580 - 1000 / Main.SLEEP_TIME * Main.NOTE_SPEED;	/* Note 생성 후 1초 뒤에 판정 라인에 다다름 */
	private String noteType;
	
	public Note(int x, String noteType) {
		this.x = x;
		this.noteType = noteType;
	}
	
	public void screenDraw(Graphics2D g) {
		if (noteType.equals("short")) {
			g.drawImage(noteImg, x, y, null);
		}
		else if (noteType.equals("long")) {
			g.drawImage(noteImg, x, y, null);
			g.drawImage(noteImg, x + 100, y, null);
		}
	}
	
	public void drop() {
		y += Main.NOTE_SPEED;
	}
	
	@Override
	public void run() {
		try {
			while (true) {	/* 1초에 100번 실행 = 1초에 700px만큼 note가 떨어짐 */
				drop();
				Thread.sleep(Main.SLEEP_TIME);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
