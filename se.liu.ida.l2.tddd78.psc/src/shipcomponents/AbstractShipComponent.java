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
		shieldingBar.draw(g, scale, screenX, screenY, shielding, MAXSHIELDING, hasShielding());
	}

	@Override public void changeStatIndicatedAt(final float rx, final float ry, final int change) {
		if (powerBar.contains(rx, ry)) {
			changePower(change);
		} else if (shieldingBar.contains(rx, ry)) {
			changeShielding(change);
		}
	}

	/**
	  * Tries to change the shielding of the component by one by the specified amount.
	  * Requests a visual update if successfull.
	  *
	  * @param change amount with which the shielding is to be changed
	  * @return true if successfull, false if shielding is at max value
	  */
	@Override public int changeShielding(int change) {
		int oldShielding = shielding;
		if (change < 0) {
			shielding = decreaseStat(shielding, 0, change);
		} else {
			shielding = increaseStat(shielding, MAXSHIELDING, change);
		}
		return change(oldShielding, shielding);
    }

	/**
	  * Tries to change the power of the component by the specified amount.
	  * Requests a visual update if a change was made.
	  *
	  * @param change amount with which the power is to be changed
	  * @return the change that was made
	  */
	@Override public int changePower(final int change) {
		int oldPower = power;
		if (change < 0) {
			power = decreaseStat(power, 0, change);
		} else {
			power = increaseStat(power, MAXPOWER, change);
		}
		return change(oldPower, power);
	}

	/**
	 * Requests a visual update if a change was made.
	 *
	 * @param oldValue a value before it was changed
	 * @param newValue the same value after it was changed
	 * @return the change from the specifed old vale to the specified new value
	 */
	private int change(int oldValue, int newValue) {
		int change = newValue - oldValue;
		if (change != 0) {
			requestVisualUpdate();
		}
		return change;
	}

	/**
	 * Increases the specified stat by the specified amount, unless this will make it greater than the specified stat maximum.
	 * In this case it is increased to the point where it is equal to the maximum
	 *
	 * @param stat the stat to change
	 * @param statMax the maximum value allowed for the specified stat
	 * @param increase amount with which the specified stat is to be increased.
	 * @return the specified stat after the increase; never greater than the specified stat maximum
	 */
	private int increaseStat(int stat, int statMax, int increase) {
		stat += increase;
		return Math.min(stat, statMax);
	}

	/**
	 * Decreases the specified stat by the specified amount, unless this will make it lesser than the specified stat minimum.
	 * In this case it is decreased to the point where it is equal to the minimum
	 *
	 * @param stat the stat to change
	 * @param statMin the minimum value allowed for the specified stat
	 * @param decrease amount with which the specified stat is to be decreased.
	 * @return the specified stat after the decrease; never lesser than the specified stat minimum
	 */
	private int decreaseStat(int stat, int statMin, int decrease) {
		stat -= decrease;
		return Math.max(stat, statMin);
	}

    @Override public boolean hasShielding() {
		return shielding > 0;
    }

    @Override public boolean hasPower() {
    		return power > 0;
    }

	@Override public int getShielding() {
		return shielding;
	}

	@Override public int getPower() {
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
		return (this.getClass() + " HP = "+ hp + ", Shielding = " + shielding + ", Power = " + power);
    }
}
