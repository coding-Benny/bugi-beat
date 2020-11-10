package dynamic_beat_16;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class Music extends Thread {
	private Player player;
	private boolean isLoop;
	private File file;
	private FileInputStream fis;
	private BufferedInputStream bis;
	
	public Music(String name, boolean isLoop) {
		try {
			this.isLoop = isLoop;
			file = new File(Main.class.getResource("../music/" + name).toURI());
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			player = new Player(bis);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * 실행되고 있는 음악의 위치를 알려줌(0.001초 단위까지)
	 * 음악에 맞춰 노트를 떨어뜨릴 때 이 함수를 이용해서 시간을 분석함
	 */
	public int getTime() {
		if (player == null)
			return 0;
		return player.getPosition();
	}
	
	/*
	 * 음악 실행 여부에 상관없이 항상 종료
	 * 실행 중 다른 곡 play 원할 때 안정적인 종료 지원
	 */
	public void close() {
		isLoop = false;
		player.close();
		this.interrupt();	/* 해당 스레드를 중지 상태로 만듦 */
	}
	
	/*
	 * 스레드를 상속받은 경우 무조건 구현해야 하는 함수
	 */
	@Override
	public void run() {
		try {
			do {
				player.play();
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				player = new Player(bis);
			} while (isLoop);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
