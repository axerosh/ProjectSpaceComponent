package shipcomponents;

/**
 * An abstract implementation of the ShipComponent interface. Handles general ship component fucntionality including HP and
 * HP loss.
 */
public abstract class AbstractShipComponent implements ShipComponent {

    /**
     * The maximum HP of this ship component. The damage it can take before it is destroyed.
     */
    private final int maxHp;

    /**
     * The HP left until destruction.
     */
    private int hp;

    /**
     * Contrucs an abstract ship component with the specified maximum HP.
     *
     * @param maxHp the maximum HP if the ship component
     */
    protected AbstractShipComponent(final int maxHp) {
        this.maxHp = maxHp;
        hp = maxHp;
    }

    @Override
    public void inflictDamage(int hp) {
        this.hp -= hp;
        this.hp = Math.max(hp, 0);
    }
}
