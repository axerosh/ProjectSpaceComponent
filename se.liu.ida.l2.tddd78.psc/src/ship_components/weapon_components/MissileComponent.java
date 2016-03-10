package ship_components.weapon_components;

import weaponry.FiringOrder;
import weaponry.projectiles.MissileProjectile;
import weaponry.projectiles.Projectile;

/**
 * A weapon component that can fire missiles according to firing orders.
 *
 * @see MissileProjectile
 * @see FiringOrder
 */
public class MissileComponent extends WeaponComponent
{

	public MissileComponent(final float integrity, final int rechargeTime) {
		super(integrity, rechargeTime);
	}

	@Override public Projectile shoot() {
		int velocity = 1;
		int damage = 2;
		int blastRadius = 3;
		return new MissileProjectile(firingOrder.getOriginX(), firingOrder.getOriginY(), firingOrder.getTargetX(),
									 firingOrder.getTargetY(), velocity, firingOrder.getTargetShip(), damage, blastRadius);
	}

	@Override public void update() {}
}
