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
public class StarShip {

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
			component.registerFunctionality(this);

		components[col][row] = component;
	}

    /**
     * Updates the ships status by going through its components.
     */
    public void update(){
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
	    wc.updateWeapon();
	}
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
	stripPoolUsage(shieldPool, usedShielding);

	powerPool = 0;
	for(ReactorComponent rc : reactorComponents){
	    powerPool += rc.getOutput();
	}
	stripPoolUsage(powerPool, usedPower);

	dodgeRate = 0;
	for(EngineComponent ec : engineComponents){
	    dodgeRate += ec.getOutput();
	}
    }

    /**
     * Strips use of the resources from the specified pool so that usage does not over exceed availability
     *
     * @param pool a pool of available resources
     * @param poolUsage the number of resources, from the specified pool, that is used
     */
    private void stripPoolUsage(int pool, int poolUsage) {
	if(poolUsage > pool){
	    for(ShipComponent[] componentCol : components){

		if(poolUsage <= pool){
		    return;
		}

		for(ShipComponent component : componentCol){
		    if (component == null){
			    continue;
		    } else if (poolUsage <= pool) {
			return;
		    }

		    while(component.hasShield() && poolUsage > pool){
			component.decreaseShielding();
			if(poolUsage <= pool){
				return;
			}
		    }
		}
	    }
	}
    }

    /**
     * Increases the shielding of the component at the position.
     * @param x the x-coordinate of the position
     * @param y the y-coordinate of the position
     */
    public void increaseShielding(final float x, final float y){
		if(shieldPool > usedShielding){
			if(getComponentAt(x,y).increaseShielding()){
				usedShielding++;
			}
		}
    }

    /**
     * Decreases the shielding of the component at the position.
     * @param x the x-coordinate of the position
     * @param y the y-coordinate of the position
     */
    public void decreaseShielding(final float x, final float y){
	if(getComponentAt(x,y).decreaseShielding()){
	    usedShielding--;
	}
    }

    /**
     * Increases the power to the component at the position.
     * @param x the x-coordinate of the position
     * @param y the y-coordinate of the position
     */
    public void increasePower(final float x, final float y){
		if(powerPool > usedPower){
			if(getComponentAt(x,y).increasePower()){
				usedPower++;
			}
		}
    }

    /**
     * Decreases the power to the component at the position.
     * @param x the x-coordinate of the position
     * @param y the y-coordinate of the position
     */
    public void decreasePower(final float x, final float y){
		if(getComponentAt(x,y).decreasePower()){
			usedPower--;
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
	 * Performs the activation action of the ship component that the cursor hovers over.
	 *
	 * @param vx the cursor's virtual x-position
	 * @param vy the cursor's virtual y-position
	 */
	public void activateWithCursor(final float vx, final float vy) {
		System.out.println("Starship recieved activation at virtual position x = " + vx + ", y = " + vy);
		ShipComponent clickedComponent = getComponentAt(vx, vy);
		if (clickedComponent != null) {
			float xRelativeToComponent = getXRelativeToShip(vx) % COMPONENT_WDITH;
			float yRelativeToComponent = getYRelativeToShip(vy) % COMPONENT_WDITH;
			clickedComponent.activateWithCursor(xRelativeToComponent, yRelativeToComponent);
		}
	}

	/**
	 * Performs the deactivation action of the ship component that the cursor hovers over.
	 *
	 * @param vx the cursor's virtual x-position
	 * @param vy the cursor's virtual y-position
	 */
	public void deactivateWithCursor(final float vx, final float vy) {
		System.out.println("Starship recieved deactivation at virtual position x = " + vx + ", y = " + vy);
		ShipComponent clickedComponent = getComponentAt(vx, vy);
		if (clickedComponent != null) {
			float xRelativeToComponent = getXRelativeToShip(vx) % COMPONENT_WDITH;
			float yRelativeToComponent = getYRelativeToShip(vy) % COMPONENT_WDITH;
			clickedComponent.deactivateWithCursor(xRelativeToComponent, yRelativeToComponent);
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
}
