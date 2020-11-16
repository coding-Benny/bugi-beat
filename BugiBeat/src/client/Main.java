package client;

import java.awt.*;
import java.io.File;

public class Main {
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;
	public static final int NOTE_SPEED = 3;
	public static final int SLEEP_TIME = 10;
	public static final int REACH_TIME = 2;	/* 노트 생성 후 판정 바에 도달하는데 걸리는 시간 */
	public static final boolean SOUND_EFFECT = true;  // 기본 on
	
	public static void main(String[] args) {
		new GameRoom();
	}

}
