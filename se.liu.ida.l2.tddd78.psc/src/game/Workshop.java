package game;

import component.ShipComponent;
import component.utility.EngineComponent;
import component.utility.ReactorComponent;
import component.utility.ShieldComponent;
import component.weapon.MissileComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Workshop {

	//8*14

	private int width, height;
	private float scale;

	private int topBarHeight;

	private int sidebarWidth;
	private int sidebarX;
	private List<ShipComponent> sidebarComponents;


	private int shipWidth = 14;
	private int shipHeight = 8;

	private Starship workingShip;

	public Workshop(final int width, final int height, final float scale) {
		this.scale = (int)(2*scale);
		this.width = width / 2;
		this.height = height/2;

		sidebarComponents = new ArrayList<>();

		init();
	}

	private void init(){

		sidebarWidth = 32 / 2 - shipWidth;
		sidebarX = width - sidebarWidth;

		topBarHeight = height - shipHeight;

		sidebarComponents.add(new EngineComponent(5, 50));
		sidebarComponents.add(new ReactorComponent(5, 50));
		sidebarComponents.add(new ShieldComponent(5, 50));
		sidebarComponents.add(new MissileComponent(5, 50));
	}

	public void update(){

	}

	public void addWorkingShip(Starship ship){
		workingShip = ship;
		workingShip.setXPosition(0);
		workingShip.setYPosition(1);
	}

	public void removeShip(){
		workingShip = null;
	}

	public void draw(final Graphics g){

		workingShip.draw(g, scale);

		g.setColor(Color.BLACK);
		for(int x = 0; x < shipWidth + 1; x++){
			g.drawLine((int)(x*scale), (int)(topBarHeight * scale), (int)(x*scale), (int)(height*scale));
		}

		for(int y = 0; y < height + 1; y++){
			g.drawLine(0, (int)((y + topBarHeight) * scale), (int)((width - sidebarWidth) * scale), (int)((y + topBarHeight) * scale));
		}

		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, (int)(width* scale), (int)(topBarHeight * scale));

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect((int)(sidebarX * scale), 0 ,(int)(sidebarWidth*scale), (int)(height*scale));

	}

	public ShipComponent getComponentAtSidebar(int x, int y){
		return sidebarComponents.get(0);
	}
}
