import java.awt.AWTException;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class CaptureTool extends Thread {
	private Calendar cal = Calendar.getInstance();
	private SimpleDateFormat cur;
	private BufferedImage capturedImg = null;
	private BufferedImage screenshot = null;
	private Component frame;
	private Rectangle rect;
	private int num = 0;
	private String fileName;
	private String date;
	private boolean isPlaying;
	private ObjectOutputStream oos;
	
	public CaptureTool(Component frame) {
		this.frame = frame;
		this.isPlaying = true;
		rect = new Rectangle(frame.getLocationOnScreen().x + 45, frame.getLocationOnScreen().y + 110,
				frame.getWidth() - 80, frame.getHeight() - 210);
		cur = new SimpleDateFormat("yyyyMMddhhmmss");
		date = cur.format(cal.getTime());
	}
	
	public synchronized BufferedImage capture(String filePath) {
		try {
			capturedImg = new Robot().createScreenCapture(rect);
			ImageIO.write(capturedImg, "png", new File("./src/captures/" + filePath + ".png"));
		} catch (AWTException | IOException e) {
			e.printStackTrace();
		}
		return capturedImg;
	}
	
	/*
	 * 스레드를 상속받은 경우 무조건 구현해야 하는 함수
	 */
	@Override
	public void run() {
		do {
			fileName = WaitingRoom.user + "#" + date + num;
			screenshot = capture(fileName);
			num++;
			try {
				oos = WaitingRoom.oos;
				oos.flush();
				ChatMsg obcm = new ChatMsg(WaitingRoom.user, "570", fileName);
				obcm.setImg(new ImageIcon(screenshot));
				oos.writeObject(obcm);
				Thread.sleep(1000);
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
		} while (isPlaying);
	}
	
	public void close() {
		isPlaying = false;
		try {
			this.interrupt();	/* 해당 스레드를 중지 상태로 만듦 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
