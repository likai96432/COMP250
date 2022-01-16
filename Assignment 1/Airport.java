
public class Airport {
private int xCoord;
private int yCoord;
private int fee;


public Airport(int x, int y, int fee) {
	this.xCoord=x;
	this.yCoord=y;
	this.fee=fee;
	
	
	
}
public  int getFees() {
	return this.fee;
	
}
public static int getDistance(Airport a,Airport b) {
	double xDifference=a.xCoord-b.xCoord;
	double yDifference=a.yCoord-b.yCoord;
	double distance= Math.sqrt((xDifference)*(xDifference)+(yDifference)*(yDifference));
	double round=Math.ceil(distance);
	int Distance= (int)round;
	 
	
	return Distance;
	
	
	
}

}

