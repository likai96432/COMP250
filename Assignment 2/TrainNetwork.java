public class TrainNetwork {
	final int swapFreq = 2;
	TrainLine[] networkLines;

	public TrainNetwork(int nLines) {
		this.networkLines = new TrainLine[nLines];
	}

	public void addLines(TrainLine[] lines) {
		this.networkLines = lines;
	}

	public TrainLine[] getLines() {
		return this.networkLines;
	}

	public void dance() {
		System.out.println("The tracks are moving!");
		for (int i = 0; i < networkLines.length; i++) {
			networkLines[i].shuffleLine();
		}
	}

	public void undance() {
		for (int i = 0; i < networkLines.length; i++) {
			networkLines[i].sortLine();
		}
	}

	public int travel(String startStation, String startLine, String endStation, String endLine) {

		TrainLine curLine = getLineByName(startLine); // use this variable to store the current line.
		TrainStation curStation = curLine.findStation(startStation); // use this variable to store the current station.

		int hoursCount = 0;
		System.out.println("Departing from " + startStation);
		TrainStation previous = null;
		TrainStation intermidate;
		int station;

		while (true) {
			if(curStation.getName().equals(endStation) ==true ) break;
			hoursCount++;
			intermidate = curStation;
			curStation = curLine.travelOneStation(curStation, previous);
			previous = intermidate;
			curLine = curStation.getLine();

			if (hoursCount == 168) {
				System.out.println("Jumped off after spending a full week on the train. Might as well walk.");
				return hoursCount;
			}
			if (hoursCount % 2 == 0) {

				this.dance();
			}

			System.out.println("Traveling on line " + curLine.getName() + ":" + curLine.toString());

			System.out.println("Hour " + hoursCount + ". Current station: " + curStation.getName() + " on line "
					+ curLine.getName());

			System.out.println("=============================================");
		}

		System.out.println("Arrived at destination after " + hoursCount + " hours!");

		station = hoursCount;

		return hoursCount;

	}

	public TrainLine getLineByName(String lineName) throws LineNotFoundException {

		for (int i = 0; i < networkLines.length; i++) {
			if (lineName.equals(networkLines[i].getName()) == true) {
				return networkLines[i];

			}
		}

		throw new LineNotFoundException(lineName);
	}

	public void printPlan() {
		System.out.println("CURRENT TRAIN NETWORK PLAN");
		System.out.println("----------------------------");
		for (int i = 0; i < this.networkLines.length; i++) {
			System.out.println(this.networkLines[i].getName() + ":" + this.networkLines[i].toString());
		}
		System.out.println("----------------------------");
	}
}

//exception when searching a network for a LineName and not finding any matching Line object.
class LineNotFoundException extends RuntimeException {
	String name;

	public LineNotFoundException(String n) {
		name = n;
	}

	public String toString() {
		return "LineNotFoundException[" + name + "]";
	}
}