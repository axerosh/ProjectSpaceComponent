package ship.component.utility;

import ship.component.AbstractShipComponent;

/**
 * A ship ship.component with an output.
 */
public abstract class UtilityComponent extends AbstractShipComponent
{
	protected float output;

	protected UtilityComponent(final float integrity, float output) {
		super(integrity);
		this.output = output;
	}

	public float getOutput() {
		if (isIntact() && isActive()) {
			return output;
		} else {
			return 0;
		}
	}

	protected void updOutput(int output) {
		this.output = output;
	}
}
