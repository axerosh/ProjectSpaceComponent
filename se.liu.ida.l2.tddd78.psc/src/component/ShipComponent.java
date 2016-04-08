package component;

import game.Starship;
import game.VisibleEntity;

import java.awt.*;

/**
 * A ship component with integrity (damage that it can take before it is destroyed), shielding (damage reduction) and power. Can
 * be drawn with with some of its stats depicted with a graphical representation.
 *
 * @see Starship
 */
public interface ShipComponent extends VisibleEntity
{

	/**
	 * Damages this ship component by reducing its HP by the specified number of hit points.
	 *
	 * @param damage the number of hit points by which this ship component's HP is reduced
	 */
	public void inflictDamage(float damage);

	/**
	 * Draws this ship component with the specified scaling.
	 *
	 * @param g        the Graphics object with which to draw this ship component
	 * @param scale    the scale with which to scale virtual positions to get on-screen positions
	 * @param virtualX the virtual x-position at which the ship component is to be drawn.
	 * @param virtualY the virtual y-position at which the ship component is to be drawn.
	 */
	public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY);

	/**
	 * @return true if this shipComponent is shielded.
	 */
	public boolean hasShielding();

	/**
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
	 * Registers this component with the specified ship, indicating that the component is a part of the specified ship and
	 * registers special ship component type functionality with the ship.
	 *
	 * @param owner the ship with which this component is registered.
	 */
	public void registerOwner(final Starship owner);

	/**
	 * Increases shielding unless it is at maximum capacity or if there is no registered ship or no available shielding from the
	 * registered ship. If shielding is increased, drains the shielding pool of the ship.
	 *
	 * @see #registerOwner
	 */
	public void increaseShielding();

	/**
	 * Increases power unless it is at maximum capacity or if there is no registered ship or no available power from the
	 * registered ship. If power is increased, drains the shielding pool of the ship.
	 *
	 * @see #registerOwner
	 */
	public void increasePower();

	/**
	 * Decreases shielding unless it is at minimum capacity. If shielding is decreased, lets loose power to the shielding pool
	 * of the registered ship.
	 *
	 * @see #registerOwner
	 */
	public void decreaseShielding();

	/**
	 * Decreases power unless it is at minimum capacity. If Power is decreased, lets loose power to the power pool of the
	 * registered ship.
	 *
	 * @see #registerOwner
	 */
	public void decreasePower();

	/**
	 * @return true if needs target for activation effect.
	 * @see #activate()
	 * @see #registerOwner
	 */
	public boolean needsTarget();
}