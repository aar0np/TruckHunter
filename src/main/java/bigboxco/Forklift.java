package bigboxco;

public class Forklift {

	private int coordX;
	private int coordY;
	
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
		coordY++;
	}
	
	public void moveUp() {
		coordY--;
	}
	
	public void moveLeft() {
		coordX--;
	}
	
	public void moveRight() {
		coordX++;
	}
}
