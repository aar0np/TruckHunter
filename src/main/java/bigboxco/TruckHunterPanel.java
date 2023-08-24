package bigboxco;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class TruckHunterPanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 6936398250313799252L;

	private final int fPS = 60; // frames per second
	private final int edgeWidth = 384;
	private final int numTrees = 60;
	private final int numLines = 11;
	
	private boolean storeInView = false;
	private boolean forkliftInView = false;
	private boolean doorOpening = false;
	
	private int doorCount;
	private int forkliftImgIdx = 0;
	private int forkliftLeg;
	private int panelHeight;
	private int panelWidth;
	private Integer speed = 0;
	
	private Long distance = 0L;
	
	private BufferedImage truckImg;
	private BufferedImage treeImg;
	private BufferedImage lineImg;
	private BufferedImage storeImg;
	private BufferedImage[] doorImgs = new BufferedImage[5];
	private BufferedImage[] forkliftImgs = new BufferedImage[5];
	
	private Font arial40 = new Font("Arial", Font.PLAIN, 40);
	private KeyHandler keyHandler;
	private Thread panelThread;
	
	private Forklift forklift;
	private Store store;
	private Truck truck;
	
	private List<RoadLine> lines;
	private List<Tree> trees;
	
	public TruckHunterPanel() {
		
		panelWidth = 1024;
		panelHeight = 1024; 
		
		this.setPreferredSize(new Dimension(panelWidth, panelHeight));
		this.setBackground(Color.black);
		this.setFocusable(true);

		keyHandler = new KeyHandler();
		this.addKeyListener(keyHandler);
		
		generateTrees();
		generateLines();
		
		truck = new Truck();
		store = new Store();
		forklift = new Forklift(916, 522);
		
		loadImages();

		panelThread = Thread.ofVirtual()
				.name("TruckHunter")
				.unstarted(this);
	}
	
	private void update() {
		
		// truck
		if  (storeInView) {
			if (truck.getCoordY() > (store.getCoordY() + 150)) {
				if (speed > 5) {
					// slow down
					speed--;
				}
				
				if (truck.getCoordX() < 610) {
					// move over
					truck.moveRight();
				}
			} else if (speed > 0 && !doorOpening){
				// stop
				speed = 0;
				distance = 0L;
				
				// trigger open door
				doorOpening = true;
				doorCount = 0;
			}
			
			store.move(speed);
			
			if (forkliftInView) {
				switch (forkliftLeg) {
				case 1:
					if (forklift.getCoordY() < 550) {
						forklift.moveDown();
					} else {
						forkliftLeg = 2;
						forkliftImgIdx = 1;
					}
					break;
				case 2:
					if (forklift.getCoordX() > truck.getCoordX()) {
						forklift.moveLeft();
					} else {
						forkliftLeg = 3;
						forkliftImgIdx = 2;
					}
					break;
				case 3:
					if (forklift.getCoordY() < truck.getCoordY() - 20) {
						forklift.moveUp();
					} else {
						forkliftLeg = 4;
						forkliftImgIdx = 3;
					}
					break;
				case 4:
					if (forklift.getCoordY() < 550) {
						forklift.moveDown();
					} else {
						forkliftLeg = 5;
						forkliftImgIdx = 4;
					}
					break;
				case 5:
					if (forklift.getCoordX() < store.getCoordX() + 275) {
						forklift.moveRight();
					} else {
						forkliftLeg = 6;
						forkliftImgIdx = 3;
					}
					break;
				default:
					if (forklift.getCoordY() > store.getCoordY() + 219) {
						forklift.moveUp();
					} else {
						forkliftInView = false;
						doorOpening = false;
						storeInView = false;
						forkliftImgIdx = 0;
					}
				} 
			}
		} else {
			if (keyHandler.isDownPressed()) {
				if (speed > 0) {
					// brake
					speed--;
				}
			} else if (keyHandler.isUpPressed()) {
				if (speed < 50) {
					// gas
					speed++;
				}
			}
			
			if (speed > 0) {
				if (keyHandler.isRightPressed()) {
					if (truck.getCoordX() < 640) {
						truck.moveRight();
					}
				} else if (keyHandler.isLeftPressed()) {
					if (truck.getCoordX() > 384) {
						truck.moveLeft();
					}
				}
			}
			
			if (((store.getCoordY() + 150) >= truck.getCoordY()
					&& (store.getCoordY() + 150) < 1024)) {
				store.move(speed);
			}
		}
		
		if (speed > 0) {
			// trees
			for (Tree tree : trees) {
				tree.move(speed);
			}
			
			// lines in road
			for (RoadLine line : lines) {
				line.move(speed);
			}
		}

		if (distance > 10000) {
			storeInView = true;
		} else {
			distance = distance + speed;
		}
	}
	
	private void loadImages() {
		
		try {
			truckImg = scaleImage(ImageIO.read(getClass().getResourceAsStream("/images/truck_hunter_1.png")),48,144);
			treeImg = scaleImage(ImageIO.read(getClass().getResourceAsStream("/images/tree.png")),48,48);
			lineImg = scaleImage(ImageIO.read(getClass().getResourceAsStream("/images/road_line.png")),48,51);
			storeImg = scaleImage(ImageIO.read(getClass().getResourceAsStream("/images/store.png")),384,384);
			doorImgs[0] = scaleImage(ImageIO.read(getClass().getResourceAsStream("/images/door_1.png")),60,60);
			doorImgs[1] = scaleImage(ImageIO.read(getClass().getResourceAsStream("/images/door_2.png")),60,60);
			doorImgs[2] = scaleImage(ImageIO.read(getClass().getResourceAsStream("/images/door_3.png")),60,60);
			doorImgs[3] = scaleImage(ImageIO.read(getClass().getResourceAsStream("/images/door_4.png")),60,60);
			doorImgs[4] = scaleImage(ImageIO.read(getClass().getResourceAsStream("/images/door_5.png")),60,60);
			forkliftImgs[0] = scaleImage(ImageIO.read(getClass().getResourceAsStream("/images/forklift_down_1.png")),48,96);
			forkliftImgs[1] = scaleImage(ImageIO.read(getClass().getResourceAsStream("/images/forklift_left_1.png")),96,96);
			forkliftImgs[2] = scaleImage(ImageIO.read(getClass().getResourceAsStream("/images/forklift_up_1.png")),48,96);
			forkliftImgs[3] = scaleImage(ImageIO.read(getClass().getResourceAsStream("/images/forklift_up_2.png")),48,96);
			forkliftImgs[4] = scaleImage(ImageIO.read(getClass().getResourceAsStream("/images/forklift_right_2.png")),96,96);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void generateLines() {
		lines = new ArrayList<>();

		for (int intLines = 0; intLines < numLines; intLines++) {
			lines.add(new RoadLine(intLines * 96));
		}
	}
	
	private void generateTrees() {
		trees = new ArrayList<>();
		
		for (int intTrees = 0; intTrees < numTrees; intTrees++) {
			trees.add(new Tree(isEven(intTrees)));
		}
	}
	
	private boolean isEven(int number) {
		if (number % 2 == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		// road edges
		g2.setColor(new Color(0,214,0));
		// left
		g2.fillRect(0, 0, edgeWidth, 1024);
		// right
		g2.fillRect(640, 0, edgeWidth, panelHeight);
		
		// draw trees
		for (Tree tree : trees) {
			g2.drawImage(treeImg, tree.getCoordX(), tree.getCoordY(), null);
		}
		
		// draw lines in road
		for (RoadLine line : lines) {
			g2.drawImage(lineImg, line.getCoordX(), line.getCoordY(), null);
		}

		// draw store
        if (storeInView || ((store.getCoordY() + 150) >= truck.getCoordY()
				&& (store.getCoordY() + 150) < 1024)) {
        	// make sure to draw the store once we're done unloading
			g2.drawImage(storeImg, store.getCoordX(), store.getCoordY(), null);
		} else {
			store.setCoordY(-400);
		}
			
		// draw truck
		g2.drawImage(truckImg, truck.getCoordX(), truck.getCoordY(), null);

		// door
		if (doorOpening) {
			if (doorCount < 125) {
				int doorIndex = doorCount / 25;
				g2.drawImage(doorImgs[doorIndex], store.getCoordX() + 270, store.getCoordY() + 219, null);
				
				doorCount++;
			} else if (forkliftInView) {
				// default to open door
				g2.drawImage(doorImgs[4], store.getCoordX() + 270, store.getCoordY() + 219, null);
				// forklift
				g2.drawImage(forkliftImgs[forkliftImgIdx], forklift.getCoordX(), forklift.getCoordY(), null);
			} else {
				g2.drawImage(doorImgs[4], store.getCoordX() + 270, store.getCoordY() + 219, null);
				forkliftInView = true;
				forkliftLeg = 1;
			}
		}
				
		// distance
		g2.setColor(Color.white);
		g2.setFont(arial40);
		g2.drawString(speed.toString(), 50, 50);
		
		g2.dispose();
	}
	
	@Override
	public void run() {
		while (panelThread.isAlive()) {
			update();
			repaint();
			
			// compute pauses based on frames per second
			try {
				Thread.sleep(1000 / fPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void start() {
		panelThread.start();
	}
	
	private static BufferedImage scaleImage(BufferedImage smallImage, int width, int height) {

		BufferedImage scaledImage = new BufferedImage(width, height, smallImage.getType());
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(smallImage, 0, 0, width, height, null);
		g2.dispose();
		
		return scaledImage;
	}
}
