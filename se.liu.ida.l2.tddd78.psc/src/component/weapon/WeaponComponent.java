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
public abstract class WeaponComponent extends AbstractShipComponent implements Weapon
{

	private final float baseRechargeTime;
	protected FiringOrder firingOrder;
	private float rechargeTimeLeft;
	private Projectile projectileToFire;

	protected WeaponComponent(final float integrity, final int rechargeTime) {
		super(integrity, true);
		this.baseRechargeTime = rechargeTime;
		rechargeTimeLeft = 0;
		firingOrder = null;
		projectileToFire = null;
	}

	/**
	 * Updates the weaponComponent, recharge the weapon by one and if there is a standing firingOrder and the weapon can shoot,
	 * a shot will be fired.
	 */
	@Override public void updateWeapon(float deltaSeconds) {
		projectileToFire = null;
		rechargeTimeLeft -= deltaSeconds;
		if (hasOrder() && canShoot()) {
			projectileToFire = shoot();
			firingOrder = null;
			float rechargeTimeCoefficient = 1 / (1 + (float) (getPower() / 2));
			rechargeTimeLeft = baseRechargeTime * rechargeTimeCoefficient;
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
		return rechargeTimeLeft <= 0 && isIntact() && hasPower();
	}

	@Override public void registerOwner(final Starship owner) {
		super.registerOwner(owner);
		owner.registerWeaponComponent(this);
	}
}
