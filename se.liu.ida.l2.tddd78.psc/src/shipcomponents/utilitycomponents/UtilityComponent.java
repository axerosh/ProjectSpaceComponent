package shipcomponents.utilitycomponents;

import shipcomponents.AbstractShipComponent;

/**
 * The utility componets are components that have no active effect,
 * but adds diffrent stats or add resurces to the ships resurce pool.
 */
public abstract class UtilityComponent extends AbstractShipComponent {

    private int output;

    protected UtilityComponent(final int maxHp, int output) {
		super(maxHp);
		this.output = output;
    }

    public int getOutput(){
		if (hp > 0 && isActive()) {
			return output;
		} else {
			return 0;
		}
    }

	protected void setOutput(int output) {
		this.output = output;
	}
}
