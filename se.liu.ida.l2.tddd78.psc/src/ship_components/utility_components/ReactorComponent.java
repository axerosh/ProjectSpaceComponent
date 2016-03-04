package ship_components.utility_components;

import game.StarShip;

import java.awt.Color;
import java.awt.Graphics;

/**
 * A utility components that contributes with shielding.
 */
public class ReactorComponent extends UtilityComponent {

	public ReactorComponent(final float integrity, final int powerOutput) {
		super(integrity, powerOutput);
	}

	@Override public void update() {}

	@Override public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY) {
		draw(g, scale, virtualX, virtualY, Color.GREEN);
	}

	@Override public void registerFunctionality(final StarShip ship) {
		ship.registerReactorComponent(this);
	}
}
