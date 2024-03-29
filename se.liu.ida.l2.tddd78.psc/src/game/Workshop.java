package game;

import graphics.DisplayableEnvironment;
import ship.ShipFactory;
import ship.Starship;
import ship.component.ShipComponent;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * A DisplayableEnvironment that shows a working area to edit induvidiall ShipComponents and also,
 * save and load Starship designes by using ShipIO
 *
 * @see DisplayableEnvironment
 * @see Starship
 * @see ShipComponent
 * @see io.ShipIO
 */
public class Workshop implements DisplayableEnvironment {

	private int width, height;

	private int topBarHeight;

	private int sidebarWidth;
	private int sidebarX;
	private List<List<ShipComponent>> sidebarComponents;


	private int shipWidth;
	private int shipHeight;

	private Starship workingShip;

	public Workshop(final int width, final int height, final int shipWidth, final int shipHeight) {
		this.width = width;
		this.height = height;
		this.shipWidth = shipWidth;
		this.shipHeight = shipHeight;

		sidebarComponents = new ArrayList<>();
		workingShip = null;

		init();
	}

	private void init() {

		sidebarWidth = width - shipWidth;
		sidebarX = width - sidebarWidth;

		topBarHeight = height - shipHeight;

		for (int i = 0; i < height; i++) {
			sidebarComponents.add(new ArrayList<>());
		}

		sidebarComponents.get(0).add(ShipFactory.getEngineComponent());
		sidebarComponents.get(0).add(ShipFactory.getReactorComponent());
		sidebarComponents.get(1).add(ShipFactory.getShieldComponent());
		sidebarComponents.get(1).add(ShipFactory.getWeaponComponent());
	}

	public void addWorkingShip(Starship ship) {
		workingShip = ship;
		workingShip.setX(0);
		workingShip.setY(1);
	}

	public void removeShip() {
		workingShip = null;
	}

	@Override public void display(final Graphics g, final float scale) {

		if (workingShip != null) {
			workingShip.draw(g, scale);
		}

		g.setColor(Color.BLACK);
		for (int x = 0; x < shipWidth + 1; x++) {
			g.drawLine((int) (x * scale), (int) (topBarHeight * scale), (int) (x * scale), (int) (height * scale));
		}

		for (int y = 0; y < height + 1; y++) {
			g.drawLine(0, (int) ((y + topBarHeight) * scale), (int) ((width - sidebarWidth) * scale),
					   (int) ((y + topBarHeight) * scale));
		}

		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, (int) (width * scale), (int) (topBarHeight * scale));

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect((int) (sidebarX * scale), 0, (int) (sidebarWidth * scale), (int) (height * scale));

		for (List<ShipComponent> row : sidebarComponents) {
			for (ShipComponent sc : row) {
				sc.draw(g, scale, shipWidth + row.indexOf(sc), sidebarComponents.indexOf(row));
			}
		}

	}

	public Starship getWorkingShip() {
		return workingShip;
	}

	public ShipComponent getComponentAtSidebar(float x, float y) {
		if (x - sidebarX < 0 || y < 0) {
			return null;
		}
		if (y < sidebarComponents.size()) {
			List<ShipComponent> row = sidebarComponents.get((int) y);
			if (x - sidebarX < row.size()) {
				return row.get((int) (x - sidebarX));
			}
		}

		return null;
	}

	@Override public float getWidth() {
		return width;
	}

	@Override public float getHeight() {
		return height;
	}
}
