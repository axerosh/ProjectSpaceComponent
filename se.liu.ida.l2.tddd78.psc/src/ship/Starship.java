package ship;

import game.Team;
import graphics.StatbarDrawer;
import ship.component.ShipComponent;
import ship.component.utility.EngineComponent;
import ship.component.utility.ReactorComponent;
import ship.component.utility.ShieldComponent;
import ship.component.weapon.Projectile;
import ship.component.weapon.WeaponComponent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A starship consisting of ship components. The collective shielding and power usage of its components may not exceed its
 * shielding and power pools respectively.
 *
 * @see ShipComponent
 */
public class Starship {

	/**
	 * Random Number Generator
	 */
	private Random rng;
	private float x;
	private float y;
	private int width;
	private int height;
	private List<ShieldComponent> shieldComponents;
	private List<ReactorComponent> reactorComponents;
	private List<EngineComponent> engineComponents;
	private List<WeaponComponent> weaponComponents;
	private List<Projectile> projectilesToFire;
	private int shieldingPool;
	private int usedShielding;
	private int powerPool;
	private int usedPower;
	private int numberOfComponents;
	private Team team;
	private float integrity;
	private float maxIntegrity;

	/**
	 * The dodge rate of this ship. The rate of which projectile will miss the ship.
	 *
	 * @see Projectile
	 */
	private float dodgeRate;

	/**
	 * A grid of this star ship's ship components.
	 */
	private ShipComponent[][] components;

	/**
	 * Constructs a star ship with the specifed position, width and height.
	 *
	 * @param width     the width of the ship i.e. the number of ship components that can fit along the width
	 * @param height    the height of the ship i.e. the number of ship components that can fit along the height
	 * @param integrity the damage the ship can take before it is destroyed
	 *
	 * @throws IllegalArgumentException if one of the following is true: <ul> <li>the specified width is negative or 0</li>
	 *                                  <li>the specified height is negative or 0</li> <li>the specified integrity is negative
	 *                                  or 0</li> </ul>
	 * @see ShipComponent
	 */
	public Starship(final int width, final int height, final float integrity) throws IllegalArgumentException {
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("Invalid ship dimensions width = " + width + ", height = " + height +
											   ". Only positive integers are permitted.");
		}
		if (integrity <= 0) {
			throw new IllegalArgumentException("Invalid inegrity = " + integrity + ". Only positive values are permitted.");
		}
		x = 0;
		y = 0;
		this.width = width;
		this.height = height;
		this.integrity = integrity;
		this.maxIntegrity = integrity;

		rng = new Random();

		shieldingPool = 0;
		powerPool = 0;
		usedShielding = 0;
		usedPower = 0;
		numberOfComponents = 0;
		dodgeRate = 0;

		shieldComponents = new ArrayList<>();
		reactorComponents = new ArrayList<>();
		engineComponents = new ArrayList<>();
		weaponComponents = new ArrayList<>();
		projectilesToFire = new ArrayList<>();

