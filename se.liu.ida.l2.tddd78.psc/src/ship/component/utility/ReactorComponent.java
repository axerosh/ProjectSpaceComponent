package ship.component.utility;

import ship.Starship;
import ship.component.AbstractShipComponent;
import ship.component.ShipComponent;

import java.awt.*;

/**
 * A ship component that supplies power to its ship. It does not require power to function.
 */
public class ReactorComponent extends AbstractShipComponent {

	private final float baseOutput;
	private float output;

	public ReactorComponent(final float integrity, final float powerOutput, final char symbolRepresentation) {
		super(integrity, symbolRepresentation, new Color(0, 230, 0));
		this.baseOutput = powerOutput;
		output = baseOutput;
	}

	/**
	 * Updates the output of the Reactor
	 */
	@Override public void update() {
		if (isIntact() && isActive()) {
			output = baseOutput;
		} else {
			output = 0;
		}
	}

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
		return new ReactorComponent(maxIntegrity, output, getSymbolRepresentation());
	}

	public float getOutput() {
		return output;
	}
}
