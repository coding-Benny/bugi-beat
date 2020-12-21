import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Note extends Thread {
	private Image note_Img;
	private Image line6_noteImg = new ImageIcon(Main.class.getResource("/images/6line-note.png")).getImage();
	private Image line4_noteImg = new ImageIcon(Main.class.getResource("/images/4line-note.png")).getImage();
	private Image fever_line6_noteImg = new ImageIcon(Main.class.getResource("/images/6line-note.png")).getImage();
	private Image fever_line4_noteImg = new ImageIcon(Main.class.getResource("/images/4line-note.png")).getImage();
	private Image line6_bg_Img = new ImageIcon(Main.class.getResource("/images/6line-bg.png")).getImage();
	private Image fever_line6_bg_Img = new ImageIcon(Main.class.getResource("/images/fever-6line-bg.png")).getImage();
	private Image line4_bg_Img = new ImageIcon(Main.class.getResource("/images/4line-bg.png")).getImage();
	private Image fever_line4_bg_Img = new ImageIcon(Main.class.getResource("/images/fever-4line-bg.png")).getImage();
	private Image cloudsendNoti0Img = new ImageIcon(Main.class.getResource("/images/cloouds-send0.png")).getImage();
	private Image cloudsendNoti1Img = new ImageIcon(Main.class.getResource("/images/cloouds-send1.png")).getImage();
	private Image nothing = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();

	public static boolean isFever = false;
	public static int fever = 0;
	public static int score = 0;
	public static int combo = 0;
	public static int maxCombo = 0;
	public static int life = 10;

	private int x,
			y = 560 - (1000 / Main.SLEEP_TIME * Main.NOTE_SPEED) * Main.REACH_TIME; /* Note 생성 후 1초 뒤에 판정 라인에 다다름 */
	private String noteType;
	private int line;
	private boolean proceeded = true;

	public String getNoteType() {
		return noteType;
	}

	public Image getNoteImg() {
		return note_Img;
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

	public Note(String noteType, int line, Image img) {
		if (line == 4) {
			if (noteType.equals("S")) {
				x = 48;
			} else if (noteType.equals("D")) {
				x = 221;
			} else if (noteType.equals("K")) {
				x = 392;
			} else if (noteType.equals("L")) {
				x = 566;
			} else if (noteType.equals("end")) {
				x = 900; // 안보이게
				Game.showingResult = true;
			}
		} else if (line == 6) {
			if (noteType.equals("S")) {
				x = 46;
			} else if (noteType.equals("D")) {
				x = 160;
			} else if (noteType.equals("F")) {
				x = 278;
			} else if (noteType.equals("J")) {
				x = 391;
			} else if (noteType.equals("K")) {
				x = 506;
			} else if (noteType.equals("L")) {
				x = 622;
			} else if (noteType.equals("end")) {
				x = 900; // 안보이게
				Game.showingResult = true;
			}
		}
		this.noteType = noteType;
		this.line = line;
		this.note_Img = img;
	}

	public void screenDraw(Graphics2D g) {
		if (line == 6 && !isFever) {
			Game.gameScreenBg = line6_bg_Img;
			note_Img = line6_noteImg;
		} else if (line == 6 && isFever) {
			Game.gameScreenBg = fever_line6_bg_Img;
			note_Img = fever_line6_noteImg;
		} else if (line == 4 && !isFever) {
			Game.gameScreenBg = line4_bg_Img;
			note_Img = line4_noteImg;
		} else if (line == 4 && isFever) {
			Game.gameScreenBg = fever_line4_bg_Img;
			note_Img = fever_line4_noteImg;
		}
		if (!noteType.equals("Space")) {
			if (line == 4)
				g.drawImage(note_Img, x, y, null);
			else if (line == 6) {
				g.drawImage(note_Img, x, y, null);
			}
		} else { // 아이템 보내기
			// 아이템 시각 효과 그리기
		}
	}

	public void drop() {
		y += Main.NOTE_SPEED;
		if (y > 560) { /* 판정바를 지나친 경우 */
			combo = 0;
			if (!noteType.equals("end")) {
				life--;
				isFever = false;
				fever = 0;
			}

			if (life < 0) { // 게임오버
				life = 0;
				Game.showingResult = true;
			}
			close();
		}
	}

	@Override
	public void run() {
		try {
			while (true) { /* 1초에 100번 실행 = 1초에 700px만큼 note가 떨어짐 */
				drop();
				GamePanel.lifeBar.setValue(life);
				GamePanel.feverBar.setValue(fever);

				if (combo >= maxCombo) // 맥스콤보 갱신
					maxCombo = combo;
				if (combo != 0 && combo % 10 == 0) { // 10콤보마다 life 회복
					if (life >= 10)
						life = 10;
					else
						life++;
				}
				if (fever != 0 && fever % 20 == 0) { // 20배수마다 피버타임 off
					isFever = false;
					fever = 0;
				} else if (fever != 0 && fever % 10 == 0) { // 10배수마다 피버타임 on
					isFever = true;
				}
/*
				if (!Game.isItemOn) {
					Game.cloudNotiImg = nothing;
				} else {
					if(cnt%11==0)
						Game.cloudNotiImg = cloudsendNoti0Img;
					else
						Game.cloudNotiImg = cloudsendNoti1Img;
				}
				*/
				
				if (proceeded) {
					Thread.sleep(Main.SLEEP_TIME);
				} else {
					interrupt();
					break;
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public String judge() { // 500을 기준
		if (y >= 525) { // Good
			if (!isFever)
				score += 5;
			else
				score += 10;
			combo++;
			fever++;
			close();
			return "Good";
		} else if (y >= 510) { // Great
			if (!isFever)
				score += 10;
			else
				score += 20;
			combo++;
			fever++;
			close();
			return "Great";
		} else if (y >= 495) { // Perfect
			if (!isFever)
				score += 20;
			else
				score += 40;
			combo++;
			fever++;
			close();
			return "Perfect";
		} else if (y >= 480) { // Great
			if (!isFever)
				score += 10;
			else
				score += 20;
			combo++;
			fever++;
			close();
			return "Great";
		} else if (y >= 465) { // Good
			if (!isFever)
				score += 5;
			else
				score += 10;
			combo++;
			fever++;
			close();
			return "Good";
		}
		return "None";
	}

	public int getY() {
		return y;
	}
}