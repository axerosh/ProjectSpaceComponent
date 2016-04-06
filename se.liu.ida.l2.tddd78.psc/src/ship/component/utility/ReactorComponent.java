package ship.component.utility;

import ship.Starship;

import java.awt.*;

/**
 * A utility components that contributes with shielding.
 */
public class ReactorComponent extends UtilityComponent
{

	public ReactorComponent(final float integrity, final int powerOutput) {
		super(integrity, powerOutput);
	}

	@Override public void update() {}

	@Override public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY) {
		draw(g, scale, virtualX, virtualY, Color.GREEN);
	}

	@Override public void registerOwner(final Starship owner) {
		super.registerOwner(owner);
		owner.registerReactorComponent(this);
	}

	@Override public char getSymbolRepresentation() {
		return 'R';
	}
}
