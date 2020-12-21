package main;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CreateRoom extends JFrame {
	private JTextField roomTitle;
	private JButton createBtn;
	private JPasswordField roomPwd;
	private JCheckBox roomSecret;
	
	public CreateRoom() {
		setTitle("방 생성");
		setSize(400, 300);
		setLocation(400, 400);
		
		Container c = getContentPane();
		c.setLayout(null);
		
		roomTitle = new JTextField();
		roomTitle.setBounds(182, 79, 116, 21);
		c.add(roomTitle);
		roomTitle.setColumns(10);
		
		roomPwd = new JPasswordField();
		roomPwd.setEnabled(false);
		roomPwd.setEditable(false);
		roomPwd.setBounds(183, 118, 115, 21);
		c.add(roomPwd);
		
		// 방만들기 완료 버튼
		createBtn = new JButton("방 생성");
		createBtn.setBounds(140, 197, 97, 23);
		c.add(createBtn);
		
		// 비밀번호 방 설정 체크 버튼
		roomSecret = new JCheckBox("비밀방");
		roomSecret.setBounds(159, 157, 121, 23);
		c.add(roomSecret);
		roomSecret.setFocusable(false);
		roomSecret.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if(roomSecret.isSelected()) {
                	roomPwd.setEditable(true);
                	roomPwd.setEnabled(true);
                } else {
                	roomPwd.setEditable(false);
                	roomPwd.setEnabled(false);
                }
            }
        });
		
		// 기타 라벨
		JLabel label = new JLabel("참여 인원 : ");
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setBounds(92, 44, 67, 15);
        getContentPane().add(label);
        
        JLabel lblNewLabel = new JLabel("방 이름 : ");
        lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel.setBounds(102, 82, 57, 15);
        getContentPane().add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("비밀번호 : ");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_1.setBounds(92, 121, 67, 15);
        getContentPane().add(lblNewLabel_1);
		
        // 방만들기 완료 버튼 누를 시 호출할 리스너
        //ActionListener bcrfh = new BtCreateRoomFinishHandler(this);
        //createBtn.addActionListener(bcrfh);
        
        // 비밀번호 필드 or 제목 입력 필드에서 엔터 누를 시 방만들기 버튼 자동 클릭
        roomTitle.addKeyListener(new KeyListener() {
            
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == 10) {
                    createBtn.doClick();
                }
            }
        });
        
        roomPwd.addKeyListener(new KeyListener() {
            
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == 10) {
                    createBtn.doClick();
                }
            }
            
        });
	}
}
