package weaponry.projectile;

import ship.Starship;

/**
 * A missile that ill move towards a target and inflict damage.
 */
public class MissileProjectile extends AbstractProjectile
{

	public MissileProjectile(final float selfX, final float selfY, final float targetX, final float targetY,
							 final float velocity, final Starship enemyShip, final float damageOnImpact, final int blastRadius)
	{
		super(selfX, selfY, targetX, targetY, velocity, enemyShip, damageOnImpact, blastRadius);
	}
}
