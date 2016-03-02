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
    protected int hp;

    protected boolean active;

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
		int pixelsAcrossComponent = (int)scale;
		g.fillRect(screenX, screenY, pixelsAcrossComponent, pixelsAcrossComponent);

		int barWidth = pixelsAcrossComponent / 4;
		int barHeight = pixelsAcrossComponent / 2;
		int barPosY = screenY + (pixelsAcrossComponent - barHeight) / 2;

		int numberOfBars = 2;
		int powerBarPosX = screenX + (pixelsAcrossComponent - barWidth * numberOfBars) / 2;
		int shieldBarPosX = powerBarPosX + barWidth;

		if (hasPower()) {
			drawStatBar(g, Color.GREEN, powerBarPosX, barPosY, barWidth, barHeight, power, MAXPOWER);
		}

		if (hasShield()) {
			drawStatBar(g, Color.CYAN, shieldBarPosX, barPosY, barWidth, barHeight, shielding, MAXSHIELDING);
		}
	}

	/**
	 * Draws a stat bar of cells of the specified stat in the specified color at the specified screen postion with the
	 * specified width and height.
	 *
	 * @param g the Graphics object with which to draw this ship component
	 * @param activeColor the color of active cells in the bar
	 * @param screenPosX the x-coordinate of the screen position at which the bar is to be drawn (left edge coordinate)
	 * @param screenPosY the y-coordinate of the screen position at which the bar is to be drawn (top edge coordinate)
	 * @param width the width of the bar in pixels
	 * @param height the height of the bar in pixels
	 * @param currentStatLevel the current stat level (indicates the number of active cells)
	 * @param maxStatLevel the maximum stat level (indicates the maximum number of cells)
	 * @throws IllegalArgumentException if the specified width, height or max stat level, are negative or zero,
	 * or if the specified current stat level is negative
	 */
	private void drawStatBar(final Graphics g, final Color activeColor, final int screenPosX, final int screenPosY,
							final int width, final int height, final int currentStatLevel, final int maxStatLevel ) {
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("Invalid ship dimensions width = " + width + ", height = " + height + ". " +
											   "Only positive integers are permitted.");
		} else if (currentStatLevel < 0) {
			throw new IllegalArgumentException("The specified stat level current stat level = " +  currentStatLevel +
											   " is invalid. It can not be negative.");
		} else if (maxStatLevel <= 0) {
			throw new IllegalArgumentException("The specified max stat level = " + maxStatLevel + " is invalid. " +
											   "It must be greater than 0.");
		}

		int outlineThickness = 1;
		int levelHeight = Math.max(1, (height - 2 * outlineThickness)/maxStatLevel);
		int fillHeight = Math.round((height - 2 * outlineThickness) * currentStatLevel / (float)maxStatLevel);
		int fillPosY = screenPosY + height - fillHeight - outlineThickness;

		//Outline/Background
		g.setColor(Color.BLACK);
		g.fillRect(screenPosX, screenPosY, width, height);

		int activeStartX  = currentStatLevel* levelHeight;

		//Active cells
		g.setColor(activeColor);
		g.fillRect(screenPosX + outlineThickness, fillPosY, width - 2 * outlineThickness, fillHeight);
	}

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

    /**
     * @return true if the component has atleast on shieldning.
     */
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
		return ("HP = "+ hp + ", Shielding = " + shielding + ", Power = " + power);
    }
}
