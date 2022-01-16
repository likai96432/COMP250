import java.util.Arrays;
import java.util.Random;

public class TrainLine {

	private TrainStation leftTerminus;
	private TrainStation rightTerminus;
	private String lineName;
	private boolean goingRight;
	public TrainStation[] lineMap;
	public static Random rand;

	public TrainLine(TrainStation leftTerminus, TrainStation rightTerminus, String name, boolean goingRight) {
		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		this.lineMap = this.getLineArray();
	}

	public TrainLine(TrainStation[] stationList, String name, boolean goingRight)
	/*
	 * Constructor for TrainStation input: stationList - An array of TrainStation
	 * containing the stations to be placed in the line name - Name of the line
	 * goingRight - boolean indicating the direction of travel
	 */
	{
		TrainStation leftT = stationList[0];
		TrainStation rightT = stationList[stationList.length - 1];

		stationList[0].setRight(stationList[stationList.length - 1]);
		stationList[stationList.length - 1].setLeft(stationList[0]);

		this.leftTerminus = stationList[0];
		this.rightTerminus = stationList[stationList.length - 1];
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		for (int i = 1; i < stationList.length - 1; i++) {
			this.addStation(stationList[i]);
		}

		this.lineMap = this.getLineArray();
	}

	public TrainLine(String[] stationNames, String name, boolean goingRight) {
		TrainStation leftTerminus = new TrainStation(stationNames[0]);
		TrainStation rightTerminus = new TrainStation(stationNames[stationNames.length - 1]);

		leftTerminus.setRight(rightTerminus);
		rightTerminus.setLeft(leftTerminus);

		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;
		for (int i = 1; i < stationNames.length - 1; i++) {
			this.addStation(new TrainStation(stationNames[i]));
		}

	}

	// adds a station at the last position before the right terminus
	public void addStation(TrainStation stationToAdd) {
		TrainStation rTer = this.rightTerminus;
		TrainStation beforeTer = rTer.getLeft();
		rTer.setLeft(stationToAdd);
		stationToAdd.setRight(rTer);
		beforeTer.setRight(stationToAdd);
		stationToAdd.setLeft(beforeTer);

		stationToAdd.setTrainLine(this);

		this.lineMap = this.getLineArray();
	}

	public String getName() {
		return this.lineName;
	}

	public int getSize() {
		int size = 0;

		TrainStation start = findStation(leftTerminus.getName());

		while (true) {

			size++;
			if (start == rightTerminus) {
				break;
			}
			start = start.getRight();

		}
		return size;
	}

	public void reverseDirection() {
		this.goingRight = !this.goingRight;
	}

	// You can modify the header to this method to handle an exception. You cannot
	// make any other change to the header.
	public TrainStation travelOneStation(TrainStation current, TrainStation previous) {
		TrainStation result = null;
		this.findStation(current.getName());

		if (current.hasConnection == true && current.getTransferStation().equals(previous) == false) {
			result = current.getTransferStation();
		}
		if (current.hasConnection == false || current.getTransferStation().equals(previous) == true) {
			result = getNext(current);
		}
		return result;
	}

	// You can modify the header to this method to handle an exception. You cannot
	// make any other change to the header.
	public TrainStation getNext(TrainStation station) {
		TrainStation result = null;
		this.findStation(station.getName());

		if (goingRight == true) {
			if (station.isRightTerminal() == true) {
				result = station.getLeft();
				reverseDirection();
			} else {
				result = station.getRight();
			}
		} else if (goingRight == false) {
			if (station.isLeftTerminal() == true) {
				result = station.getRight();
				reverseDirection();
			} else {
				result = station.getLeft();
			}
		}
		return result;
	}

	// You can modify the header to this method to handle an exception. You cannot
	// make any other change to the header.
	public TrainStation findStation(String name) {

		TrainStation temp = this.leftTerminus;

		while (true) {
			if (temp.getName().equals(name) == true) {
				return temp;
			}
			if (temp.getName().equals(name) == false) {
				temp = temp.getRight();
			}
			if (temp == null) {
				throw new StationNotFoundException(name);
			}

		}

	}

