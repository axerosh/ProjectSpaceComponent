package shipcomponents;

public interface ShipComponent {

    /**
     * Damages this ship component by reducing its integrity by the specified number of hit point.
     *
     * @param hp the number of hit points by which this ship component's integrity is reduced
     */
    public void inflictDamage(int hp);

    /**
     * Activates this ship component, performing its component-type specific action.
     */
    public void activate();

}
