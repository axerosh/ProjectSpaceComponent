package ship.component.weapon;

import ship.component.ShipComponent;
import weaponry.FiringOrder;
import weaponry.projectile.MissileProjectile;
import weaponry.projectile.Projectile;

import java.awt.Color;
import java.awt.Graphics;

/**
 * A weapon component that can fire missiles according to firing orders.
 *
 * @see MissileProjectile
 * @see FiringOrder
 */
public class MissileComponent extends WeaponComponent
{

	public MissileComponent(final float integrity, final float damageScale, final float blastRadiusScale,
							final float projectileVelocity, final float baseRechargeTime, final float rechargeScale,
							final char symbolRepresentation) {
		super(integrity, damageScale, blastRadiusScale, projectileVelocity, baseRechargeTime, rechargeScale,
			  symbolRepresentation);
	}

	@Override public Projectile shoot() {

		int damage = (int) (getPower() * damageScale);
		int blastRadius = (int) (getPower() * blastRadiusScale);

		return new MissileProjectile(firingOrder.getOriginX(), firingOrder.getOriginY(), firingOrder.getTargetX(),
									 firingOrder.getTargetY(), projectileVelocity, firingOrder.getTargetShip(), damage,
									 blastRadius);
	}

	@Override public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY) {
		final Color gray = new Color(100, 100, 100);
		draw(g, scale, virtualX, virtualY, gray);
	}

	@Override public void update() {}

	@Override public final ShipComponent copy() {
		return new MissileComponent(maxIntegrity, damageScale, blastRadiusScale, projectileVelocity, baseRechargeTime,
									rechargeScale, getSymbolRepresentation());
	}
}
