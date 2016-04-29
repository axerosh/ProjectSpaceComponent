package control;

import game.BattleSpace;
import ship.Starship;
import ship.component.ShipComponent;
import ship.component.weapon.FiringOrder;
import ship.component.weapon.WeaponComponent;


/**
 * Basic AI that controlls a Starship in a BattleSpace by powering and giving FireingOrders to WeaponsComponents
 * @see Starship
 * @see BattleSpace
 * @see FiringOrder
 * @see WeaponComponent
 */
public class BasicAI {
	private BattleSpace field;
	private Starship aiShip;

	public BasicAI(final BattleSpace field, final Starship aiShip) {
		this.field = field;
		this.aiShip = aiShip;
	}

	private void orderPoolUsage() {
		for (WeaponComponent wc : aiShip.getWeaponComponents()) {
			if (aiShip.hasFreePower()) {
				wc.increasePower();
			}
		}
	}

	private void orderWeaponsUsage() {

		for (WeaponComponent wc : aiShip.getWeaponComponents()) {
			Starship targetShip = field.getRandomHostileTeam(aiShip.getTeam()).getRandomMember();
			if (targetShip == null || targetShip.isEmpty()) {
				return;
			}
			ShipComponent targetComponent = targetShip.getRandomComponent();

			float originX = aiShip.getPositionOf(wc).x;
			float originY = aiShip.getPositionOf(wc).y;

			float targetX = targetShip.getPositionOf(targetComponent).x;
			float targetY = targetShip.getPositionOf(targetComponent).y;

			FiringOrder order = new FiringOrder(originX, originY, targetX, targetY, targetShip);
			wc.setFiringOrder(order);
		}
	}

	/**
	 * The AI calculates what i want to do and then gives its components orders.
	 */
	public void update() {
		orderPoolUsage();
		orderWeaponsUsage();

	}
}
