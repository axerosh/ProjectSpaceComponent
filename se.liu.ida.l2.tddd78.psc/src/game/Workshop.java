package game;

import java.awt.*;

public class Workshop {

	//8*14

	private int width, height;
	private float scale;

	private int topBarHeight;

	private int sidebarWidth;
	private int sidebarX;


	private int shipWidth = 14;
	private int shipHeight = 8;
	private float shipSpaceScale;

	private int topAndBottomSpace;

	private Starship workingShip;

	public Workshop(final int width, final int height, final float scale) {
		this.scale = scale;
		this.width = (int)(width * this.scale);
		this.height = (int)(height * this.scale);

		init();
	}

	private void init(){

		sidebarWidth = width / 4;
		sidebarX = width - sidebarWidth;

		shipSpaceScale = (width - sidebarWidth) / shipWidth;
		System.out.println(shipSpaceScale);

		topBarHeight = (int)(height / shipHeight);
		System.out.println(topBarHeight);

		int shipSpaceHeigth = (int)(8 * shipSpaceScale);

		topAndBottomSpace = ((height - topBarHeight) - shipSpaceHeigth) / 2;
		System.out.println(topAndBottomSpace);
	}

	public void update(){

	}

	public void addShip(Starship ship){
		workingShip = ship;
		workingShip.setXPosition(0);
		workingShip.setYPosition((int)((topBarHeight + topAndBottomSpace) / shipSpaceScale + 0.5));
	}

	public void draw(final Graphics g){

		workingShip.draw(g, shipSpaceScale);

		g.setColor(Color.BLACK);
		for(int x = 0; x < shipWidth + 1; x++){
			g.drawLine((int)(x*shipSpaceScale), topBarHeight + topAndBottomSpace, (int)(x * shipSpaceScale), topAndBottomSpace +
																											 topBarHeight + (int)(shipHeight * shipSpaceScale));
		}

		for(int y = 0; y < shipHeight + 1; y++){
			g.drawLine(0, (int)(y*shipSpaceScale) + topBarHeight + topAndBottomSpace, (int)(shipWidth * shipSpaceScale), (int)(y * shipSpaceScale) +
																														 topBarHeight + topAndBottomSpace);
		}

		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, width, topBarHeight);

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(sidebarX, 0 ,sidebarWidth, height);


	}
}
