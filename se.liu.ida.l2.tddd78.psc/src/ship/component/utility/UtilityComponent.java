package ship.component.utility;

import ship.component.AbstractShipComponent;

/**
 * A ship ship.component with an output.
 */
public abstract class UtilityComponent extends AbstractShipComponent
{

	private int output;

	protected UtilityComponent(final float integrity, int output) {
		super(integrity, false);
		this.output = output;
	}

	public int getOutput() {
		if (isIntact() && isActive()) {
			return output;
		} else {
			return 0;
		}
	}

	protected void setOutput(int output) {
		this.output = output;
	}
}
