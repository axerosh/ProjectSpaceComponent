package ship_components.utility_components;

import ship_components.AbstractShipComponent;

/**
 * The utility componets are components that have no active effect,
 * but adds diffrent stats or add resurces to the ships resurce pool.
 */
public abstract class UtilityComponent extends AbstractShipComponent {

    private int output;

    protected UtilityComponent(final float integrity, int output) {
		super(integrity);
		this.output = output;
    }

    public int getOutput(){
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
