package ship.component;

import graphics.StatbarDrawer;
import ship.Starship;

import java.awt.*;

/**
 * A general shipComponent.
 *
 * @see ShipComponent
 */
public abstract class AbstractShipComponent implements ShipComponent, Cloneable {

	/**
	 * The maximum level of shielding a ship component may recieve.
	 */
	//Static because the maximum shielding is the same for all ship components
	public static final int MAXSHIELDING = 6;
	//Static because the highlight color is the same for all ship components
	private final static Color HIGHLIGHT_COLOR = Color.YELLOW;
	/**
	 * The maximum integrity of this ship component. The damage it can take before it is destroyed.
	 */
	protected final float maxIntegrity;
	private final char symbolRepresentation;
	private final Color color;
	/**
	 * The maximum level of power a scomponent may recieve.
	 */
	private final int maxPower;
	private int shielding;
	private int power;
	/**
	 * The current integrity left until destruction. The remaining damage it can take before it is destroyed.
	 */
	private float integrity;
	private boolean active;
	private boolean selected;
	private Starship owner;

	/**
	 * Construcs an abstract ship component with the specified maximum HP.
	 *
	 * @param integrity            the damage the ship component can take before it is destroyed
	 * @param symbolRepresentation the character that is to represent this component
	 * @param color                the color with which the component is drawn
	 *
	 * @throws IllegalArgumentException if the specified integrity is negative
	 */
	protected AbstractShipComponent(final float integrity, final int maxPower, final char symbolRepresentation,
									final Color color) throws IllegalArgumentException {
		if (integrity < 0) {
			throw new IllegalArgumentException("The specified integrity " + integrity + " is negative.");
		}
		this.integrity = integrity;
		maxIntegrity = integrity;
		this.symbolRepresentation = symbolRepresentation;
		this.color = color;
		owner = null;
		shielding = 0;
		power = 0;
		this.maxPower = maxPower;
		active = true;
		selected = false;
	}

	@Override public void registerOwner(final Starship owner) {
		this.owner = owner;
	}

	@Override public void deregisterOwner() {
		owner = null;
	}

	@Override public void inflictDamage(float damage) {
		float damageTaken = damageThroughShield(damage);
		integrity -= damageTaken;
		integrity = Math.max(integrity, 0);
		if (owner != null) {
			owner.inflictDamage(damage);
		}
	}

	public Starship getOwner() {
		return owner;
	}

	/**
	 * @param damage the damage taken if not for the shielding
	 *
	 * @return the damage that the component will take after its shield has reduced it
	 */
	private float damageThroughShield(float damage) {
		final float shieldRatePerShielding = 0.15f;
		float shieldRate = shieldRatePerShielding * shielding;
		return damage * (1 - shieldRate);
	}

