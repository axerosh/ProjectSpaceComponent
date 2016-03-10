package component.weapon;

import component.AbstractShipComponent;
import game.Starship;
import weaponry.FiringOrder;
import weaponry.Weapon;
import weaponry.projectile.Projectile;

/**
 * A ship component that can fires projectile according to firing orders.
 *
 * @see Projectile
 * @see FiringOrder
 */
public abstract class WeaponComponent extends AbstractShipComponent implements Weapon {

	protected FiringOrder firingOrder;
	private int rechargeTime;
	private int rechargeCounter;
	private Projectile projectileToFire;

	protected WeaponComponent(final float integrity, final int rechargeTime) {
		super(integrity, true);
		this.rechargeTime = rechargeTime;
		rechargeCounter = 0;
		firingOrder = null;
		projectileToFire = null;
	}

	/**
	 * Updates the weaponComponent, recharge the weapon by one and if there is a standing firingOrder and the weapon can shoot,
	 * a shot will be fired.
	 */
	@Override public void updateWeapon() {
		projectileToFire = null;
		rechargeCounter += (1 + (getPower() / 2));
		if (hasOrder() && canShoot()) {
			projectileToFire = shoot();
			firingOrder = null;
		}
	}

	/**
	 * Sets the firingOrder for the weapon.
	 *
	 * @param order the firingOrder to give the weapon.
	 */
	@Override public void giveFiringOrder(FiringOrder order) {
		this.firingOrder = order;
	}

	/**
	 * @return a projectile if the weapon has fired.
	 */
	@Override public Projectile getProjectileToFire() {
		return projectileToFire;
	}

	/**
	 * @return true if an firingOrder exists.
	 */
	@Override public boolean hasOrder() {
		return firingOrder != null;
	}

	/**
	 * Checks wheter the weaponry timer has reached its recharge time and also if the components life and power are more than
	 * zero.
	 *
	 * @return true if the weapon can fire
	 */
	@Override public boolean canShoot() {
		return rechargeCounter > rechargeTime && isIntact() && hasPower();
	}

	@Override public void registerOwner(final Starship owner) {
		super.registerOwner(owner);
		owner.registerWeaponComponent(this);
	}
}
