
public class HotelReservation extends Reservation {
	private Hotel hotel;
	private String roomname;
	private int numberofnights;
	private int price;

	public HotelReservation(String name, Hotel hotel, String roomname, int numberofnights) {
		super(name);
		this.roomname = roomname;
		this.numberofnights = numberofnights;
		this.hotel = hotel;
		this.price = hotel.reserveRoom(roomname);
		

	}

	public int getNumOfNights() {
		return this.numberofnights;
	}

	@Override
	public int getCost() {
		return price * getNumOfNights();
	}

	@Override
	public boolean equals(Object a) {
		boolean result = false;
		if (a instanceof HotelReservation) {
			HotelReservation b = (HotelReservation) a;
			if (       b.roomname.equals(this.roomname) 
					&& b.reservationName().equals(this.reservationName())
					&& b.getNumOfNights() == this.getNumOfNights() 
					&& b.getCost() == this.getCost()) {
				result = true;
			}
		}

		return result;

	}
}
