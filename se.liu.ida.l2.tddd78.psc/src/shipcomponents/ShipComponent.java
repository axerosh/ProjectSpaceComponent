package shipcomponents;

import game.StarShip;

import java.awt.*;

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
	 * @param virtualX the virtual x-position at which the ship component is to be drawn.
	 * @param virtualY the virtual y-position at which the ship component is to be drawn.
     */
    public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY);

    /**
     * Tries to increase the shielding of the component by one.
     *
     * @return true if successfull
     */
    public boolean increaseShielding();

    /**
     * Tries to decrease the shielding of the component by one.
     *
     * @return true if successfull
     */
    public boolean decreaseShielding();

    /**
     * Tries to increase the power yo the component by one.
     *
     * @return true if successfull
     */
    public boolean increasePower();

    /**
     * Tries to decrease the power to the component by one.
     *
     * @return true if successfull
     */
    public boolean decreasePower();

	/**
	 *
	 * @return true if this shipComponent is shielded.
	 */
    public boolean hasShield();

	/**
	 *
	 * @return true if this shipComponent recieves power.
	 */
    public boolean hasPower();

	/**
	 * Registers this component with the specified ship, indicating it's functionality.
	 *
	 * @param ship the ship with which this component is registered.
	 */
	public void registerFunctionality(StarShip ship);
}
