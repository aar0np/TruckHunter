package bigboxco;

public class Forklift {

	private int coordX;
	private int coordY;
	private final int speed = 2;
	
	public Forklift(int x, int y) {
		coordX = x;
		coordY = y;
	}
	
	public int getCoordX() {
		return coordX;
	}
	
	public int getCoordY() {
		return coordY;
	}
	
	public void moveDown() {
		coordY = coordY + speed;
	}
	
	public void moveUp() {
		coordY = coordY - speed;
	}
	
	public void moveLeft() {
		coordX = coordX - speed;
	}
	
	public void moveRight() {
		coordX = coordX + speed;
	}
}
