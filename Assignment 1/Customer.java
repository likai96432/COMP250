
public class Customer {
	private String name;
	private int balance;
	private Basket reservation;

	public Customer(String name, int balance) {
		this.name = name;
		this.balance = balance;
		reservation = new Basket();

	}

	public String getName() {
		return this.name;
	}

	public int getBalance() {
		return this.balance;
	}

	public Basket getBasket() {
		return this.reservation;
	}

	public int addFunds(int add) {
		int sum = 0;
		if (add < 0) {
			throw new IllegalArgumentException("negative fund");
		}
		if (add >= 0) {
			sum = this.getBalance() + add;
			balance=sum;
		}
		return sum;
	}

	public int addToBasket(Reservation a) {
		if (this.name.equalsIgnoreCase(a.reservationName()) == true) {
			reservation.add(a);
		} else {
			throw new IllegalArgumentException();
		}

		return reservation.getNumOfReservations();

	}

	public int addToBasket(Hotel a, String type, int nofn, boolean breakfast) {
		Reservation csn = null;
		if (breakfast == true) {
			csn = new BnBReservation(this.name, a, type, nofn);

		}
		if (breakfast == false) {
			csn = new HotelReservation(this.name, a, type, nofn);
		}
		reservation.add(csn);
		return reservation.getNumOfReservations();
	}

	public int addToBasket(Airport a, Airport b) {
		Reservation air = new FlightReservation(this.name, a, b);
		reservation.add(air);
		return reservation.getNumOfReservations();
	}

	public boolean removeFromBasket(Reservation a) {

		boolean result = reservation.remove(a);

		return result;
	}

	public int checkOut() {
		if (getBalance() < reservation.getTotalCost()) {
			throw new IllegalStateException();
		} else {
			
			this.balance=(getBalance() - reservation.getTotalCost());
			reservation.clear();
		}
		return this.balance;
	}

}
