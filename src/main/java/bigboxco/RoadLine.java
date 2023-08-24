package bigboxco;

public class RoadLine {

	private String image = "road_line.png";
	
	int coordX;
	int coordY;
	
	public RoadLine(int y) {
		coordX = 488;
		coordY = y;
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
		coordX = y;
	}
	
	public void move(int speed) {
		coordY = coordY + speed;
		
		if (coordY > 1023) {
			coordY = -48;
		}
	}
}
