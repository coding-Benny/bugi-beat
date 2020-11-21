package main;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;


public class Note extends Thread {
	private Image note_Img;
	private Image line6_noteImg = new ImageIcon(Main.class.getResource("../images/6line-note.png")).getImage();
	private Image line4_noteImg = new ImageIcon(Main.class.getResource("../images/4line-note.png")).getImage();
	private Image fever_line6_noteImg = new ImageIcon(Main.class.getResource("../images/6line-note.png")).getImage();
	private Image fever_line4_noteImg = new ImageIcon(Main.class.getResource("../images/4line-note.png")).getImage();
	private ImageIcon line6_bg_Img = new ImageIcon(Main.class.getResource("../images/6line-bg.png"));
	private ImageIcon fever_line6_bg_Img = new ImageIcon(Main.class.getResource("../images/fever-6line-bg.png"));
	private ImageIcon line4_bg_Img = new ImageIcon(Main.class.getResource("../images/4line-bg.png"));
	private ImageIcon fever_line4_bg_Img = new ImageIcon(Main.class.getResource("../images/fever-4line-bg.png"));

	private RoomSetting roomSetting = new RoomSetting();
	
	private int x, y = 560 - (1000 / Main.SLEEP_TIME * Main.NOTE_SPEED) * Main.REACH_TIME;	/* Note 생성 후 1초 뒤에 판정 라인에 다다름 */
	private String noteType;
	private int line;
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
	
	public Note(String noteType, int line, Image img) {
		if (line==4) {
			if (noteType.equals("S")) {
				x = 48;
			}
			else if (noteType.equals("D")) {
				x = 221;
			}
			else if (noteType.equals("K")) {
				x = 392;
			}
			else if (noteType.equals("L")) {
				x = 566;
			}
		}
		else if (line==6) {
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
		this.line = line;
		this.note_Img = img;
	}
	
	public void screenDraw(Graphics2D g) {
		if (!noteType.equals("Space")) {
			if (!noteType.equals("Space")) {
				if (line==4)
					g.drawImage(note_Img, x, y, null);
				else if (line==6) {
					g.drawImage(note_Img, x, y, null);
				}
			}
		}
		else {
			// 아이템 시각 효과 그리기
		}
	}
	
	public void drop() {
		y += Main.NOTE_SPEED;
		if (y > 560) {	/* 판정바를 지나친 경우 */
			Main.combo=0;
			Main.life--;
			Main.isFever=false;
			if(Main.fever>1)
				Main.fever -= 5;
			else
				Main.fever = 0;
			
			if(Main.life<0) //게임오버 나중에 구현
				Main.life=0;
				
			System.out.println(Main.life + " " +Main.fever);
			close();
		}
	}
	
	@Override
	public void run() {
		try {
			while (true) {	/* 1초에 100번 실행 = 1초에 700px만큼 note가 떨어짐 */
				drop();
				GamePanel.lifeBar.setValue(Main.life);
				GamePanel.feverBar.setValue(Main.fever);
				if(line==6 && !Main.isFever) {
					Game.gameScreenBg = line6_bg_Img.getImage();
					note_Img = line6_noteImg;
				}
				else if(line==6 && Main.isFever) {
					Game.gameScreenBg = fever_line6_bg_Img.getImage();
					note_Img = fever_line6_noteImg;
				}
				else if(line==4 && !Main.isFever) {
					Game.gameScreenBg = line4_bg_Img.getImage();
					note_Img = line4_noteImg;
				}
				else if(line==4 && Main.isFever) {
					Game.gameScreenBg = fever_line4_bg_Img.getImage();
					note_Img = fever_line4_noteImg;
				}
				
				if(Main.combo>=Main.maxCombo)  //맥스콤보 갱신
					Main.maxCombo=Main.combo;
				if(Main.combo!=0 && Main.combo%10==0)  //10콤보시 life 회복
					Main.life++;
				if(Main.fever!=0 && Main.fever%10==0)  //10배수마다 피버타임 on
					Main.isFever=true;
				else if(Main.fever!=0 && Main.fever%20==0) {  //20배수에 피버타임 off
					Main.isFever=false;
					Main.fever=0;
				}
				
				GamePanel.scoreLabel.setText(Main.score+"");
				GamePanel.maxComboLabel.setText(Main.maxCombo+"");
				
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

	public String judge() {  //500을 기준
		if (y >= 525) {  //Good
			if(!Main.isFever)
				Main.score += 5;
			else
				Main.score += 10;
			Main.combo++;
			Main.fever++;
			close();
			return "Good";
		}
		else if (y >= 510) {  //Great
			if(!Main.isFever)
				Main.score += 10;
			else
				Main.score += 20;
			Main.combo++;
			Main.fever++;
			close();
			return "Great";
		}
		else if (y >= 495) {  //Perfect
			if(!Main.isFever)
				Main.score += 20;
			else
				Main.score += 40;
			Main.combo++;
			Main.fever++;
			close();
			return "Perfect";
		}
		else if (y >= 480) {  //Great
			if(!Main.isFever)
				Main.score += 10;
			else
				Main.score += 20;
			Main.combo++;
			Main.fever++;
			close();
			return "Great";
		}
		else if (y >= 465) {  //Good
			if(!Main.isFever)
				Main.score += 5;
			else
				Main.score += 10;
			Main.combo++;
			Main.fever++;
			close();
			return "Good";
		}
		return "None";
	}
	
	public int getY() {
		return y;
	}
}
