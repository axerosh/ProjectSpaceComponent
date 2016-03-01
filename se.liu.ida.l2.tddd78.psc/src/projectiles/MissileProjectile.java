package projectiles;

import game.StarShip;

public class MissileProjectile extends AbstractProjectile{

    public MissileProjectile(final float selfX, final float selfY, final float targetX, final float targetY,
			     final float velocity, final StarShip enemyShip, final int damageOnImpact, final int areaOfEffect) {
	super(selfX, selfY, targetX, targetY, velocity, enemyShip, damageOnImpact, areaOfEffect);
    }



}
