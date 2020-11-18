

//ChatMsg.java 채팅 메시지 ObjectStream 용.
import java.io.Serializable;

import javax.swing.ImageIcon;

class ChatMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	public String code; // 100:로그�?�, 400:로그아웃, 200:채팅메시지, 300:Image
	public String UserName;
	public String data;
	public ImageIcon img;

	public ChatMsg(String UserName, String code, String msg) {
		this.code = code;
		this.UserName = UserName;
		this.data = msg;
	}
}
