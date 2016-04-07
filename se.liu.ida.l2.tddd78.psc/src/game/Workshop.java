package game;

import java.awt.*;

public class Workshop {

	private int width, height;

	private int topbarHeight;

	private int sidebarWidth;
	private int sidebarX;

	private Starship workingShip;

	public Workshop(final int width, final int height, final float scale) {
		this.width = (int)(width * scale);
		this.height = (int)(height * scale);

		topbarHeight = this.height / 10;

		sidebarWidth = this.width / 4;
		sidebarX = this.width - sidebarWidth;
	}

	public void update(){

	}

	public void addShip(Starship ship){
		workingShip = ship;
	}

	public void draw(final Graphics g){

		g.setColor(Color.ORANGE);
		g.fillRect(0,0,width, topbarHeight);

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(sidebarX, 0 ,sidebarWidth, height);
	}
}
