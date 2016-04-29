package ship.component.utility;

import ship.Starship;
import ship.component.ShipComponent;

import java.awt.Color;

/**
 * A PoweredUtilityComponent that supplies shielding to its Starships Shieldpool when it is powered.
 *
 * @see PoweredUtilityComponent
 * @see Starship
 */
public class ShieldComponent extends PoweredUtilityComponent {

	/**
	 * Constructs a shield component.
	 *
	 * @param integrity            the integrity of the component (damage it can take before it is destroyed)
	 * @param baseShield           the shielding, that is supplied to its ship, if scaling is disregarded
	 * @param shieldScaling        the extra shielding, that is supplied to its ship, per each level of power that is supplied
	 *                             to the shield component
	 * @param maxPower             the maximum power that the weapon can be supplied at once
	 * @param symbolRepresentation the symbol that is to represent this kind of component. Used for starship text
	 *                             representations
	 *
	 * @throws IllegalArgumentException if the specified integrity is negative
	 */
	public ShieldComponent(final float integrity, final float baseShield, final float shieldScaling, final int maxPower,
						   final char symbolRepresentation) throws IllegalArgumentException {
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
