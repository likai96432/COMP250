
public class Basket {
	private Reservation[] reservation;

	public Basket() {
		this.reservation = new Reservation[0];

	}

	public Reservation[] getProducts() {
		Reservation[] a = new Reservation[reservation.length];
		
		a = reservation;

		return a;
	}

	public int add(Reservation a) {
		Reservation[] addarray = new Reservation[this.reservation.length + 1];
		for (int i = 0; i < reservation.length; i++) {
			
				addarray[i] = reservation[i];
			
		
		}
		addarray[addarray.length-1]=a;
		this.reservation = addarray;
		return addarray.length;
	}

	public boolean remove(Reservation a) {
		Reservation[] after=new Reservation[reservation.length];
		boolean result = false;
		int indice = 0;
		for (int i = 0; i < reservation.length; i++) {
			if (reservation[i].equals(a) == true) {
				after=new Reservation[reservation.length-1];

				result = true;
				indice = i;
				break;

			}
			if (reservation[i].equals(a) == false) {
				result = false;
				after=reservation;
			}
		}
		if(result==true) {
			for(int j=0;j<after.length;j++) {
				if(j<indice) {
					after[j]=reservation[j];
				}
				if(j>=indice) {
					after[j]=reservation[j+1];
				}
			}
		}
		
		
		
		
		
		this.reservation=after;

		return result;
	}

	public void clear() {
		reservation = new Reservation[0];
	}

	public int getNumOfReservations() {
		return reservation.length;
	}

	public int getTotalCost() {
		int total = 0;
		for (int i = 0; i < reservation.length; i++) {
			int n = reservation[i].getCost();
			total += n;
		}
		return total;

	}
}
