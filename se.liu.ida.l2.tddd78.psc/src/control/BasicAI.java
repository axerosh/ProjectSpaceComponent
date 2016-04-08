package control;

import component.ShipComponent;
import component.weapon.WeaponComponent;
import game.Battlefield;
import game.Starship;
import weaponry.FiringOrder;
import weaponry.Weapon;
import weaponry.projectile.Projectile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class BasicAI
{
	private Battlefield field;
	private Starship aiShip;
	private List<Projectile> projetilesToFire;
	private Random random;

	public BasicAI(final Battlefield field, final Starship aiShip) {
		this.field = field;
		this.aiShip = aiShip;
		projetilesToFire = new ArrayList<>();
	}

	private void orderPoolUsage(){
		for(WeaponComponent wc : aiShip.getWeaponComponents()){
			if(aiShip.hasFreePower()){
				wc.increasePower();
			}
		}
	}

	private void orderWeaponsUsage(){

		Starship targetShip;
		ShipComponent targetComponent;
		for (WeaponComponent wc : aiShip.getWeaponComponents()) {
			targetShip = field.getRandomShipOfTeam(field.getRandomHostileTeam(aiShip.getTeam()));
			targetComponent = targetShip.getRandomComponent();

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

	public void addProjectiles(final Collection<Projectile> projectiles) {
		projetilesToFire.addAll(projectiles);
	}
}
