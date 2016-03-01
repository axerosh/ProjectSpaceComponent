package shipcomponents.utilitycomponents;

/**
 * Component that add dodgechance to a ship.
 */
public class EngineComponent extends UtilityComponent{
    public EngineComponent(final int maxHp, final int dodgePercentage) {
	super(maxHp, dodgePercentage);
    }

    @Override public void performAction() {

    }
}