	public void sortLine() {
		int size = this.getSize();
		TrainStation[] array = this.getLineArray();
		array[0].setNonTerminal();
		array[size - 1].setNonTerminal();
		for (int i = 0; i < size; i++) {
			for (int j = 1; j < (size - i); j++) {
				if (array[j - 1].getName().compareTo(array[j].getName()) > 0) {
					TrainStation temp = array[j - 1];
					array[j - 1] = array[j];
					array[j] = temp;
				}
			}
		}
		this.leftTerminus = array[0];
		this.leftTerminus.setLeftTerminal();
		this.leftTerminus.setLeft(null);
		this.rightTerminus = array[size - 1];
		this.rightTerminus.setRightTerminal();
		this.rightTerminus.setRight(null);
		TrainStation current = null;
		TrainStation previous = this.leftTerminus;
		for (int i = 1; i < size; i++) {
			current = array[i];
			previous.setRight(current);
			current.setLeft(previous);
			previous = current;
		}
		this.lineMap = array;
	}

	public TrainStation[] getLineArray() {

		TrainStation haha = findStation(leftTerminus.getName());

		TrainStation[] newlist = new TrainStation[this.getSize()];
		for (int j = 0; j < this.getSize(); j++) {
			newlist[j] = haha;
			haha = haha.getRight();
		}

		return newlist; // change this
	}

	private TrainStation[] shuffleArray(TrainStation[] array) {
		Random rand = new Random();
		rand.setSeed(11);
		for (int i = 0; i < array.length; i++) {
			int randomIndexToSwap = rand.nextInt(array.length);
			TrainStation temp = array[randomIndexToSwap];
			array[randomIndexToSwap] = array[i];
			array[i] = temp;
		}
		this.lineMap = array;
		return array;
	}

	public void shuffleLine() {

		// you are given a shuffled array of trainStations to start with
		int size = this.getSize();
		TrainStation[] lineArray = this.getLineArray();
		lineArray[0].setNonTerminal();
		lineArray[size - 1].setNonTerminal();
		TrainStation[] shuffledArray = shuffleArray(lineArray);
		this.leftTerminus = shuffledArray[0];
		this.leftTerminus.setLeftTerminal();
		this.leftTerminus.setLeft(null);
		this.rightTerminus = shuffledArray[size - 1];
		this.rightTerminus.setRightTerminal();
		this.rightTerminus.setRight(null);
		TrainStation curr = null, prev = this.leftTerminus;
		for (int i = 1; i < size; i++) {
			curr = shuffledArray[i];
			prev.setRight(curr);
			curr.setLeft(prev);
			prev = curr;
		}
		this.lineMap = shuffledArray;

	}

	public String toString() {
		TrainStation[] lineArr = this.getLineArray();
		String[] nameArr = new String[lineArr.length];
		for (int i = 0; i < lineArr.length; i++) {
			nameArr[i] = lineArr[i].getName();
		}
		return Arrays.deepToString(nameArr);
	}

	public boolean equals(TrainLine line2) {

		// check for equality of each station
		TrainStation current = this.leftTerminus;
		TrainStation curr2 = line2.leftTerminus;

		try {
			while (current != null) {
				if (!current.equals(curr2))
					return false;
				else {
					current = current.getRight();
					curr2 = curr2.getRight();
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public TrainStation getLeftTerminus() {
		return this.leftTerminus;
	}

	public TrainStation getRightTerminus() {
		return this.rightTerminus;
	}
}

//Exception for when searching a line for a station and not finding any station of the right name.
class StationNotFoundException extends RuntimeException {
	String name;

	public StationNotFoundException(String n) {
		name = n;
	}

	public String toString() {
		return "StationNotFoundException[" + name + "]";
	}
}
