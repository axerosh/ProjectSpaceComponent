package shipcomponents;

import game.GeneralVisibleEntity;
import temp.StatBar;

import java.awt.*;

/**
 * An abstract implementation of the ShipComponent interface. Handles general ship component fucntionality including HP and
 * HP loss.
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
     * The maximum HP of this ship component. The damage it can take before it is destroyed.
     */
    private final int maxHp;

    /**
     * The HP left until destruction.
     */
    protected int hp;

    protected boolean active;

	private StatBar shieldingBar;
	private StatBar powerBar;

    private int shielding; // 0 - 6
    protected int power;

    /**
     * Contrucs an abstract ship component with the specified maximum HP.
     *
     * @param maxHp the maximum HP if the ship component
     */
    protected AbstractShipComponent(final int maxHp) {
        this.maxHp = maxHp;
        hp = maxHp;
        shielding = 0;
		power = 0;

		int componentWidth = 1;
		float barWidth = (float)componentWidth / 4;
		float barHeight = (float)componentWidth / 2;
		float barPosY = (componentWidth - barHeight) / 2;

		int numberOfBars = 2;
		float powerBarPosX = (componentWidth - barWidth * numberOfBars) / 2;
		float shieldBarPosX = powerBarPosX + barWidth;

		powerBar = new StatBar(Color.GREEN, powerBarPosX, barPosY, barWidth, barHeight);
		shieldingBar = new StatBar(Color.CYAN, shieldBarPosX, barPosY, barWidth, barHeight);
    }

    @Override
    public void inflictDamage(int damage) {
		int damageTaken = damageThroughShield(damage);
		hp -= damageTaken;
        hp = Math.max(hp, 0);
    }

    /**
     * @param damage the damage taken if not for the shielding
     * @return the damage that the component will take after its shield has reduced it
     */
    private int damageThroughShield(int damage) {
		final float shieldRatePerShielding = 0.15f;
		float shieldRate = shieldRatePerShielding * shielding;
		return Math.round(damage * (1 - shieldRate));
    }

    @Override public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY) {
		draw(g, scale, virtualX, virtualY, Color.GRAY);
    }

	/**
	 * Draws this ship component with the specified scaling and the specified color.
	 *
	 * @param g the Graphics object with which to draw this ship component
	 * @param scale the scale with which to scale virtual positions to get on-screen positions
	 * @param virtualX the virtual x-position at which the ship component is to be drawn.
	 * @param virtualY the virtual y-position at which the ship component is to be drawn.
	 * @param color the color with which to draw this ship component.
	 */
	public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY, Color color) {
		g.setColor(color);
		int screenX = (int)(virtualX * scale);
		int screenY = (int)(virtualY * scale);
		int pixelsAcrossComponent = (int)scale;
		g.fillRect(screenX, screenY, pixelsAcrossComponent, pixelsAcrossComponent);

		final int maxAlpha = 255 / 2;
		int alpha = Math.round((maxAlpha * (1 - (float)hp / maxHp)));
		Color transparentBlackOverlay = new Color(0,0,0, alpha);
		g.setColor(transparentBlackOverlay);
		g.fillRect(screenX, screenY, pixelsAcrossComponent, pixelsAcrossComponent);

		powerBar.draw(g, scale, screenX, screenY, power, MAXPOWER, hasPower());
		shieldingBar.draw(g, scale, screenX, screenY, shielding, MAXSHIELDING, hasShield());
	}

	/**
	 * Performs activation action for this ship component, depending on where the cursor is relative to this ship component.
	 * Increases the level of of any stat which indication bar the cursor hovers over.
	 *
	 * @param rx the cursor's virtual x-position relative to this ship component
	 * @param ry the cursor's virtual y-position relative to this ship component
	 */
	@Override public void activateWithCursor(final float rx, final float ry) {
		if (powerBar.contains(rx, ry)) {
			increasePower();
		} else if (shieldingBar.contains(rx, ry)) {
			increaseShielding();
		}
	}

	/**
	 * Performs deactivation action for this ship component, depending on where the cursor is relative to this ship component.
	 * Decreases the level of of any stat which indication bar the cursor hovers over.
	 *
	 * @param rx the cursor's virtual x-position relative to this ship component
	 * @param ry the cursor's virtual y-position relative to this ship component
	 */
	@Override public void deactivateWithCursor(final float rx, final float ry) {
		if (powerBar.contains(rx, ry)) {
			decreasePower();
		} else if (shieldingBar.contains(rx, ry)) {
			decreaseShielding();
		}
	}

	/**
	 * Tries to increase the shielding of the component by one.
	 * Requests a visual update if successfull.
	 *
	 * @return true if successfull
	 */
	@Override public boolean increaseShielding() {
		int oldShielding = shielding;
		shielding = increaseStat(shielding, MAXSHIELDING);
		return hasChanged(shielding, oldShielding);
    }

	/**
	 * Tries to decrease the shielding of the component by one.
	 * Requests a visual update if successfull.
	 *
	 * @return true if successfull
	 */
    @Override public boolean decreaseShielding() {
		int oldShielding = shielding;
		shielding = decreaseStat(shielding, 0);
		return hasChanged(shielding, oldShielding);
    }

	/**
	 * Tries to increase the power of the component by one.
	 * Requests a visual update if successfull.
	 *
	 * @return true if successfull
	 */
    @Override public boolean increasePower() {
		int oldPower = power;
		power = increaseStat(power, MAXPOWER);
		return hasChanged(power, oldPower);
    }

	/**
	 * Tries to decrease the power of the component by one.
	 * Requests a visual update if successfull.
	 *
	 * @return true if successfull
	 */
    @Override public boolean decreasePower() {
		int oldPower = power;
		power = decreaseStat(power, 0);
		boolean hasChanged = power != oldPower;
		if (hasChanged){
			requestVisualUpdate();
		}
		return hasChanged;

    }

	/**
	 * Returns true and requests an visual update if the the specified values differ.
	 *
	 * @param value1 the value before eventual change
	 * @param value2 the value after eventual change
	 * @return true if the the specified values differ
	 */
	private boolean hasChanged(int value1, int value2) {
		boolean hasChanged = value1 != value2;
		if (hasChanged){
			requestVisualUpdate();
		}
		return hasChanged;
	}

	/**
	* Increases the specified stat by 1, unless it has reached the specified maximum stat value.
	*
	* @param stat the stat to change
	* @param statMax the maximum value allowed for the specified stat
	* @return false if the stat was unchanged because it had reached the maximum stat value
	*/
	private int increaseStat(int stat, int statMax) {
		if (stat < statMax){
			stat++;
		}
		return stat;
	}

	/**
	* Decreases the specified stat by 1, unless it has reached the specified minimum stat value.
	*
	* @param stat the stat to change
	* @param statMin the minimum value allowed for the specified stat
	* @return false if the stat was unchanged because it had reached the minimum stat value
	*/
	private int decreaseStat(int stat, int statMin) {
		if (stat > statMin){
			stat--;
		}
		return stat;
	}

    @Override public boolean hasShield() {
		return shielding > 0;
    }

    @Override public boolean hasPower() {
    		return power > 0;
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
		return (this.getClass() + " HP = "+ hp + ", Shielding = " + shielding + ", Power = " + power);
    }
}
