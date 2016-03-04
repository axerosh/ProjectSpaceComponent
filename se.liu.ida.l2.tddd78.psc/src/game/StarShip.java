package game;

import projectiles.Projectile;
import shipcomponents.ShipComponent;
import shipcomponents.utilitycomponents.EngineComponent;
import shipcomponents.utilitycomponents.ReactorComponent;
import shipcomponents.utilitycomponents.ShieldComponent;
import shipcomponents.weaponscomponents.Weapon;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A star ship consisting of ship components.
 *
 * @see ShipComponent
 */
public class StarShip extends GeneralVisibleEntity {

    private float x;
    private float y;
    private int width;
    private int height;

	/**
	 * The virtual width of a ship component.
	 */
	private static final int COMPONENT_WDITH = 1;

    private List<ShieldComponent> shieldComponents;
    private List<ReactorComponent> reactorComponents;
    private List<EngineComponent> engineComponents;
    private List<Weapon> weapons;
    private List<Projectile>firedProjectiles;
    private int shieldPool;
    private int usedShielding;
    private int powerPool;
    private int usedPower;

    /**
     * The dodge rate of this ship. The rate of which projectiles will miss the ship.
     *
     * @see Projectile
     */
    private float dodgeRate = 0;

    /**
     * A grid of this star ship's ship components.
     */
    private ShipComponent[][] components;

    /**
     * Constructs a star ship with the specifed position, width and height.
     *
     * @param x the x-position of the ship
     * @param y the y-position of the ship
     * @param width the width of the ship i.e. the number of ship components that can fit along the width
     * @param height the height of the ship i.e. the number of ship components that can fit along the height
     * @throws IllegalArgumentException if specified width and/or the specified height are negative or 0
     * @see ShipComponent
     */
    public StarShip(final float x, final float y, final int width, final int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Invalid ship dimensions width = " + width + ", height = " + height + ". " +
                                               "Only positive integers are permitted.");
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

	shieldPool = 0;
	powerPool = 0;
	usedShielding = 0;
	usedPower = 0;
	shieldComponents = new ArrayList<>();
	reactorComponents = new ArrayList<>();
	engineComponents = new ArrayList<>();

	weapons = new ArrayList<>();
	firedProjectiles = new ArrayList<>();