		components = new ShipComponent[width][height];
		team = null;
	}

	/**
	 * Constructs a star ship with the specifed position, width and height.
	 *
	 * @param width        the width of the ship i.e. the number of ship components that can fit along the width
	 * @param height       the height of the ship i.e. the number of ship components that can fit along the height
	 * @param integrity    the current damage the ship can take before it is destroyed
	 * @param maxIntegrity the damage the ship can take before it is destroyed when undamaged. Is ignored if less than
	 *                        specified integrity
	 *
	 * @throws IllegalArgumentException if one of the following is true: <ul> <li>the specified width is negative or 0</li>
	 *                                  <li>the specified height is negative or 0</li> <li>the specified integrity is negative
	 *                                  or 0</li> </ul><ul>
	 * @see ShipComponent
	 */
	public Starship(final int width, final int height, final float integrity, final float maxIntegrity)
	throws IllegalArgumentException {
		this(width, height, integrity);

		if (maxIntegrity < integrity) {
			this.maxIntegrity = integrity;
			String message = "Invalid maximum integrity = " + maxIntegrity + ". Must be equal to or greater " +
							 "than the integrity = " + integrity + ". Using integrity value as maximum integrity instead.";
			IllegalArgumentException exception = new IllegalArgumentException(message);
			Logger.getGlobal().log(Level.FINE, message, exception);
			throw exception;
		} else {
			this.maxIntegrity = maxIntegrity;
		}
	}

	public void inflictDamage(float damage) {
		integrity -= damage;
		integrity = Math.max(integrity, 0);
	}

	public boolean isIntact() {
		return integrity > 0;
	}

	/**
	 * @return the virtual position of the specified component; null if the component is null or not a part of this ship
	 */
	public Point2D.Float getPositionOf(ShipComponent component) {
		if (component == null) {
			return null;
		}
		final float halfComponentWidth = 0.5f;
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				if (component.equals(components[col][row])) {
					return new Point2D.Float(x + col + halfComponentWidth, y + row + halfComponentWidth);
				}
			}
		}
		return null;
	}

	/**
	 * Returns the ship component at the specified position.
	 *
	 * @param x the x-coordinate of the position
	 * @param y the y-coordinate of the position
	 *
	 * @return the ship component at the specified position; <code>null</code> if the specified position is outside of ship
	 * bounds
	 */
	public ShipComponent getComponentAt(final float x, final float y) {
		double xRelativeToShip = getXRelativeToShip(x);
		double yRelativeToShip = getYRelativeToShip(y);

		if (xRelativeToShip < 0 || xRelativeToShip >= width || yRelativeToShip < 0 || yRelativeToShip >= height) {
			return null;
		}

		return components[(int) xRelativeToShip][(int) yRelativeToShip];
	}

	/**
	 * Checks if an incoming attacked missed or not.
	 *
	 * @return true if an attcked missed
	 */
	public boolean successfullyDodged() {
		return rng.nextDouble() < dodgeRate;
	}


	/**
	 * Draws this star ship with the specified scaling.
	 *
	 * @param g     the Graphics object with which to draw this star ship
	 * @param scale the scale with which to scale virtual positions to get on-screen positions
	 */
	public void draw(final Graphics g, final float scale) {
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				ShipComponent componentToDraw = components[col][row];
				if (componentToDraw != null) {
					componentToDraw.draw(g, scale, x + col, y + row);
				}
			}
		}
		final int shipScreenX = (int) (x * scale);
		final int shipScreenY = (int) (y * scale);
		final int shipRenderedWidth = (int) (width * scale);

		final int integrityBarRenderedWidth = shipRenderedWidth / 2;
		final int integrityBarRenderedHeight = (int) (0.25 * scale);
		final int integrityBarScreenX = shipScreenX + shipRenderedWidth / 2 - integrityBarRenderedWidth / 2;
		final int integrityBarScreenY = shipScreenY - (int) (0.375 * scale);
		final int integrityPerCell = 1;

		StatbarDrawer.drawHorizontal(g, integrityBarScreenX, integrityBarScreenY, integrityBarRenderedWidth,
									 integrityBarRenderedHeight, integrity, maxIntegrity, integrityPerCell, Color.RED);
	}

	/**
	 * Adds the specified component at the specified internal positon. Registers the component with this ship.
	 *
	 * @param componentToPlace the component to add
	 * @param col              column in which to add the component
	 * @param row              row in which to add the component
	 */
	public void setComponentInternal(final ShipComponent componentToPlace, final int col, final int row) {
		if (col < 0 || col >= width || row < 0 || row >= height) {
			String message = "The specified position x = " + col + ", y = " + row + " is out of bounds.";
			IllegalArgumentException exception = new IllegalArgumentException(message);
			Logger.getGlobal().log(Level.FINE, message, exception);
			return;
		}
		ShipComponent alreadyPlacedComponent = components[col][row];


		if (componentToPlace == null) {
			if (alreadyPlacedComponent != null) {
				alreadyPlacedComponent.deregisterOwner();
				numberOfComponents--;
			}
		} else {
			componentToPlace.registerOwner(this);
			if (alreadyPlacedComponent == null) {
				numberOfComponents++;
			} else {
				alreadyPlacedComponent.deregisterOwner();
			}
		}
		components[col][row] = componentToPlace;

	}

	/**
	 * Adds the specified component at the specified virtual environment position. Registers the component with this ship.
	 *
	 * @param componentToPlace the component to add
	 * @param vx               the virtual x position at which to add the component
	 * @param vy               the virtual y position at which to add the component
	 */
	public void setComponentExternal(final ShipComponent componentToPlace, final float vx, final float vy) {
		if (vx < x || vx >= x + width || vy < y || vy >= y + height) {
			String message = "The specified position x = " + vx + ", y = " + vy + " is out of ship bounds.";
			IllegalArgumentException exception = new IllegalArgumentException(message);
			Logger.getGlobal().log(Level.FINE, message, exception);
			return;
		}

		setComponentInternal(componentToPlace, (int) getXRelativeToShip(vx), (int) getYRelativeToShip(vy));

	}


	/**
	 * Updates the ships status by going through its components.
	 */
	public void update(float deltaSeconds) {
		projectilesToFire.clear();
		updatePools();
		updateShields();
		for (ShipComponent[] scArray : components) {
			for (ShipComponent sc : scArray) {
				if (sc != null) {
					sc.update();
				}
			}
		}
		for (WeaponComponent wc : weaponComponents) {
			wc.updateWeapon(deltaSeconds);
			Projectile p = wc.getProjectileToFire();
			if (p != null) {
				projectilesToFire.add(p);
			}
		}
	}

	/**
	 * @return projetiles the ship wants to fire this tick
	 */
	public Collection<Projectile> getProjectilesToFire() {
		return projectilesToFire;
	}

	/**
	 * Updates all the ShieldComponents
	 */
	public void updateShields() {
		for (ShieldComponent sc : shieldComponents) {
			sc.update();
		}
	}

	/**
	 * Updates the Ship's resource pools.
	 */
	private void updatePools() {
		shieldingPool = 0;
		for (ShieldComponent shield : shieldComponents) {
			shieldingPool += (int) shield.getOutput();
		}
		stripShielding();

		powerPool = 0;
		for (ReactorComponent rc : reactorComponents) {
			powerPool += (int) rc.getOutput();
		}
		stripPower();

		int totalEnginePower = 0;
		dodgeRate = 0;
		for (EngineComponent ec : engineComponents) {
			dodgeRate += ec.getOutput();
			totalEnginePower += ec.getPower();
		}

		float totalMaxPower = 0;
		for (ShipComponent[] componentCol : components) {
			for (ShipComponent component : componentCol) {
				if (component != null) {
					totalMaxPower += component.getMaxPower();
				}
			}
		}
		dodgeRate *= totalEnginePower / totalMaxPower;
	}

	/**
	 * Strips use of the shielding so that shield usage does not exceed the available shielding from the shielding pool.
	 */
	private void stripShielding() {
		if (usedShielding > shieldingPool) {
			for (ShipComponent[] componentCol : components) {

				if (usedShielding <= shieldingPool) {
					return;
				}

				for (ShipComponent component : componentCol) {
					if (component == null) {
						continue;
					} else if (usedShielding <= shieldingPool) {
						return;
					}

					while (component.hasShielding() && usedShielding > shieldingPool) {
						component.decreaseShielding();
						if (usedShielding <= shieldingPool) {
							return;
						}
					}
				}
			}
		}
	}

	/**
	 * Strips use of the power so that shield usage does not exceed the available power from the power pool.
	 */
	private void stripPower() {
		if (usedPower > powerPool) {
			for (ShipComponent[] componentCol : components) {

				if (usedPower <= powerPool) {
					return;
				}

				for (ShipComponent component : componentCol) {
					if (component == null) {
						continue;
					} else if (usedPower <= powerPool) {
						return;
					}

					while (component.hasPower() && usedPower > powerPool) {
						component.decreasePower();
						if (usedPower <= powerPool) {
							return;
						}
					}
				}
			}
		}
	}

	public void setX(float vx) {
		x = vx;
	}

	public void setY(float vy) {
		y = vy;
	}

	private float getXRelativeToShip(float x) {
		return x - this.x;
	}

	private float getYRelativeToShip(float y) {
		return y - this.y;
	}

	/**
	 * Checks whether this ship contains the specified position.
	 *
	 * @param x the x-position
	 * @param y the y-position
	 *
	 * @return true if his stat bar contains the specified position
	 */
	public boolean contains(float x, float y) {
		return x >= this.x && x <= this.x + this.width &&
			   y >= this.y && y <= this.y + this.height;
	}

	public boolean hasFreePower() {
		return usedPower < powerPool;
	}

	/**
	 * Tries to increase shielding usage.
	 *
	 * @return true if successfull
	 */
	public boolean increaseShieldingUsage() {
		if (usedShielding < shieldingPool) {
			usedShielding++;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Tries to increase power usage.
	 *
	 * @return true if successfull
	 */
	public boolean increasePowerUsage() {
		if (usedPower < powerPool) {
			usedPower++;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Tries to decrease shielding usage.
	 *
	 * @return true if successfull
	 */
	public boolean decreaseShieldingUsage() {
		if (usedShielding > 0) {
			usedShielding--;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Tries to decrease power usage.
	 *
	 * @return true if successfull
	 */

	public boolean decreasePowerUsage() {
		if (usedPower > 0) {
			usedPower--;
			return true;
		} else {
			return false;
		}
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(final Team team) {
		this.team = team;
	}

	public void registerShieldComponent(final ShieldComponent shield) {
		shieldComponents.add(shield);
	}

	public void registerReactorComponent(final ReactorComponent reactor) {
		reactorComponents.add(reactor);
	}

	public void registerEngineComponent(final EngineComponent engine) {
		engineComponents.add(engine);
	}

	public void registerWeaponComponent(final WeaponComponent weapon) {
		weaponComponents.add(weapon);
	}

	public void deregisterShieldComponent(final ShieldComponent shield) {
		shieldComponents.remove(shield);
	}

	public void deregisterReactorComponent(final ReactorComponent reactor) {
		reactorComponents.remove(reactor);
	}

	public void deregisterEngineComponent(final EngineComponent engine) {
		engineComponents.remove(engine);
	}

	public void deregisterWeaponComponent(final WeaponComponent weapon) {
		weaponComponents.remove(weapon);
	}

	/**
	 * @return Returns a 1D ArrayList of all non null components the ship has.
	 */
	public List<ShipComponent> getShipComponents() {
		List<ShipComponent> shipComponents = new ArrayList<>();
		for (ShipComponent[] componentList : components) {
			for (ShipComponent sc : componentList) {
				if (sc != null) {
					shipComponents.add(sc);
				}
			}
		}
		return shipComponents;
	}

	/**
	 * @return a List of all WeaponComponents the ship has.
	 */
	public Iterable<WeaponComponent> getWeaponComponents() {
		Collection<WeaponComponent> weaponComponents = new ArrayList<>();
		for (ShipComponent[] componentList : components) {
			for (ShipComponent sc : componentList) {
				if (sc instanceof WeaponComponent) {
					weaponComponents.add((WeaponComponent) sc);
				}
			}
		}
		return weaponComponents;
	}

	/**
	 * @return a non null random ShipComponent from the ship unless there is no components in the ship, then null will be
	 * returned.
	 */
	public ShipComponent getRandomComponent() {
		List<ShipComponent> shipComponents = getShipComponents();

		if (shipComponents.isEmpty()) {
			return null;
		}

		return shipComponents.get(rng.nextInt(shipComponents.size()));
	}

	public String getTextRepresentation() {
		StringBuilder textRep = new StringBuilder();
		textRep.append("width=");
		textRep.append(width);
		textRep.append(", height=");
		textRep.append(height);
		textRep.append(", integrity=");
		textRep.append(integrity);
		textRep.append(", maxIntegrity=");
		textRep.append(maxIntegrity);
		textRep.append("; \n");

		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				ShipComponent component = components[col][row];
				if (component != null) {
					textRep.append(component.getSymbolRepresentation());
				} else {
					textRep.append(".");
				}
			}
			boolean lastRow = row == height - 1;
			if (lastRow) {
				textRep.append(";");
			} else {
				textRep.append(",\n");
			}
		}
		return textRep.toString();
	}

	/**
	 * Rotates the ship 180 degrees.
	 */
	public void rotate180() {
		int halfHeight = (height + 1) / 2;

		for (int row = 0; row < halfHeight; row++) {
			int invRow = height - 1 - row;

			for (int col = 0; col < width; col++) {
				int invCol = width - 1 - col;

				ShipComponent temp = components[col][row];
				components[col][row] = components[invCol][invRow];
				components[invCol][invRow] = temp;
			}
		}
	}

	/**
	 * Restores all components to max integrity and removes all power or shield.
	 */
	public void restore() {
		integrity = maxIntegrity;
		for (ShipComponent[] row : components) {
			for (ShipComponent col : row) {
				if (col != null) {
					col.restore();
				}

			}
		}
	}

	public void clearComponents() {
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				components[col][row] = null;
			}
		}
	}

	public boolean isEmpty() {
		return numberOfComponents == 0;
	}

	@Override public boolean equals(final Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		final Starship starship = (Starship) o;

		if (Float.compare(starship.maxIntegrity, maxIntegrity) != 0) { return false; }
		if (!Arrays.deepEquals(components, starship.components)) { return false; }

		return true;
	}
}
