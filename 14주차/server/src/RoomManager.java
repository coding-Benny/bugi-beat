import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoomManager {
	public static List<GameRoom> roomList = new ArrayList<GameRoom>();	// 방의 리스트
	private static AtomicInteger atomicInteger = new AtomicInteger();;
	
	public RoomManager() { }
	
	/*
	 * 방을 생성함과 동시에 방장으로 임명
	 */
	public static GameRoom createRoom(GameUser owner, String title, String difficulty, int numOfLines) {
		int roomID = atomicInteger.incrementAndGet();
		
		GameRoom gameRoom = new GameRoom(roomID, owner, title, difficulty, numOfLines);
		
		roomList.add(gameRoom);
		System.out.println("Room created!!");
		return gameRoom;
	}
	
	public static GameRoom getRoom(GameRoom gameRoom) {
		int index = roomList.indexOf(gameRoom);
		
		if (index > 0)
			return (GameRoom) roomList.get(index);
		else
			return null;
	}
	
	public static GameRoom getRoom(int index) {
		if (index > 0)
			return (GameRoom) roomList.get(index);
		else
			return null;
	}
	
	public static void removeRoom(GameRoom gameRoom) {
		gameRoom.close();
		roomList.remove(gameRoom);
		System.out.println("Room deleted!!");
	}
	
	public static int roomCount() {
		return roomList.size();
	}
}