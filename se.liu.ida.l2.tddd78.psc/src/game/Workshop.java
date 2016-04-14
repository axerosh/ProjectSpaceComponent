package game;

import ship.component.ShipComponent;
import ship.component.utility.EngineComponent;
import ship.component.utility.ReactorComponent;
import ship.component.utility.ShieldComponent;
import ship.component.weapon.MissileComponent;
import ship.Starship;

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
	private List<List<ShipComponent>> sidebarComponents;


	private int shipWidth = 14;
	private int shipHeight = 8;

	private Starship workingShip;

	private boolean draggingComponent;

	public Workshop(final int width, final int height, final float scale) {
		this.scale = (int)(scale);
		this.width = width;
		this.height = height;

		sidebarComponents = new ArrayList<>();

		draggingComponent = false;

		init();
	}

	private void init(){

		sidebarWidth = width - shipWidth;
		sidebarX = width - sidebarWidth;

		topBarHeight = height - shipHeight;

		for(int i = 0; i < height; i++){
			sidebarComponents.add(new ArrayList<>());
		}
		sidebarComponents.get(0).add(new EngineComponent(1, 1));
		sidebarComponents.get(0).add(new ReactorComponent(1, 1));
		sidebarComponents.get(1).add(new ShieldComponent(1, 1));
		sidebarComponents.get(1).add(new MissileComponent(1, 1));
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

		for(List<ShipComponent> row :sidebarComponents){
			for(ShipComponent sc: row){
				sc.draw(g, scale, 14 + row.indexOf(sc), sidebarComponents.indexOf(row));
			}
		}

	}

	public Starship getWorkingShip() {
		return workingShip;
	}

	public ShipComponent getComponentAtSidebar(float x, float y){
		if(x - sidebarX < 0){
			return null;
		}
		return sidebarComponents.get((int)y).get((int)x - sidebarX);
	}

	public int getTopBarHeight() {
		return topBarHeight;
	}
}
