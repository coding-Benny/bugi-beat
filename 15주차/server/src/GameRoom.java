import java.util.ArrayList;
import java.util.List;

public class GameRoom {
	private int id;	// room ID
	private List<GameUser> userList;
	private GameUser roomOwner;	// 방장
	private String roomTitle;	// 방제
	private String difficulty;	// 난이도
	private int numOfLines;	// 칸 수
	private int usercnt;	// 유저수
	
	public GameRoom(int roomID) {
		this.id = roomID;
		userList = new ArrayList<GameUser>();
	}
	
	public GameRoom(int roomID, GameUser user, String title, String difficulty, int numOfLines) {
		this.id = roomID;
		this.roomTitle = title;
		this.difficulty = difficulty;
		this.numOfLines = numOfLines;
		userList = new ArrayList<GameUser>();
		user.enterRoom(this);
		userList.add(user);	// 유저를 추가
		this.roomOwner = user;	// 방을 생성한 user가 방장이 됨
		usercnt = userList.size();
	}
	
	public void enterUser(GameUser user) {
		user.enterRoom(this);
		userList.add(user);
		usercnt = userList.size();
	}
	
	public void exitUser(GameUser user) {
		user.exitRoom(this);
		userList.remove(user);	// 해당 유저를 강퇴
		usercnt = userList.size();
		
		if (userList.size() < 1) {	// 모든 인원이 퇴장한 경우
			RoomManager.removeRoom(this);	// 방을 제거
			return;
		}
		
		if (userList.size() < 2) {	// 방에 남은 인원이 1명 이하라면
			this.roomOwner = userList.get(0);	// 리스트의 첫번째 유저가 방장이 됨
			return;
		}
	}
	
	/*
	 * 유저를 전부 퇴장시키고 삭제
	 */
	public void close() {
		for (GameUser user : userList) {
			user.exitRoom(this);
		}
		this.userList.clear();
		this.userList = null;
		usercnt = userList.size();
	}
	
	public void setOwner(GameUser gameUser) {
		this.roomOwner = gameUser;
	}
	
	public void setRoomTitle(String title) {
		this.roomTitle = title;
	}
	
	public String getRoomTitle() {
		return roomTitle;
	}
	
	public int getUserSize() {
		return userList.size();
	}
	
	public GameUser getRoomOwner() {
		return roomOwner;
	}
	
	public List<GameUser> getUserList() {
		return userList;
	}
	
	public void setUserList(List<GameUser> userList) {
		this.userList = userList;
	}
	
	public void setRoomOwner(GameUser roomOwner) {
		this.roomOwner = roomOwner;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDifficulty() {
		return difficulty;
	}
	
	public int getNumOfLines() {
		return numOfLines;
	}
	
	public int getUsercnt() {
		return usercnt;
	}
}
