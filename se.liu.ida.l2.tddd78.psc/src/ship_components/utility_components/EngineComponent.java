package ship_components.utility_components;

import game.StarShip;

import java.awt.Color;
import java.awt.Graphics;

/**
 * A utility components that contributes with dodge rate.
 */
public class EngineComponent extends UtilityComponent {

	public EngineComponent(final float integrity, final int dodgePercentage) {
		super(integrity, dodgePercentage);
	}

	@Override public void update() {}

	@Override public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY) {
		draw(g, scale, virtualX, virtualY, Color.RED);
	}

	@Override public void registerFunctionality(final StarShip ship) {
		ship.registerEngineComponent(this);
	}
}
