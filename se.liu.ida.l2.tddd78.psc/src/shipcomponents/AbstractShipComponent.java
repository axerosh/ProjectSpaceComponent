package shipcomponents;

import java.awt.*;

/**
 * An abstract implementation of the ShipComponent interface. Handles general ship component fucntionality including HP and
 * HP loss.
 */
public abstract class AbstractShipComponent implements ShipComponent {

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
    private int hp;

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
    }

    @Override
    public void inflictDamage(int damage) {
        hp -= damageThroughShield(damage);
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
		int pixelsAcrossWhenRendered = (int)scale;
		g.fillRect(screenX, screenY, pixelsAcrossWhenRendered, pixelsAcrossWhenRendered);

	}

	public void drawStatBar(final Graphics g, final int screenPosX, final int screenPosY, final int width, final int height) {

	}

    /**
     * Increases the Shieldning of the component
     */
    @Override public boolean increaseShielding() {
		if (shielding < MAXSHIELDING){
			shielding++;
			return true;
		} else {
			return false;
		}
    }

    @Override public boolean decreaseShielding() {
		if (shielding > 0){
	    	shielding--;
			return true;
		} else {
			return false;
		}
    }

    @Override public boolean increasePower() {
		if (power < MAXPOWER){
			power++;
			return true;
		} else {
			return false;
		}
    }

    @Override public boolean decreasePower() {
		if (power > 0){
			power--;
			return true;
		} else {
			return false;
		}
    }

    @Override public boolean hasShield() {
		return shielding > 0;
    }



    @Override public String toString() {
		return ("HP = "+ hp + ", Shielding = " + shielding + ", Power = " + power);
    }
}