	/**
	 * Draws this ship component with the specified scaling and the specified color.
	 *
	 * @param g        the Graphics object with which to draw this ship component
	 * @param scale    the scale with which to scale virtual positions to get on-screen positions
	 * @param virtualX the virtual x-position at which the ship component is to be drawn.
	 * @param virtualY the virtual y-position at which the ship component is to be drawn.
	 */
	public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY) {
		g.setColor(color);
		int screenX = (int) (virtualX * scale);
		int screenY = (int) (virtualY * scale);
		int pixelsAcross = (int) scale;
		g.fillRect(screenX, screenY, pixelsAcross, pixelsAcross);

		final int maxAlpha = 255;
		int alpha = Math.round((maxAlpha * (1 - integrity / maxIntegrity)));
		Color transparentBlackOverlay = new Color(0, 0, 0, alpha);
		g.setColor(transparentBlackOverlay);
		g.fillRect(screenX, screenY, pixelsAcross, pixelsAcross);

		int numberOfBars = 2;
		int numberOfGaps = numberOfBars - 1;
		int gapBetweenBars = pixelsAcross / 10;
		int barHeight = pixelsAcross / 5;
		int barWidth = 3 * barHeight;

		int totalBarsHeight = (numberOfBars * barHeight + numberOfGaps * gapBetweenBars);
		int barX = screenX + (pixelsAcross - barWidth) / 2;
		int shieldBarY = screenY + (pixelsAcross - totalBarsHeight) / 2;
		int powerBarY = shieldBarY + barHeight + gapBetweenBars;

		int levelsPerCell = 1;
		if (hasShielding()) {
			StatbarDrawer.drawHorizontal(g, barX, shieldBarY, barWidth, barHeight, shielding, MAXSHIELDING, levelsPerCell,
										 Color.CYAN);
		}
		if (hasPower()) {
			StatbarDrawer.drawHorizontal(g, barX, powerBarY, barWidth, barHeight, power, maxPower, levelsPerCell, Color.GREEN);
		}

		if (selected) {

			g.setColor(HIGHLIGHT_COLOR);
			g.drawRect(screenX, screenY, pixelsAcross - 1, pixelsAcross - 1);
		}
	}

	/**
	 * Increases shielding unless it is at maximum capacity or if there is no registered ship or no available shielding from
	 * the
	 * registered ship. If shielding is increased, drains the shielding pool of the ship. Requests a visual updateMovement if
	 * shielding was increased.
	 *
	 * @see #registerOwner
	 */
	@Override public void increaseShielding() {
		if (owner != null) {

			if (shielding < MAXSHIELDING) {
				if (owner.increaseShieldingUsage()) {
					shielding++;
				}
			}
		}
	}

	/**
	 * Increases power unless it is at maximum capacity or if there is no registered ship or no available power from the
	 * registered ship. If power is increased, drains the shielding pool of the ship. Requests a visual updateMovement if power
	 * was increased.
	 *
	 * @see #registerOwner
	 */
	@Override public void increasePower() {
		if (owner != null) {

			if (power < maxPower) {
				if (owner.increasePowerUsage()) {
					power++;
				}
			}
		}
	}

	/**
	 * Decreases shielding unless it is at minimum capacity. If shielding is decreased, lets loose power to the shielding pool
	 * of the registered ship. Requests a visual updateMovement if shielding was decreased.
	 *
	 * @see #registerOwner
	 */
	@Override public void decreaseShielding() {
		if (owner != null) {

			if (shielding > 0) {
				if (owner.decreaseShieldingUsage()) {
					shielding--;
				}
			}
		}
	}

	/**
	 * Decreases power unless it is at minimum capacity. If Power is decreased, lets loose power to the power pool of the
	 * registered ship. Requests a visual updateMovement if power was decreased.
	 *
	 * @see #registerOwner
	 */
	@Override public void decreasePower() {
		if (owner != null) {

			if (power > 0) {
				if (owner.decreasePowerUsage()) {
					power--;
				}
			}
		}
	}

	@Override public char getSymbolRepresentation() {
		return symbolRepresentation;
	}

	@Override public boolean hasShielding() {
		return shielding > 0;
	}

	@Override public boolean hasPower() {
		return power > 0;
	}

	public boolean isIntact() {
		return integrity > 0;
	}

	public int getPower() {
		return power;
	}

	@Override public int getMaxPower() {
		return maxPower;
	}

	/**
	 * @return true if component is active
	 */
	public boolean isActive() {
		return active;
	}

	@Override public void setActive(final boolean flag) {
		active = flag;
	}

	@Override public void setSelected(final boolean flag) {
		selected = flag;
	}

	@Override public String toString() {
		return (this.getClass() + " HP = " + integrity + ", Shielding = " + shielding + ", Power = " + power);
	}

	@Override public void restore() {
		integrity = maxIntegrity;
		active = true;

		while (hasPower()) {
			decreasePower();
		}
		while (hasShielding()) {
			decreaseShielding();
		}
	}

	@Override public ShipComponent copy() {
		return null;
	}

	@Override public boolean equals(final Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }

		final AbstractShipComponent that = (AbstractShipComponent) o;

		if (Float.compare(that.maxIntegrity, maxIntegrity) != 0) { return false; }
		if (symbolRepresentation != that.symbolRepresentation) { return false; }
		if (maxPower != that.maxPower) { return false; }
		if (color != null ? !color.equals(that.color) : that.color != null) { return false; }

		return true;
	}
}
