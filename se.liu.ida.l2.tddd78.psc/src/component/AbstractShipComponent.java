package component;

import game.GeneralVisibleEntity;
import game.Starship;
import graphics.Statbar;

import java.awt.Color;
import java.awt.Graphics;

/**
 * A generalship component. Handles general ship component functionality including integrity, shielding and power as well as
 * graphical projection of itself and some of its stats.
 */
public abstract class AbstractShipComponent extends GeneralVisibleEntity implements ShipComponent {

	/**
	 * The maximum level of shielding a ship component may recieve.
	 */
	public static final int MAXSHIELDING = 6;
	/**
	 * The maximum level of power a ship component may recieve.
	 */
	public static final int MAXPOWER = 6;
	/**
	 * The maximum integrity of this ship component. The damage it can take before it is destroyed.
	 */
	private final float maxIntegrity;
	private int shielding;
	private int power;
	/**
	 * The current integrity left until destruction. The remaining damage it can take before it is destroyed.
	 */
	private float integrity;
	private boolean active;
	private boolean needsTarget;
	private Starship owner;

	/**
	 * Construcs an abstract ship component with the specified maximum HP.
	 *
	 * @param integrity   the damage the ship component can take before it is destroyed
	 * @param needsTarget set to true if the components activation requires a target.
	 *
	 * @see #activate()
	 */
	protected AbstractShipComponent(final float integrity, final boolean needsTarget) {
		this.integrity = integrity;
		maxIntegrity = integrity;
		this.needsTarget = needsTarget;
		owner = null;
		shielding = 0;
		power = 0;
		active = true;
	}

	@Override public void registerOwner(final Starship owner) {
		this.owner = owner;
	}

	@Override public void inflictDamage(float damage) {
		integrity -= damageThroughShield(damage);
		integrity = Math.max(integrity, 0);
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

	@Override public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY) {
		draw(g, scale, virtualX, virtualY, Color.GRAY);
	}

	/**
	 * Draws this ship component with the specified scaling and the specified color.
	 *
	 * @param g        the Graphics object with which to draw this ship component
	 * @param scale    the scale with which to scale virtual positions to get on-screen positions
	 * @param virtualX the virtual x-position at which the ship component is to be drawn.
	 * @param virtualY the virtual y-position at which the ship component is to be drawn.
	 * @param color    the color with which to draw this ship component.
	 */
	public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY, Color color) {
		g.setColor(color);
		int screenX = (int) (virtualX * scale);
		int screenY = (int) (virtualY * scale);
		int pixelsAcross = (int) scale;
		g.fillRect(screenX, screenY, pixelsAcross, pixelsAcross);

		final int maxAlpha = 255 / 2;
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
			Statbar.drawHorizontal(g, barX, shieldBarY, barWidth, barHeight, shielding, MAXSHIELDING, levelsPerCell,
								   Color.CYAN);
		}
		if (hasPower()) {
			Statbar.drawHorizontal(g, barX, powerBarY, barWidth, barHeight, power, MAXPOWER, levelsPerCell, Color.GREEN);
		}
	}

	/**
	 * Increases shielding unless it is at maximum capacity or if there is no registered ship or no available shielding from
	 * the
	 * registered ship. If shielding is increased, drains the shielding pool of the ship. Requests a visual update if shielding
	 * was increased.
	 *
	 * @see #registerOwner
	 */
	@Override public void increaseShielding() {
		if (owner != null) {

			if (shielding < MAXSHIELDING) {
				if (owner.increaseShieldingUsage()) {
					shielding++;
					requestVisualUpdate();
				}
			}
		}
	}

	/**
	 * Increases power unless it is at maximum capacity or if there is no registered ship or no available power from the
	 * registered ship. If power is increased, drains the shielding pool of the ship. Requests a visual update if power was
	 * increased.
	 *
	 * @see #registerOwner
	 */
	@Override public void increasePower() {
		if (owner != null) {

			if (power < MAXPOWER) {
				if (owner.increasePowerUsage()) {
					power++;
					requestVisualUpdate();
				}
			}
		}
	}

	/**
	 * Decreases shielding unless it is at minimum capacity. If shielding is decreased, lets loose power to the shielding pool
	 * of the registered ship. Requests a visual update if shielding was decreased.
	 *
	 * @see #registerOwner
	 */
	@Override public void decreaseShielding() {
		if (owner != null) {

			if (shielding > 0) {
				if (owner.decreaseShieldingUsage()) {
					shielding--;
					requestVisualUpdate();
				}
			}
		}
	}

	/**
	 * Decreases power unless it is at minimum capacity. If Power is decreased, lets loose power to the power pool of the
	 * registered ship. Requests a visual update if power was decreased.
	 *
	 * @see #registerOwner
	 */
	@Override public void decreasePower() {
		if (owner != null) {

			if (power > 0) {
				if (owner.decreasePowerUsage()) {
					power--;
					requestVisualUpdate();
				}
			}
		}
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

	@Override public void activate() {
		active = true;
	}

	@Override public void deactivate() {
		active = false;
	}

	@Override public boolean isActive() {
		return active;
	}

	@Override public boolean needsTarget() {
		return needsTarget;
	}

	@Override public String toString() {
		return (this.getClass() + " HP = " + integrity + ", Shielding = " + shielding + ", Power = " + power);
	}
}
