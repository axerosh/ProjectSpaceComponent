package weaponry.projectiles;

import game.Starship;

public class MissileProjectile extends AbstractProjectile{

    public MissileProjectile(final float selfX, final float selfY, final float targetX, final float targetY,
							 final float velocity, final Starship enemyShip, final int damageOnImpact, final int blastRadius) {
		super(selfX, selfY, targetX, targetY, velocity, enemyShip, damageOnImpact, blastRadius);
    }
}
