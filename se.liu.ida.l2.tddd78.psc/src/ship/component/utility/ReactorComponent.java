package ship.component.utility;

import ship.Starship;
import ship.component.ShipComponent;

import java.awt.Color;
import java.awt.Graphics;

/**
 * A utility components that contributes with shielding.
 */
public class ReactorComponent extends UtilityComponent {

	public ReactorComponent(final float integrity, final float powerOutput, final char symbolRepresentation) {
		super(integrity, powerOutput, symbolRepresentation);
	}

	@Override public void update() {}

	@Override public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY) {
		final Color green = new Color(0, 230, 0);
		draw(g, scale, virtualX, virtualY, green);
	}

	@Override public void registerOwner(final Starship owner) {
		super.registerOwner(owner);
		owner.registerReactorComponent(this);
	}

	@Override public final ShipComponent copy() {
		return new ReactorComponent(maxIntegrity, output, getSymbolRepresentation());
	}
}
