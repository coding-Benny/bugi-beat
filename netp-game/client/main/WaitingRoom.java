package main;


import java.awt.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Vector;

import javax.swing.*;


public class WaitingRoom extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
	private Socket socket; // 연결소켓

	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	private String user;

	private Image background;
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/bar.png")));
	private ImageIcon exitBtnEnteredImg = new ImageIcon(Main.class.getResource("../images/exit1.png"));
	private ImageIcon exitBtnImg = new ImageIcon(Main.class.getResource("../images/exit0.png"));
	private Image bg1Img = new ImageIcon(Main.class.getResource("../images/anteroom1-bg.png")).getImage();
	private Image bg2Img = new ImageIcon(Main.class.getResource("../images/anteroom2-bg.png")).getImage();
	private Image accessListPanel_bg= new ImageIcon(Main.class.getResource("../images/accesslist-bg.png")).getImage();
	private Image chatList_bg= new ImageIcon(Main.class.getResource("../images/4line-push.png")).getImage();

	private RoomSetting roomSetPanel = new RoomSetting();
	private JPanel userListPanel = new UserListPanel();
	private JPanel roomListPanel = new RoomListPanel();
	private JPanel rankingPanel = new RankingPanel();
	
	private JScrollPane chatScrollPane = new JScrollPane();
	
	private JTextPane textArea = new JTextPane();
	private JTextField inputField = new JTextField();
	private JButton exitBtn = new JButton(exitBtnImg);
	private JButton gameSetBtn;
	private JButton createRoomBtn;
	
	private JList<String> userList = new JList<String>();
	public static Vector<String> member = new Vector<String>();

	private int mouseX, mouseY;
	
	public WaitingRoom(String userName, String ipAddress, String portNo) {
		super("대기실");
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setShape(new RoundRectangle2D.Double(0, 0, Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT, 40, 40));
		setResizable(false);
		setLocationRelativeTo(null);
		
		if (roomSetPanel.getBgSet() == 1)
			background = bg1Img;
		else
			background = bg2Img;
		
		setContentPane(new JLabel(new ImageIcon(background)));
		setLayout(null);
		
		rankingPanel.setBounds(25, 35, 450, 335);
		add(rankingPanel);
		
		chatScrollPane.setBounds(25, 380, 450, 240);
		add(chatScrollPane);
		textArea.setEditable(true);
		chatScrollPane.setViewportView(textArea);
		
		inputField.setBounds(25, 630, 450, 20);
		inputField.setColumns(24);
		add(inputField);
		
		roomListPanel.setBounds(500, 35, 390, 615);
		add(roomListPanel);
		
		userListPanel.setBounds(915, 230, 340, 420);
		add(userListPanel);
		
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
		
		setVisible(true);
		
		AppendText("User " + userName + " connecting " + ipAddress + " " + portNo);
		user = userName;
		
		try {
			socket = new Socket(ipAddress, Integer.parseInt(portNo));

			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());

			ChatMsg obcm = new ChatMsg(userName, "100", "Hello");
			member.add(user);
			userList.setListData(member);
			SendObject(obcm);

			ListenNetwork net = new ListenNetwork();
			net.start();
			TextSendAction textSendAction = new TextSendAction();
			inputField.addActionListener(textSendAction);
			inputField.requestFocus();

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
						msg = String.format("[%s] %s", cm.id, cm.data);
					} else
						continue;
					switch (cm.code) {
					case "200": // chat message
						if (msg.contains("입장")) {
							String[] welcomeMsg = cm.getData().split("]");
							String newUser = welcomeMsg[0].substring(1);
							member.add(newUser);
							userList.setListData(member);
						}
						if (user.equals(cm.getId()))
							AppendText(msg);
						else if ("SERVER".equals(cm.getId()))
							AppendText(msg);
						else
							AppendText(msg);
						break;
					case "800": // OldUser
						String[] oldUserInfo = msg.split(" ");
						String oldUserName = oldUserInfo[2];
						if (!oldUserName.equals(user)) {
							member.add(oldUserName);
							userList.setListData(member);
						}
						break;
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

	// keyboard enter key 치면 서버로 전송
	class TextSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// 메시지 입력하고 Enter key 치면
			if (e.getSource() == inputField) {
				String msg = null;
				msg = inputField.getText();
				SendMessage(msg);
				inputField.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
				inputField.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
				if (msg.contains("/exit")) // 종료 처리
					System.exit(0);
			}
		}
	}
	
	public JList<String> getUserList() {
		return userList;
	}
	
	public Vector<String> getMember() {
		return member;
	}
	
	// 화면에 출력
	public void AppendText(String msg) {
		msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
		int len = textArea.getDocument().getLength();
		// 끝으로 이동
		textArea.setCaretPosition(len);
		textArea.replaceSelection(msg + "\n");
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

	// Server에게 network으로 전송
	public void SendMessage(String msg) {
		try {
			ChatMsg obcm = new ChatMsg(user, "200", msg);
			oos.writeObject(obcm);
		} catch (IOException e) {
			AppendText("oos.writeObject() error");
			try {
				ois.close();
				oos.close();
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
				System.exit(0);
			}
		}
	}
		
	public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
		try {
			oos.writeObject(ob);
		} catch (IOException e) {
			AppendText("SendObject Error");
		}
	}
}
	/*
	
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
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
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
*/