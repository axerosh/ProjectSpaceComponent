package shipcomponents;

import java.awt.*;

/**
 * An abstract implementation of the ShipComponent interface. Handles general ship component fucntionality including HP and
 * HP loss.
 */
public abstract class AbstractShipComponent implements ShipComponent {

    /**
     * The maximum HP of this ship component. The damage it can take before it is destroyed.
     */
    private final int maxHp;

    /**
     * The HP left until destruction.
     */
    private int hp;

    private int shielding; // 0 - 6
    private int power;

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
	g.setColor(Color.CYAN);
	int screenX = (int)(virtualX * scale);
	int screenY = (int)(virtualY * scale);
	int widthOnScreen = (int)scale;
	g.fillRect(screenX, screenY, widthOnScreen, widthOnScreen);
    }


    @Override public void increaseShielding() {
	shielding++;
    }

    @Override public void decreaseShielding() {
	shielding--;
    }

    @Override public void increasePower() {
	power++;
    }

    @Override public void decreasePower() {
	power--;
    }
}
