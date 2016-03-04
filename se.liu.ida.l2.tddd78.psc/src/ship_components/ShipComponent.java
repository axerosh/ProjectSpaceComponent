package ship_components;

import game.Starship;
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
    public void inflictDamage(float damage);

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
     * @return true if this shipComponent is shielded.
     */
    public boolean hasShielding();

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
    public void registerFunctionality(Starship ship);

	/**
	 * Increases shielding unless it is at maximum capacity.
	 *
	 * @return true if shielding was increased, false if it was not
	 */
	public boolean increaseShielding();

	/**
	 * Decreases shielding unless it is at minimum capacity.
	 *
	 * @return true if shielding was decreased, false if it was not
	 */
	public boolean decreaseShielding();

	/**
	 * Increases power unless it is at maximum capacity.
	 *
	 * @return true if power was increased, false if it was not
	 */
	public boolean increasePower();

	/**
	 * Decreases power unless it is at minimum capacity.
	 *
	 * @return true if power was decreased, false if it was not
	 */
	public boolean decreasePower();
}
