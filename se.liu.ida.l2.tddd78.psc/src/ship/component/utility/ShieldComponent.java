package ship.component.utility;

import ship.Starship;
import ship.component.ShipComponent;

import java.awt.Color;

/**
 * A utility components that contributes with power.
 */
public class ShieldComponent extends PoweredUtilityComponent {

	public ShieldComponent(final float integrity, final float baseShield, final float shieldScaling, final int maxPower,
						   final char symbolRepresentation) {
		super(integrity, baseShield, shieldScaling, maxPower, symbolRepresentation, Color.BLUE.brighter());
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
		return new ShieldComponent(maxIntegrity, baseOutput, outputScaling, getMaxPower(), getSymbolRepresentation());
	}
}
