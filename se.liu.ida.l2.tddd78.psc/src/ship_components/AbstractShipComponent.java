package ship_components;

import game.GeneralVisibleEntity;
import graphics.StatbarTemp;

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

	/**
	 * Construcs an abstract ship component with the specified maximum HP.
	 *
	 * @param integrity the damage the ship component can take before it is destroyed
	 */
	protected AbstractShipComponent(final float integrity) {
		this.integrity = integrity;
		maxIntegrity = integrity;
		shielding = 0;
		power = 0;
		active = true;
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
			StatbarTemp.drawHorizontal(g, barX, shieldBarY, barWidth, barHeight, shielding, MAXSHIELDING, levelsPerCell,
									   Color.CYAN);
		}
		if (hasPower()) {
			StatbarTemp.drawHorizontal(g, barX, powerBarY, barWidth, barHeight, power, MAXPOWER, levelsPerCell, Color.GREEN);
		}
	}

	/**
	 * Increases the specified stat by 1, unless it has reached the specified maximum stat value.
	 *
	 * @param stat    the stat to change
	 * @param statMax the maximum value allowed for the specified stat
	 *
	 * @return false if the stat was unchanged because it had reached the maximum stat value
	 */
	private int increaseStat(int stat, int statMax) {
		if (stat < statMax) {
			stat++;
		}
		return stat;
	}

	/**
	 * Decreases the specified stat by 1, unless it has reached the specified minimum stat value.
	 *
	 * @param stat    the stat to change
	 * @param statMin the minimum value allowed for the specified stat
	 *
	 * @return false if the stat was unchanged because it had reached the minimum stat value
	 */
	private int decreaseStat(int stat, int statMin) {
		if (stat > statMin) {
			stat--;
		}
		return stat;
	}

	/**
	 * Returns true and requests an visual update if the the specified values differ.
	 *
	 * @param value1 the value before eventual change
	 * @param value2 the value after eventual change
	 *
	 * @return true if the the specified values differ
	 */
	private boolean areDifferent(int value1, int value2) {
		boolean areDifferent = value1 != value2;
		if (areDifferent) {
			requestVisualUpdate();
		}
		return areDifferent;
	}

	/**
	 * Increases shielding unless it is at maximum capacity. Requests a visual update if shielding was increased.
	 *
	 * @return true if shielding was increased, false if it was not
	 */
	@Override public boolean increaseShielding() {
		int oldShielding = shielding;
		shielding = increaseStat(shielding, MAXSHIELDING);
		return areDifferent(shielding, oldShielding);
	}

	/**
	 * Decreases shielding unless it is at minimum capacity. Requests a visual update if shielding was decreased.
	 *
	 * @return true if shielding was decreased, false if it was not
	 */
	@Override public boolean decreaseShielding() {
		int oldShielding = shielding;
		shielding = decreaseStat(shielding, 0);
		return areDifferent(shielding, oldShielding);
	}

	/**
	 * Increases power unless it is at maximum capacity. Requests a visual update if powerg was increased.
	 *
	 * @return true if power was increased, false if it was not
	 */
	@Override public boolean increasePower() {
		int oldPower = power;
		power = increaseStat(power, MAXPOWER);
		return areDifferent(power, oldPower);
	}

	/**
	 * Decreases power unless it is at minimum capacity. Requests a visual update if powerg was decreased.
	 *
	 * @return true if power was decreased, false if it was not
	 */
	@Override public boolean decreasePower() {
		int oldPower = power;
		power = decreaseStat(power, 0);
		return areDifferent(power, oldPower);
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

	@Override public String toString() {
		return (this.getClass() + " HP = " + integrity + ", Shielding = " + shielding + ", Power = " + power);
	}
}
