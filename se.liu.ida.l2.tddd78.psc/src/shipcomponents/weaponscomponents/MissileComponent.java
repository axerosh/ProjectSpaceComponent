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
	return new MissileProjectile(order.getOriginX(),order.getOriginY(),order.getTargetX(),order.getTargetY(), power, order.getTargetShip(), power, 2);
    }

    @Override public void update() {

    }

    @Override public void performAction() {

    }

    @Override public void registerFunctionality(final StarShip ship) {
        ship.registerWeaponComponent(this);
    }
}
