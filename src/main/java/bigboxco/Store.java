package bigboxco;

public class Store {

	private int coordX;
	private int coordY;
	private final int stopPoint = 256;
	
	public Store() {
		coordX = 640;
		coordY = -400;
	}
	
	public int getCoordX() {
		return coordX;
	}
	
	public void setCoordX(int x) {
		coordX = x;
	}
	
	public int getCoordY() {
		return coordY;
	}
	
	public void setCoordY(int y) {
		coordY = y;
	}
	
	public int getStopPoint() {
		return stopPoint;
	}
	
	public void move(int speed) {
		coordY = coordY + speed;
	}
}
