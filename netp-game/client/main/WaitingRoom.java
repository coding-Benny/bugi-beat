package main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.io.*;
import java.net.Socket;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


import javax.swing.*;


public class WaitingRoom extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private String UserName;
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
	private Socket socket; // 연결소켓

	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	private Image background;

	private Image screenImage;
	private Graphics screenGraphic;
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/bar.png")));
	private ImageIcon exitBtnEnteredImg = new ImageIcon(Main.class.getResource("../images/exit1.png"));
	private ImageIcon exitBtnImg = new ImageIcon(Main.class.getResource("../images/exit0.png"));
	private ImageIcon bg1Img = new ImageIcon(Main.class.getResource("../images/anteroom1-bg.png"));
	private ImageIcon bg2Img = new ImageIcon(Main.class.getResource("../images/anteroom2-bg.png"));
	private Image accessListPanel_bg= new ImageIcon(Main.class.getResource("../images/accesslist-bg.png")).getImage();
	private Image chatList_bg= new ImageIcon(Main.class.getResource("../images/4line-push.png")).getImage();
	
	private RoomSetting roomSetPanel = new RoomSetting();
	private JButton exitBtn = new JButton(exitBtnImg);
	private JScrollPane accessListPanel = new JScrollPane();
	private JTextPane chatList = new JTextPane();
	private JTextField textField;
	private JButton gameSetBtn;
	private JButton createRoomBtn;

	private int mouseX, mouseY;
	
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw((Graphics2D) screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}
	
	public void screenDraw(Graphics2D g) {
		g.drawImage(background, 0, 0, null);
		g.drawImage(accessListPanel_bg, 915, 230, null);
		g.drawImage(chatList_bg, 25, 380, null);
		
		g.drawImage(screenImage, 0, 0, null);
		
		this.repaint();
		paintComponents(g);
	}
	
	public WaitingRoom(String username, String ip_addr, String port_no) {
		super("대기실");
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setShape(new RoundRectangle2D.Double(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, 40, 40));
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setLayout(null);

		if(roomSetPanel.getBgSet() == 1)
			background = bg1Img.getImage();
		else
			background = bg2Img.getImage();
		
		accessListPanel.setBounds(915, 230, 340, 420);
		add(accessListPanel);
		
		JPanel roomListPanel = new RoomListPanel();
		roomListPanel.setBounds(500, 35, 390, 615);
		add(roomListPanel);
		
		JPanel rankingPanel = new RankingPanel();
		rankingPanel.setBounds(25, 35, 450, 335);
		add(rankingPanel);
		
		chatList.setBounds(25, 380, 450, 240);
		add(chatList);
		
		textField = new JTextField();
		textField.setBounds(25, 630, 450, 20);
		textField.setColumns(24);
		add(textField);
		
		createRoomBtn = new JButton("방생성");
		createRoomBtn.setBounds(1010, 70, 160, 56);
		add(createRoomBtn);
		
		gameSetBtn = new JButton("게임설정");
		gameSetBtn.setBounds(1010, 140, 160, 56);
		gameSetBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				//gameSetBtn.setIcon(gamesetEnteredImg);  //이미지 만들면 수정
				gameSetBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
					if (Main.SOUND_EFFECT) {
						Music btnEnteredMusic = new Music("btnEnteredSound.mp3", false);
						btnEnteredMusic.start();
					}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				//gameSetBtn.setIcon(gamesetImg);
				gameSetBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
					if (Main.SOUND_EFFECT) {
						Music btnPressedMusic = new Music("btnPressedSound.mp3", false);
						btnPressedMusic.start();
						//gameSetPanel.setVisible(true);  //나중에 추가
					}
			}
		});
		add(gameSetBtn);
		
		menuBar.setBounds(0, 0, 1220, 30);
		menuBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {	/* 메뉴바 잡고 이동 */
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);	
			}
		});
		add(menuBar);


		exitBtn.setBounds(1238, 12, 30, 27);
		exitBtn.setBorderPainted(false);
		exitBtn.setContentAreaFilled(false);
		exitBtn.setFocusPainted(false);
		exitBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitBtn.setIcon(exitBtnEnteredImg);
				exitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				if(Main.SOUND_EFFECT) {
					Music btnEnteredMusic = new Music("btnEnteredSound.mp3", false);
					btnEnteredMusic.start();
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				exitBtn.setIcon(exitBtnImg);
				exitBtn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				if(Main.SOUND_EFFECT) {
					Music btnPressedMusic = new Music("btnPressedSound.mp3", false);
					btnPressedMusic.start();
				}
				try {
					Thread.sleep(500);	//효과음이 정상적으로 나타날 수 있도록 sleep
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		});
		add(exitBtn);
		
		AppendText("User " + username + " connecting " + ip_addr + " " + port_no);
		UserName = username;
		
		try {
			socket = new Socket(ip_addr, Integer.parseInt(port_no));

			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());

			ChatMsg obcm = new ChatMsg(UserName, "100", "Hello");
			SendObject(obcm);

			ListenNetwork net = new ListenNetwork();
			net.start();

		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
			AppendText("connect error");
		}
	}

	// Server Message를 수신해서 화면에 표시
	class ListenNetwork extends Thread {
		public void run() {
			while (true) {
				try {
					Object obcm = null;
					String msg = null;
					ChatMsg cm;
					try {
						obcm = ois.readObject();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						break;
					}
					if (obcm == null)
						break;
					if (obcm instanceof ChatMsg) {
						cm = (ChatMsg) obcm;
						msg = String.format("[%s] %s", cm.UserName, cm.data);
					} else
						continue;
					switch (cm.code) {
						// 각 코드별 동작 구현
					}
				} catch (IOException e) {
					AppendText("ois.readObject() error");
					try {
						ois.close();
						oos.close();
						socket.close();

						break;
					} catch (Exception ee) {
						break;
					} // catch문 끝
				} // 바깥 catch문끝
			}
		}
	}

	// 화면에 출력
	public void AppendText(String msg) {
		msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
		int len = chatList.getDocument().getLength();
		// 끝으로 이동
		chatList.setCaretPosition(len);
		chatList.replaceSelection(msg + "\n");
	}
	
	// Windows 처럼 message 제외한 나머지 부분은 NULL 로 만들기 위한 함수
	public byte[] MakePacket(String msg) {
		byte[] packet = new byte[BUF_LEN];
		byte[] bb = null;
		int i;
		for (i = 0; i < BUF_LEN; i++)
			packet[i] = 0;
		try {
			bb = msg.getBytes("euc-kr");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.exit(0);
		}
		for (i = 0; i < bb.length; i++)
			packet[i] = bb[i];
		return packet;
	}

	public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
		try {
			oos.writeObject(ob);
		} catch (IOException e) {
			AppendText("SendObject Error");
		}
	}
}