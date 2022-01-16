
public class FlightReservation extends Reservation {
	private Airport departure;
	private Airport arrival;

	public FlightReservation(String name, Airport a, Airport b) {
		super(name);
		this.departure = a;
		this.arrival = b;
		if (a == b) {
			throw new IllegalArgumentException();

		}

	}

	public int getCost() {
		double total = 0;
		int distance = Airport.getDistance(this.departure, this.arrival);
		double fuel = (double) distance / 167.52;
		double fuelcost = fuel * 1.24 * 100.0;
		int fee = this.departure.getFees() + this.arrival.getFees();
		total = fuelcost + fee + 53.75 * 100.0;
		double cost = Math.ceil(total);

		return (int) cost;

	}

	@Override
	public boolean equals(Object a) {
		boolean result = false;
		if (a instanceof FlightReservation) {
			if (((FlightReservation) a).arrival.equals(this.arrival)
					&& ((FlightReservation) a).departure.equals(this.departure)
					&& ((Reservation) a).reservationName().equals(this.reservationName())) {
				result = true;
			}
		}
		return result;
	}
}
