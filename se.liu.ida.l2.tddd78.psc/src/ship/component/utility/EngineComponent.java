package ship.component.utility;

import ship.Starship;
import ship.component.ShipComponent;

import java.awt.Color;

/**
 * A utility components that contributes with dodge rate.
 */
public class EngineComponent extends PoweredUtilityComponent {

	public EngineComponent(final float integrity, final float baseDodging, final float dodgeScaling, final int maxPower,
						   final char symbolRepresentation) {
		super(integrity, baseDodging, dodgeScaling, maxPower, symbolRepresentation, Color.RED);
	}

	@Override public void registerOwner(final Starship owner) {
		super.registerOwner(owner);
		owner.registerEngineComponent(this);
	}

	@Override public void deregisterOwner() {
		if (getOwner() != null) {
			getOwner().deregisterEngineComponent(this);
			super.deregisterOwner();
		}
	}

	@Override public final ShipComponent copy() {
		return new EngineComponent(maxIntegrity, baseOutput, outputScaling, getMaxPower(), getSymbolRepresentation());
	}
}
