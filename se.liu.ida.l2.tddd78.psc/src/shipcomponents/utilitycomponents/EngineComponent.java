package shipcomponents.utilitycomponents;

import game.StarShip;

import java.awt.*;

/**
 * Component that add dodgechance to a ship.
 */
public class EngineComponent extends UtilityComponent {

    public EngineComponent(final int maxHp, final int dodgePercentage) {
		super(maxHp, dodgePercentage);
    }

    @Override public void update() {
    }

    @Override public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY) {
		draw(g, scale, virtualX, virtualY, Color.RED);
    }

	@Override public void registerFunctionality(final StarShip ship) {
		ship.registerEngineComponent(this);
	}
}
