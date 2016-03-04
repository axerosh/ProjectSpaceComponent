package ship_components.weapon_components;

import game.Starship;
import weaponry.projectiles.MissileProjectile;
import weaponry.projectiles.Projectile;

public class MissileComponent extends AbstractWeaponComponent {

    public MissileComponent(final float integrity, final int rechargeTime) {
		super(integrity, rechargeTime);
    }

    @Override public Projectile shoot() {
		int velocity = 1;
        int damage = 2;
		int blastRadius = 3;
	return new MissileProjectile(firingOrder.getOriginX(), firingOrder.getOriginY(), firingOrder.getTargetX(), firingOrder.getTargetY(), velocity,
								 firingOrder.getTargetShip(), damage, blastRadius);
    }

    @Override public void update() {

    }

    @Override public void registerFunctionality(final Starship ship) {
        ship.registerWeaponComponent(this);
    }
}
