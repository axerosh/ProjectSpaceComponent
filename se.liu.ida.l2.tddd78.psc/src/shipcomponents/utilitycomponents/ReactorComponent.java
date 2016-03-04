package shipcomponents.utilitycomponents;

import game.StarShip;

import java.awt.*;

/**
 * Components that adds power to a ships power pool.
 */
public class ReactorComponent extends UtilityComponent{

    public ReactorComponent(final int maxHp, final int powerOutput) {
		super(maxHp, powerOutput);
    }

    public void update(){
    }

    @Override public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY) {
		draw(g, scale, virtualX, virtualY, Color.GREEN);
    }

	@Override public void registerFunctionality(final StarShip ship) {
		ship.registerReactorComponent(this);
	}
}
