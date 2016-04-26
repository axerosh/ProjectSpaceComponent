package ship.component.utility;

import ship.Starship;
import ship.component.ShipComponent;

import java.awt.*;

/**
 * A utility components that contributes with power.
 */
public class ShieldComponent extends PoweredUtilityComponent
{
	private final float shieldScaling;

	public ShieldComponent(final float integrity, final float baseShield, final float shieldScaling,
						   final char symbolRepresentation)
	{
		super(integrity, baseShield, shieldScaling, symbolRepresentation, new Color(0, 150, 255));
		this.shieldScaling = shieldScaling;
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
		return new ShieldComponent(maxIntegrity, baseOutput, outputScaling, getSymbolRepresentation());
	}
}
