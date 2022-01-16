
public class Hotel {
	private String name;

	private Room[] roomarray;

	public Hotel(String s, Room[] b) {
		this.name = s;
		this.roomarray = new Room[b.length];

		for (int i = 0; i < b.length; i++) {
			Room r = b[i];
			Room h = new Room(r);
			roomarray[i] = h;

		}

	}

	public int reserveRoom(String type) {

		Room room = Room.findAvailableRoom(roomarray, type);
		
		if (room!=null) {
			room.changeAvailability();
			return room.getPrice();

		}
		else{
			throw new IllegalArgumentException("no avaiable room");

		}
		
		

	}

	public boolean cancelRoom(String type) {

		return Room.makeRoomAvailable(this.roomarray, type);
	}
	

}
