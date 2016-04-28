package ship.component.utility;

import ship.Starship;
import ship.component.ShipComponent;

import java.awt.Color;

/**
 * A utility components that supplies dodge rate to its ship when it is powered.
 */
public class EngineComponent extends PoweredUtilityComponent {

	/**
	 * Constructs a engine component.
	 *
	 * @param integrity            the integrity of the component (damage it can take before it is destroyed)
	 * @param baseDodging          the dodge rate, that is supplied to its ship, if scaling is disregarded
	 * @param dodgeScaling         the extra dodge rate, that is supplied to its ship, per each level of power that is supplied
	 *                             to the engine component
	 * @param maxPower             the maximum power that the weapon can be supplied at once
	 * @param symbolRepresentation the symbol that is to represent this kind of component. Used for starship text
	 *                             representations
	 *
	 * @throws IllegalArgumentException if the specified integrity is negative
	 */
	public EngineComponent(final float integrity, final float baseDodging, final float dodgeScaling, final int maxPower,
						   final char symbolRepresentation) throws IllegalArgumentException {
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
