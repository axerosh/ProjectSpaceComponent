import shipcomponents.ShipComponent;

/**
 * A star ship consisting of ship components.
 *
 * @see ShipComponent
 */
public class StarShip {

    private double x;
    private double y;
    private double width;
    private double height;

    /**
     * A grid of this star ship's ship components.
     */
    private ShipComponent[][] components;

    /**
     * Constructs a star ship with the specifed width and height.
     *
     * @param width the width of the ship i.e. the number of ship components that can fit along the width
     * @param height the height of the ship i.e. the number of ship components that can fit along the height
     * @throws IllegalArgumentException if specified width and/or the specified height are negative or 0
     * @see ShipComponent
     */
    public StarShip(final int width, final int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Invalid ship dimensions width = " + width + ", height = " + height + ". " +
                                               "Only positive integers are permitted.");
        }

        components = new ShipComponent[width][height];
        this.width = width;
        this.height = height;
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
}
