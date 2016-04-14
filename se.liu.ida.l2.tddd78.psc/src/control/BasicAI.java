package control;

import game.Battlefield;
import ship.Starship;
import ship.component.ShipComponent;
import ship.component.weapon.WeaponComponent;
import weaponry.FiringOrder;


/**
 * Basic AI for basic AI fucntionallity.
 */
public class BasicAI
{
	private Battlefield field;
	private Starship aiShip;

	public BasicAI(final Battlefield field, final Starship aiShip) {
		this.field = field;
		this.aiShip = aiShip;
	}

	private void orderPoolUsage(){
		for(WeaponComponent wc : aiShip.getWeaponComponents()){
			if(aiShip.hasFreePower()){
				wc.increasePower();
			}
		}
	}

	private void orderWeaponsUsage(){

		for (WeaponComponent wc : aiShip.getWeaponComponents()) {
			Starship targetShip = field.getRandomShipOfTeam(field.getRandomHostileTeam(aiShip.getTeam()));
			ShipComponent targetComponent = targetShip.getRandomComponent();

			float originX = aiShip.getPositionOf(wc).x;
			float originY = aiShip.getPositionOf(wc).y;

			float targetX = targetShip.getPositionOf(targetComponent).x;
			float targetY = targetShip.getPositionOf(targetComponent).y;

			FiringOrder order = new FiringOrder(originX, originY, targetX, targetY, targetShip);
			wc.giveFiringOrder(order);
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
