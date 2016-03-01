package game;

import shipcomponents.ShipComponent;

import java.awt.*;
import java.util.Random;
import projectiles.Projectile;

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
     * The dodge rate of this ship. The rate of which projectiles will miss the ship.
     *
     * @see Projectile
     */
    private float dodgeRate = 0.25f;

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
    public ShipComponent getComponentAt(final double x, final double y) {
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

    public void setComponent(final ShipComponent component, final int x, final int y) {
	if (x < 0 || x >= width || y < 0 || y >= height) {
	    throw new IllegalArgumentException("The specified position x = " + x + ", y = " + y + " is out of bounds.");
	}

	components[x][y] = component;
    }
}
