package shipcomponents;

import java.awt.Graphics;

/**
 * Interface defining the ship component and general functions including once for recieving damage and activation.
 */
public interface ShipComponent {

    /**
     * Damages this ship component by reducing its HP by the specified number of hit points.
     *
     * @param damage the number of hit points by which this ship component's HP is reduced
     */
    public void inflictDamage(int damage);

    /**
     * Activates this ship component, performing its component-type specific action.
     */
    public void performAction();

    /**
     * Draws this ship component with the specified scaling.
     *
     * @param g the Graphics object with which to draw this ship component
     * @param scale the scale with which to scale virtual positions to get on-screen positions
     */
    public void draw(final Graphics g, final float scale, final float virtualX, final float cirtualY);

    /**
     * Increases the shielding of the component by one.
     */
    public void increaseShielding();

    /**
     * Decreases the shielding of the component by one.
     */
    public void decreaseShielding();

    /**
     * Increases the power to the component by one.
     */
    public void increasePower();

    /**
     * Decreases the power to the component by one.
     */
    public void decreasePower();
}
