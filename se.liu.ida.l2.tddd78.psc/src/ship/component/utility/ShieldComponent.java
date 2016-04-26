package ship.component.utility;

import ship.Starship;
import ship.component.ShipComponent;

import java.awt.Color;
import java.awt.Graphics;

/**
 * A utility components that contributes with power.
 */
public class ShieldComponent extends UtilityComponent
{
	private final float shieldScaling;

	public ShieldComponent(final float integrity, final float shieldScaling, final char symbolRepresentation) {
		super(integrity, 0, symbolRepresentation);
		this.shieldScaling = shieldScaling;
	}

	@Override public void update() {
		setOutput((int)(getPower() * shieldScaling));
	}

	@Override public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY) {
		final Color blue = new Color(0, 150, 255);
		draw(g, scale, virtualX, virtualY, blue);
	}

	@Override public void registerOwner(final Starship owner) {
		super.registerOwner(owner);
		owner.registerShieldComponent(this);
	}


	@Override public void deregisterOwner() {
		if (getOwner() != null) {
			getOwner().deregisterShieldComponent(this);
			super.deregisterOwner();
		}
	}


	@Override public final ShipComponent copy() {
		return new ShieldComponent(maxIntegrity, output, getSymbolRepresentation());
	}
}
