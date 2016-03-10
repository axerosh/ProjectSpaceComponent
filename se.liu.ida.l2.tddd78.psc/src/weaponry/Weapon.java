package weaponry;

import weaponry.projectiles.Projectile;

/**
 * A weapon that can shoot projectiles to follow firing orders.
 *
 * @see Projectile
 * @see FiringOrder
 */
public interface Weapon
{

	public void update();

	public void giveFiringOrder(FiringOrder order);

	public void cancelFiringOrder();

	public Projectile getProjectileToFire();

	public boolean hasOrder();

	public boolean canShoot();

	public Projectile shoot();

	public void updateWeapon();

}
