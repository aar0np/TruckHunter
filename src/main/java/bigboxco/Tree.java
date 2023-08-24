package bigboxco;

import java.util.Random;

public class Tree {

	private String image = "tree.png";
	
	private boolean leftSide = false;
	
	private int coordX;
	private int coordY;
	
	private Random random;
	
	public Tree (Boolean side) {
		
		random = new Random();
		
		if (side) {
			coordX = genLeftX();
			leftSide = true;
		} else {
			coordX = genRightX();
		}
		
		coordY = random.nextInt(0,1024);
	}
	
	private int genLeftX() {
		return random.nextInt(649,1016);
	}
	
	private int genRightX() {
		return random.nextInt(0,344);
	}
	
	public String getImage() {
		return image;
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
	
	public void move(int speed) {
		coordY = coordY + speed;
		
		if (coordY > 1024) {
			coordY = -48;

			if (leftSide) {
				coordX = genLeftX();
			} else {
				coordX = genRightX();
			}
		}
	}
}
