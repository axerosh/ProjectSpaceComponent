package ship.component.weapon;

import graphics.Statbar;
import ship.Starship;
import ship.component.AbstractShipComponent;
import weaponry.FiringOrder;
import weaponry.Weapon;
import weaponry.projectile.Projectile;

import java.awt.*;

/**
 * A ship ship.component that can fires projectile according to firing orders.
 *
 * @see Projectile
 * @see FiringOrder
 */
public abstract class WeaponComponent extends AbstractShipComponent implements Weapon
{

	private final static Color HIGHLIGHT_COLOR = Color.YELLOW;
	private final float baseRechargeTime;
	protected FiringOrder firingOrder;
	private float currentRechargeTime;
	private float rechargeTimeLeft;
	private Projectile projectileToFire;

	protected WeaponComponent(final float integrity, final int rechargeTime) {
		super(integrity);
		this.baseRechargeTime = rechargeTime;
		rechargeTimeLeft = 0;
		firingOrder = null;
		projectileToFire = null;
		deactivate();
	}

	private boolean isRecharging() {
		return rechargeTimeLeft > 0;
	}

	/**
	 * Updates the weaponComponent, recharge the weapon by one and if there is a standing firingOrder and the weapon can shoot,
	 * a shot will be fired.
	 */
	@Override public void updateWeapon(float deltaSeconds) {
		projectileToFire = null;
		rechargeTimeLeft -= deltaSeconds;
		if (hasOrder() && canShoot()) {
			projectileToFire = shoot();
			firingOrder = null;
			float rechargeTimeCoefficient = 1 / (1 + (float) (getPower() / 2));
			currentRechargeTime = baseRechargeTime * rechargeTimeCoefficient;
			rechargeTimeLeft = currentRechargeTime;
		}
	}

	@Override
	public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY, final Color color) {
		super.draw(g, scale, virtualX, virtualY, color);


		if (isActive()) {
			int screenX = (int) (virtualX * scale);
			int screenY = (int) (virtualY * scale);
			int pixelsAcross = (int) scale;

			g.setColor(HIGHLIGHT_COLOR);
			g.drawRect(screenX, screenY, pixelsAcross - 1, pixelsAcross - 1);
		}

		if (isRecharging()) {
			int indicatorScreenX = (int) (virtualX * scale);
			int indicatorScreenY = (int) (virtualY * scale);

			int pixelsAcrossComponent = (int) scale;
			int pixelsAcrossIndicator = pixelsAcrossComponent / 5;

			Statbar.drawOval(g, indicatorScreenX, indicatorScreenY, pixelsAcrossIndicator, pixelsAcrossIndicator,
							 rechargeTimeLeft, currentRechargeTime, Color.ORANGE);
		}
	}

	/**
	 * Sets the firingOrder for the weapon.
	 *
	 * @param order the firingOrder to give the weapon.
	 */
	@Override public void giveFiringOrder(FiringOrder order) {
		this.firingOrder = order;
	}

	/**
	 * @return a projectile if the weapon has fired.
	 */
	@Override public Projectile getProjectileToFire() {
		return projectileToFire;
	}

	/**
	 * @return true if an firingOrder exists.
	 */
	@Override public boolean hasOrder() {
		return firingOrder != null;
	}

	/**
	 * Checks wheter the weaponry timer has reached its recharge time and also if the components life and power are more than
	 * zero.
	 *
	 * @return true if the weapon can fire
	 */
	@Override public boolean canShoot() {
		return !isRecharging() && isIntact() && hasPower();
	}

	@Override public void registerOwner(final Starship owner) {
		super.registerOwner(owner);
		owner.registerWeaponComponent(this);
	}
}
