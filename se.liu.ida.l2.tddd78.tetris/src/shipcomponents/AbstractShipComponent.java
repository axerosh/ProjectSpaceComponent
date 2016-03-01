package shipcomponents;

public abstract class AbstractShipComponent {

    /**
     * The maximum integrity of this ship component. The damage it can take before it is destroyed.
     */
    private final int maxHp;

    /**
     * The integrity left until destruction.
     */
    private int hp;

    public AbstractShipComponent(final int maxHp) {
        this.maxHp = maxHp;
        hp = maxHp;
    }

    public void inflictDamage(int hp) {
        this.hp -= hp;
        this.hp = Math.max(hp, 0);
    }
}
