package weaponry;

import weaponry.projectile.Projectile;

/**
 * A weapon that can shoot projectile to follow firing orders.
 *
 * @see Projectile
 * @see FiringOrder
 */
public interface Weapon
{

	public void giveFiringOrder(FiringOrder order);

	public Projectile getProjectileToFire();

	public boolean hasOrder();

	public boolean canShoot();

	public Projectile shoot();

	public void updateWeapon();

}
