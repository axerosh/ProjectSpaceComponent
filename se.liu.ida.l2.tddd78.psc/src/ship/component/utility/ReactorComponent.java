package ship.component.utility;

import ship.Starship;
import ship.component.AbstractShipComponent;
import ship.component.ShipComponent;

import java.awt.*;

/**
 * A ship component that supplies power to its ship. It does not require power to function.
 */
public class ReactorComponent extends AbstractShipComponent {

	private final float output;

	public ReactorComponent(final float integrity, final float powerOutput, final float weight, final char symbolRepresentation)
	{
		super(integrity, weight, symbolRepresentation, Color.GREEN);
		this.output = powerOutput;
	}

	@Override public void update() {}

	@Override public void registerOwner(final Starship owner) {
		super.registerOwner(owner);
		owner.registerReactorComponent(this);
	}


	@Override public void deregisterOwner() {
		if (getOwner() != null) {
			getOwner().deregisterReactorComponent(this);
			super.deregisterOwner();
		}
	}


	@Override public final ShipComponent copy() {
		return new ReactorComponent(maxIntegrity, output, getWeight(), getSymbolRepresentation());
	}

	public float getOutput() {
		if (isActive() && isIntact()) {
			return output;
		} else {
			return 0;
		}
	}
}
