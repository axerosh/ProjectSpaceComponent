package shipcomponents;

import game.StarShip;
import game.VisibleEntity;

import java.awt.*;

/**
 * Interface defining the ship component and general functions including once for recieving damage and activation.
 * Has stats and a stat bar for them.
 */
public interface ShipComponent extends VisibleEntity {

    /**
     * Damages this ship component by reducing its HP by the specified number of hit points.
     *
     * @param damage the number of hit points by which this ship component's HP is reduced
     */
    public void inflictDamage(int damage);

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
     * Tries to change the shielding of the component by the specified amount.
     *
	 * @param change amount with which the shielding is to be changed
     * @return true if successfull, false if shielding is at max value
     */
    public boolean changeShielding(int change);

	/**
	  * Tries to change the power of the component by the specified amount.
	  *
	  * @param change amount with which the power is to be changed
	  * @return true if successfull, false if power is at max value
	  */
	 public boolean changePower(int change);

	/**
	 * Tries to change the stat, which indicator bar is at the specified virtual position relative to this ship component,
	 * with the specified amount.
	 *
	 * @param rx a virtual x-position relative to this ship component
	 * @param ry a virtual y-position relative to this ship component
	 * @param change amount with which the stat is to be changed
	 */
    public void changeStatIndicatedAt(final float rx, final float ry, int change);

    /**
     * @return true if this shipComponent is shielded.
     */
    public boolean hasShield();

    /**
     *
     * @return true if this shipComponent recieves power.
     */
    public boolean hasPower();

    /**
     * Updates the component
     */
    public void update();

    /**
     * Activates the component
     */
    public void activate();

    /**
     * Deactivates the component
     */
    public void deactivate();

    /**
     * @return true if component is active
     */
    public boolean isActive();

    /**
     * Registers this component with the specified ship, indicating it's functionality.
     *
     * @param ship the ship with which this component is registered.
     */
    public void registerFunctionality(StarShip ship);
}