        components = new ShipComponent[width][height];
    }

    /**
     * Returns the ship component at the specified position.
     *
     * @param x the x-coordinate of the position
     * @param y the y-coordinate of the position
     * @return the ship component at the specified position;
     * <code>null</code> if the specified position is outside of ship bounds
     */
    public ShipComponent getComponentAt(final float x, final float y) {
        double xRelativeToShip = getXRelativeToShip(x);
        double yRelativeToShip = getYRelativeToShip(y);

        if (xRelativeToShip < 0 || xRelativeToShip >= width || yRelativeToShip < 0 || yRelativeToShip >= height) {
            return null;
        }

        return components[(int)xRelativeToShip][(int)yRelativeToShip];
    }

    /**
     * Checks if an incoming attacked missed or not.
     *
     * @return true if an attcked missed
     */
    public boolean successfullyDodged() {
        return new Random().nextDouble() > dodgeRate;
    }


    /**
     * Draws this star ship with the specified scaling.
     *
     * @param g the Graphics object with which to draw this star ship
     * @param scale the scale with which to scale virtual positions to get on-screen positions
     */
    public void draw(final Graphics g, final float scale) {
        for (int col = 0; col < width; col ++) {
            for (int row = 0; row < height; row ++) {
				ShipComponent componentToDraw = components[col][row];
				if (componentToDraw != null) {
					componentToDraw.draw(g, scale, x + col, y + row);
				}
            }
        }
    }

    /**
     * Adds the specified component at the specified position.
     *
     * @param component the component to add
     * @param col column in which to add the component
     * @param row row in which to add the component
     */
    public void setComponent(final ShipComponent component, final int col, final int row) {
		if (col < 0 || col >= width || row < 0 || row >= height) {
			throw new IllegalArgumentException("The specified position x = " + col + ", y = " + row + " is out of bounds.");
		}
		if (component != null) {
			component.registerFunctionality(this);
			for(VisibleEntityListener listener: visibleEntityListeners) {
				component.addVisibleEntityListener(listener);
			}
		}

		components[col][row] = component;
	}

    /**
     * Updates the ships status by going through its components.
     */
    public List<Projectile> update(){
		firedProjectiles = new ArrayList<>();
		updatePools();
		updateShields();
		for(ShipComponent[] scArray : components){
			for (ShipComponent sc : scArray){
			if (sc != null){
				sc.update();
			}
			}
		}
		for (Weapon wc : weapons){
			Projectile p = wc.updateWeapon();
			if(p != null){
			firedProjectiles.add(p);
			}
		}
		return firedProjectiles;
    }



    /**
     * Updates all the ShieldComponents
     */
    public void updateShields(){
	for(ShieldComponent sc : shieldComponents){
	    sc.update();
	}
    }

    /**
     * Updates the Ship's resource pools.
     */
    private void updatePools(){
	shieldPool = 0;
	for(ShieldComponent shield : shieldComponents){
	    shieldPool += shield.getOutput();
	}
	stripShielding();

	powerPool = 0;
	for(ReactorComponent rc : reactorComponents){
	    powerPool += rc.getOutput();
	}
	stripPower();

	dodgeRate = 0;
	for(EngineComponent ec : engineComponents){
	    dodgeRate += ec.getOutput();
	}
    }

    /**
     * Strips use of the shielding so that shield usage does not exceed the available shielding from the shielding pool.
     *
     */
    private void stripShielding() {
		if(usedShielding > shieldPool){
			for(ShipComponent[] componentCol : components){

				if(usedShielding <= shieldPool){
					return;
				}

				for(ShipComponent component : componentCol){
					if (component == null){
						continue;
					} else if (usedShielding <= shieldPool) {
						return;
					}

					while(component.hasShield() && usedShielding > shieldPool){
						component.changeShielding(-1);
						if(usedShielding <= shieldPool){
							return;
						}
					}
				}
			}
		}
	}

	/**
     * Strips use of the power so that shield usage does not exceed the available power from the power pool.
     *
	 */
 	private void stripPower() {
		if(usedPower > powerPool){
		for(ShipComponent[] componentCol : components){

			if(usedPower <= powerPool){
				return;
			}

			for(ShipComponent component : componentCol){
				if (component == null){
					continue;
				} else if (usedPower <= powerPool) {
					return;
				}

				while(component.hasShield() && usedPower > powerPool){
					component.changePower(-1);
					if(usedPower <= powerPool){
						return;
					}
				}
			}
		}
		}
	}

	/**
	  * Changes the shielding to the component, at the specified virtual position, with the specified amount.
	  *
	  * @param x the x-coordinate of the position
	  * @param y the y-coordinate of the position
	  * @param change amount with which the shielding is to be changed
	  */
	public void changeShielding(final float x, final float y, int change){
		ShipComponent componentToChange = getComponentAt(x,y);
		if (componentToChange != null) {
			if(getComponentAt(x,y).changeShielding(change)){
				usedShielding -= change;
			}
		}
	 }

    /**
     * Changes the power to the component, at the specified virtual position, with the specified amount.
	 *
     * @param x the x-coordinate of the position
     * @param y the y-coordinate of the position
	 * @param change amount with which the power is to be changed
	 */
    public void changePower(final float x, final float y, int change){
		ShipComponent componentToChange = getComponentAt(x,y);
		if (componentToChange != null) {
			if(getComponentAt(x,y).changePower(change)){
				usedPower -= change;
			}
		}
    }

    /**
     * Prints the stats of the ship and then
     * each of the ships components stats.
     * Used only in debugging and testing
     */
    public void printShip(){
		System.out.println("Dodge = " + dodgeRate + ", Shieldpool = " + shieldPool + ", Powerpool = " + powerPool);
		for(ShipComponent[] scArray: components){
			for(ShipComponent sc : scArray){
				System.out.println(sc);
			}
		}
		System.out.println();
    }

	/**
	 * Tries to change the stat, which indicator bar is at the specified virtual position, with the specified amount.
	 *
	 * @param vx a virtual x-position
	 * @param vy a virtual y-position
	 * @param change amount with which the stat is to be changed
	 */
	public void changeStatIndicatedAt(final float vx, final float vy, int change) {
		ShipComponent clickedComponent = getComponentAt(vx, vy);
		if (clickedComponent != null) {
			float xRelativeToComponent = getXRelativeToShip(vx) % COMPONENT_WDITH;
			float yRelativeToComponent = getYRelativeToShip(vy) % COMPONENT_WDITH;
			clickedComponent.changeStatIndicatedAt(xRelativeToComponent, yRelativeToComponent, change);
		}
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
	 * @return true if his stat bar contains the specified position
	 */
	public boolean contains(float x, float y) {
		return x >= this.x && x <= this.x + this.width &&
			   y >= this.y && y <= this.y + this.height;
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

    public void registerWeaponComponent(final Weapon weapon){
	weapons.add(weapon);
    }

	/**
	 * Adds the specified listener to this VisibleEntity and all its Ship Components.
	 *
	 * @param listener the lister to add
	 */
	@Override public void addVisibleEntityListener(final VisibleEntityListener listener) {
		super.addVisibleEntityListener(listener);
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				VisibleEntity visibleComponent = components[col][row];
				if (visibleComponent != null) {
					visibleComponent.addVisibleEntityListener(listener);
					System.out.println("Listener added to " + visibleComponent);
				}
			}
		}
	}
}
