
public class BnBReservation extends HotelReservation {

	public BnBReservation(String name,Hotel hotel, String roomname, int numberofnights) {
		super(name, hotel, roomname, numberofnights);
		
	}
	public int getCost() {
		int n=super.getCost();
		int cost=10*getNumOfNights()*100;
		return n+cost;
	}

}
