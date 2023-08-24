package bigboxco;

public class Truck {

	int coordX;
	int coordY;
	
	public Truck() {
		coordX = 488;
		coordY = 440;
	}
	
	public int getCoordX() {
		return coordX;
	}
	
	public int getCoordY() {
		return coordY;
	}
	
	public void moveLeft() {
		coordX = coordX - 1;
	}
	
	public void moveRight() {
		coordX = coordX + 1;
	}	
}
