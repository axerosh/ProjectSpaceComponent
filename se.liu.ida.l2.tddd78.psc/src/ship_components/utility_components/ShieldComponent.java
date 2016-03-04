package ship_components.utility_components;

import game.StarshipTemp;

import java.awt.Color;
import java.awt.Graphics;

/**
 * A utility components that contributes with power.
 */
public class ShieldComponent extends UtilityComponent {

	public ShieldComponent(final float integrity, final int shieldOutput) {
		super(integrity, shieldOutput);
	}

	@Override public void update() {
		setOutput(getPower() * 2);
	}

	@Override public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY) {
		draw(g, scale, virtualX, virtualY, Color.CYAN);
	}

	@Override public void registerFunctionality(final StarshipTemp ship) {
		ship.registerShieldComponent(this);
	}
}
