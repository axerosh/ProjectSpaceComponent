package ship.component;

import ship.Starship;

import java.awt.Graphics;

/**
 * A ship component with integrity (damage that it can take before it is destroyed), shielding (damage reduction) and power.
 * The component can be selected and deselected and added to a Starship
 * Can be drawn with with some of its stats depicted with a graphical representation.
 *
 * @see Starship
 */
public interface ShipComponent {

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
	 * Sets the activation status of the compoennt to the given value
	 *
	 * @param flag the new activation status of the compoenent. <code>true</code> to active the component
	 */
	public void setActive(boolean flag);

	/**
	 * Sets the selection status of the compoennt to the given value
	 *
	 * @param flag the new selection status of the compoenent. <code>true</code> to active the component
	 */
	public void setSelected(boolean flag);

	/**
	 * Registers this component with the specified ship, indicating that the component is a part of the specified ship and
	 * registers special ship component type functionality with the ship.
	 *
	 * @param owner the ship with which this component is registered.
	 */
	public void registerOwner(final Starship owner);

	/**
	 * Removes any registrated owner of the component.
	 */
	public void deregisterOwner();

	/**
	 * Increases shielding unless it is at maximum capacity or if there is no registered ship or no available shielding from
	 * the
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
	 * @return the symbol that represents a component of this type
	 */
	public char getSymbolRepresentation();

	/**
	 * @return the ships power level
	 */
	public int getPower();

	/**
	 * @return true if the component has integrity left
	 */
	public boolean isIntact();

	/**
	 * Restores the components to max integrity and strips all power and shielding
	 */
	public void restore();

	/**
	 * @return the maximum power that this component can be supplied.
	 */
	public int getMaxPower();

	public ShipComponent copy();
}
