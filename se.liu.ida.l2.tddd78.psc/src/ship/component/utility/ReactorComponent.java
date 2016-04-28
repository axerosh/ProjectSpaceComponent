package ship.component.utility;

import ship.Starship;
import ship.component.AbstractShipComponent;
import ship.component.ShipComponent;

import java.awt.Color;

/**
 * A ship component that supplies power to its ship. It does not require power to function.
 */
public class ReactorComponent extends AbstractShipComponent {

	private final float output;

	public ReactorComponent(final float integrity, final float powerOutput, final char symbolRepresentation)
	{
		super(integrity, 0, symbolRepresentation, Color.GREEN);
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
		return new ReactorComponent(maxIntegrity, output, getSymbolRepresentation());
	}

	public float getOutput() {
		if (isActive() && isIntact()) {
			return output;
		} else {
			return 0;
		}
	}

	@Override public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;

		final ReactorComponent that = (ReactorComponent) o;

		if (Float.compare(that.output, output) != 0)
			return false;

		return true;
	}
}
