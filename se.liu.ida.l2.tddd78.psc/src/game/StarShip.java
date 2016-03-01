package game;

import shipcomponents.ShipComponent;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import projectiles.Projectile;
import shipcomponents.utilitycomponents.EngineComponent;
import shipcomponents.utilitycomponents.ReactorComponent;
import shipcomponents.utilitycomponents.ShieldComponent;

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

    private List<ShieldComponent> shieldComponents;
    private List<ReactorComponent> reactorComponents;
    private List<EngineComponent> engineComponents;
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
        double internalX = x - this.x;
        double internalY = y - this.y;

        if (internalX < 0 || internalX > width || internalY < 0 || internalY > height) {
            return null;
        }

        return components[(int)internalX][(int)internalY];
    }

    /**
     * Checks if an incoming attacked missed or not.
     *
     * @return true if an attcked missed
     */
    public boolean successfullyDodged() {
        return (new Random().nextDouble() > dodgeRate);
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
     * Adds a component at a specified position
     *
     * @param component a component to add
     * @param col column in which to add the component
     * @param row row in which to add the component
     */
    public void setComponent(final ShipComponent component, final int col, final int row) {
	if (col < 0 || col >= width || row < 0 || row >= height) {
	    throw new IllegalArgumentException("The specified position x = " + col + ", y = " + row + " is out of bounds.");
	}

	if(component instanceof ShieldComponent){
	    shieldComponents.add((ShieldComponent)component);
	}else if(component instanceof ReactorComponent){
	    reactorComponents.add((ReactorComponent)component);
	}else if(component instanceof EngineComponent){
	    engineComponents.add((EngineComponent)component);
	}

	components[col][row] = component;
    }

    public void update(){
	updateShields();
	updatePools();
    }

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
	for(ShieldComponent sc : shieldComponents){
	    shieldPool += sc.getOutput();
	}
	if(usedShielding > shieldPool){
	    for(ShipComponent[] shipCArray : components){
		if(usedShielding <= shieldPool){
		    break;
		}

		for(ShipComponent shipC : shipCArray){
		    if(usedShielding <= shieldPool || shipC == null){
			continue;
		    }

		    while(shipC.hasShield()){
			shipC.decreaseShielding();
			if(usedShielding <= shieldPool){
			    break;
			}
		    }
		}
	    }
	}

	powerPool = 0;
	for(ReactorComponent rc : reactorComponents){
	    powerPool += rc.getOutput();
	}

	dodgeRate = 0;
	for(EngineComponent ec : engineComponents){
	    dodgeRate += ec.getOutput();
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


    public void printShip(){
	System.out.println("Dodge = " + dodgeRate + ", Shieldpool = " + shieldPool + ", Powerpool = " + powerPool);
	for(ShipComponent[] scArray: components){
	    for(ShipComponent sc : scArray){
		System.out.println(sc);
	    }
	}
	System.out.println();
    }
}
