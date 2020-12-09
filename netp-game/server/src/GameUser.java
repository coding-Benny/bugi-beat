import java.net.Socket;

public class GameUser {
	private String userName;
	private GameRoom room;
	private Socket sock;
	// 플레이어 위치 정보 추가 예정
	
	public GameUser(String userName) {
		this.userName = userName;
	}
	
	public void enterRoom(GameRoom room) {
		//room.enterUser(this);	// room에 입장
		this.room = room;		// 유저가 속한 방을 현재 room으로 변경
	}
	
	public void exitRoom(GameRoom room) {
		this.room = null;
		// 퇴장 처리
	}
	
	public GameRoom getGameRoom() {
		return room;
	}
	
	public void setGameRoom(GameRoom room) {
		this.room = room;
	}
	
	public Socket getSock() {
		return sock;
	}
	
	public void setSock(Socket sock) {
		this.sock = sock;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
