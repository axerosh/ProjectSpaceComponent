package ship_components.utility_components;

import ship_components.AbstractShipComponent;

/**
 * A ship component with an output.
 */
public abstract class UtilityComponent extends AbstractShipComponent {

	private int output;

	protected UtilityComponent(final float integrity, int output) {
		super(integrity);
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