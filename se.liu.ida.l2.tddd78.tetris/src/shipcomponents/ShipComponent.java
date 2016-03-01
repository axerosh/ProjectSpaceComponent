package shipcomponents;

/**
 * Interface defining the ship component and general functions including once for recieving damage and activation.
 */
public interface ShipComponent {

    /**
     * Damages this ship component by reducing its HP by the specified number of hit points.
     *
     * @param hp the number of hit points by which this ship component's HP is reduced
     */
    public void inflictDamage(int hp);

    /**
     * Activates this ship component, performing its component-type specific action.
     */
    public void performAction();

}
