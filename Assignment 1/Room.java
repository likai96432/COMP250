
public class Room {
	private boolean available;
	private int price;
	private String type;

	public Room(String type) {
		
		if (type.equalsIgnoreCase("double")==true) {
			this.type = type;
			this.price = 90 * 100;
			this.available = true;
		} else if (type.equalsIgnoreCase("queen")==true){
			this.type = type;
			this.price = 110 * 100;
			this.available = true;
		} else if (type.equalsIgnoreCase("king")==true) {
			this.type = type;
			price = 150 * 100;
			available = true;
		} else {
			
			throw new IllegalArgumentException("no room of such type can be created");
		}

	}

	public Room(Room newroom) {
		this.price = newroom.price;
		this.type = newroom.type;
		this.available = newroom.available;

	}

	public String getType() {
		return this.type;
	}
	

	public int getPrice() {
		return this.price;
	}

	public void changeAvailability() {
		this.available = !this.available;

	}

	public static Room findAvailableRoom(Room[] a, String type) {
		Room h=null;
		for (int i = 0; i < a.length; i++) {
			if (a[i].getType().equalsIgnoreCase(type)==true && a[i].available == true) {
				h= a[i];
				break;
			}
		}
		return h;
	}
	public static boolean makeRoomAvailable(Room[] a,String type) {
		boolean Availability=false;
		for (int i = 0; i <= a.length - 1; i++) {
			if(a[i].type.equalsIgnoreCase(type)==true && a[i].available==false) {
				a[i].changeAvailability();
				Availability=true;
				break;
				
			}
		}
		return Availability;

	}
	

}
