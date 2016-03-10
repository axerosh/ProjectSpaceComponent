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
public class MissileComponent extends WeaponComponent {

	public MissileComponent(final float integrity, final int rechargeTime) {
		super(integrity, rechargeTime);
	}

	@Override public Projectile shoot() {
		final float damagePerPower = 0.5f;
		final int baseDamage = 1;

		final float blastRadiusPerPower = 0.25f;
		final float baseBlastDamage = 2.5f;

		int velocity = 1;
		int damage = baseDamage + (int) (getPower() * damagePerPower);
		int blastRadius = (int) (baseBlastDamage + getPower() * blastRadiusPerPower);
		return new MissileProjectile(firingOrder.getOriginX(), firingOrder.getOriginY(), firingOrder.getTargetX(),
									 firingOrder.getTargetY(), velocity, firingOrder.getTargetShip(), damage, blastRadius);
	}

	@Override public void update() {}
}
