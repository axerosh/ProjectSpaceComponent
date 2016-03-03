package shipcomponents.weaponscomponents;

import game.StarShip;
import projectiles.MissileProjectile;
import projectiles.Projectile;

public class MissileComponent extends AbstractWeaponComponent
{

    public MissileComponent(final int maxHp, final int rechargeTime) {
	super(maxHp, rechargeTime);
    }

    @Override public Projectile shoot() {
		int velocity = 1;
        int damage = 1;
		int blastRadius = 3;
	return new MissileProjectile(order.getOriginX(),order.getOriginY(),order.getTargetX(),order.getTargetY(), velocity,
								 order.getTargetShip(), damage, blastRadius);
    }

    @Override public void update() {

    }

    @Override public void performAction() {

    }

    @Override public void registerFunctionality(final StarShip ship) {
        ship.registerWeaponComponent(this);
    }
}
