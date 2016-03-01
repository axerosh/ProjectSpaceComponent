package shipcomponents.utilitycomponents;

import shipcomponents.AbstractShipComponent;

/**
 * The utility componets are components that have no active effect,
 * but adds diffrent stats or add resurces to the ships resurce pool.
 */
public abstract class UtilityComponent extends AbstractShipComponent {

    public UtilityComponent(final int maxHp) {
	super(maxHp);
    }
}
