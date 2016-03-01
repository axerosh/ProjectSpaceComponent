package shipcomponents.utilitycomponents;

import shipcomponents.AbstractShipComponent;

/**
 * The utility componets are components that have no active effect,
 * but adds diffrent stats or add resurces to the ships resurce pool.
 */
public abstract class UtilityComponent extends AbstractShipComponent {

    int output;

    protected UtilityComponent(final int maxHp, int output) {
		super(maxHp);
		output = 0;
    }

    public int getOutput(){
		return output;
    }
}
