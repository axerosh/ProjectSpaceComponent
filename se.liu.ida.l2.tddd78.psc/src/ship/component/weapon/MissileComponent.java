package ship.component.weapon;

import ship.component.ShipComponent;
import weaponry.FiringOrder;
import weaponry.projectile.MissileProjectile;
import weaponry.projectile.Projectile;

import java.awt.*;

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
		final float damagePerPower = 0.5f;
		final int baseDamage = 1;

		final float blastRadiusPerPower = 0.25f;
		final float baseBlastDamage = 2.5f;

		int velocity = 10;
		int damage = baseDamage + (int) (getPower() * damagePerPower);
		int blastRadius = (int) (baseBlastDamage + getPower() * blastRadiusPerPower);
		return new MissileProjectile(firingOrder.getOriginX(), firingOrder.getOriginY(), firingOrder.getTargetX(),
									 firingOrder.getTargetY(), velocity, firingOrder.getTargetShip(), damage, blastRadius);
	}

	@Override public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY) {
		final Color gray = new Color(100, 100, 100);
		draw(g, scale, virtualX, virtualY, gray);
	}

	@Override public void update() {}


	@Override public char getSymbolRepresentation() {
		return 'M';
	}

	@Override public ShipComponent clone() throws CloneNotSupportedException {
		return new MissileComponent(maxIntegrity, (int)baseRechargeTime);
	}
}
