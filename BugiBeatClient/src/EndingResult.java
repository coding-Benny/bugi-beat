import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EndingResult {
	private String musicTitle;
	private int score;
	private String rank;
	private String msg = null;
	private boolean step1On;
	private boolean step2On;
	private boolean step3On;
	private boolean step4On;
	private boolean nextgame;
	private int angle;
	private Socket socket; // 연결소켓
	private ObjectOutputStream oos;

	// BGM
	private Music endingBgm;

	boolean isPlayed = false;

	public EndingResult() {
		step1On = false;
		step2On = false;
		step3On = false;
		step4On = false;
		nextgame = false;
		angle = 155;

		endingBgm = new Music("resultBgm.mp3", false);
	}

	public void calRank() { // 점수 랭크 기준
		if (score <= 200)
			rank = "C";
		else if (score <= 700)
			rank = "B";
		else if (score <= 1500)
			rank = "A";
		else if (score <= 3000)
			rank = "S";
	}

	public void writeScore(String musicTitle) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
		String dateStr = sdf.format(cal.getTime());
		String titleStr = musicTitle;
		String scoreStr = String.valueOf(score);
		
		msg = dateStr + "#" + titleStr + "#" + scoreStr;
		SendMessage(msg);	// 서버에 460 Game over 시 게임 기록 전송

	}

	public void update() {
		angle += 3;

		if (endingBgm.getTime() >= 6500) {
			nextgame = true;
		}
	}

	public void draw(Graphics2D g) {

		// result
		g.setColor(new Color(0, 0, 0, 150));
		g.fillRect(230, 200, 350, 220);

		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// 1
		if (endingBgm.getTime() >= 3000 || step1On == true) {
			step1On = true;
			g.setColor(Color.YELLOW);
			g.setFont(new Font("산돌수필B", Font.PLAIN, 60));
			g.drawString("Game Over", 300, 260);
		}

		// 2
		if (endingBgm.getTime() >= 4000 || step2On == true) {
			step2On = true;
			g.setColor(Color.ORANGE);
			g.setFont(new Font("산돌수필B", Font.PLAIN, 32));
			g.drawString("Score: " + String.valueOf(score), 350, 300);
		}

		// 3
		if (endingBgm.getTime() >= 5500 || step3On == true) {
			step3On = true;
			g.setColor(Color.ORANGE);
			g.setFont(new Font("산돌수필B", Font.PLAIN, 120));
			g.drawString(rank, 385, 395);
			g.setColor(Color.WHITE);
			g.setFont(new Font("산돌수필B", Font.PLAIN, 110));
			g.drawString(rank, 380, 390);
		}

		// 4
		if (endingBgm.getTime() >= 6500 || step4On == true) {
			step4On = true;
			if (angle >= 175)
				angle = 0;
			g.setColor(Color.BLACK);
			g.setFont(new Font("산돌수필B", Font.PLAIN, 27));
			g.drawString("- Press ENTER -", 332, 452);
			g.setColor(Color.WHITE);
			g.setFont(new Font("산돌수필B", Font.PLAIN, 26));
			g.drawString("- Press ENTER -", 330, 450);
		}

	}
	
	public synchronized void SendMessage(String msg) {
		try {
			socket = WaitingRoom.socket;

			oos = WaitingRoom.oos;
			oos.flush();
			ChatMsg obcm = new ChatMsg(WaitingRoom.user, "460", msg);
			try {
				oos.writeObject(obcm);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void playBgm() {
		isPlayed = true;
		endingBgm.start();
	}

	public void closeBgm() {
		if (!isPlayed)
			return;
		endingBgm.close();
	}

	public boolean getNextGame() {
		return nextgame;
	}

	public void takeMusicTitle(String title) {
		musicTitle = title;
	}

	public void takeScore(int score) {
		this.score = score;
	}
}