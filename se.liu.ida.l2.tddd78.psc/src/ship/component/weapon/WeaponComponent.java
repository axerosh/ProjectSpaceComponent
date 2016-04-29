package ship.component.weapon;

import graphics.StatbarDrawer;
import ship.Starship;
import ship.component.AbstractShipComponent;
import ship.component.ShipComponent;

import java.awt.Color;
import java.awt.Graphics;

/**
 * A AbstractShipComponent that can fire Projectiles according to a FiringOrder
 * On update will count down a timer untill it can fire again
 *
 * @see Projectile
 * @see FiringOrder
 */
public class WeaponComponent extends AbstractShipComponent {

	protected final float baseDamage;
	protected final float damageScale;
	protected final int baseBlastRadius;
	protected final float blastRadiusScale;
	protected final float baseRechargeTime;
	protected final float rechargeScale;
	protected final float projectileVelocity;
	protected FiringOrder firingOrder;
	private float currentRechargeTime;
	private float rechargeTimeLeft;
	private Projectile projectileToFire;

	/**
	 * Constructs a weapon component.
	 *
	 * @param integrity            the integrity of the component (damage it can take before it is destroyed)
	 * @param baseDamage           the damage, that is dealt by the weapon's projectiles, if scaling is disregarded
	 * @param damageScale          the extra damage, that is dealt by the weapon's projectiles, per each level of power that is
	 *                             supplied to the weapon component
	 * @param baseBlastRadius      the blast radius, of the weapon's projectiles, if scaling is disregarded
	 * @param blastRadiusScale     the extra blast radius that, is added to the weapon's projectiles, per each level of power
	 *                             that is supplied to the weapon
	 * @param projectileVelocity   the velocity of the weapon's projectiles
	 * @param baseRechargeTime     the recharge time, between shots, if scaling is disregarded
	 * @param rechargeScale        the time, that the recharge time is reduced by, per each level of power that is supplied to
	 *                             the weapon
	 * @param maxPower             the maximum power that the weapon can be supplied at once
	 * @param symbolRepresentation the symbol that is to represent this kind of component. Used for starship text
	 *                             representations
	 *
	 * @throws IllegalArgumentException if the baseBlastRadius and/or projectileVelocity is negative or zero or if the
	 * specified
	 *                                  integrity is negative
	 */
	public WeaponComponent(final float integrity, final float baseDamage, final float damageScale, final int baseBlastRadius,
						   final float blastRadiusScale, final float projectileVelocity, final float baseRechargeTime,
						   final float rechargeScale, final int maxPower, final char symbolRepresentation) throws IllegalArgumentException {
		super(integrity, maxPower, symbolRepresentation, new Color(100, 100, 100));

		if (baseBlastRadius <= 0) {
			throw new IllegalArgumentException("The specified base blast radius " + baseBlastRadius + " is negative or zero.");
		}

		if (projectileVelocity <= 0) {
			throw new IllegalArgumentException("The specified projectile velocity " + projectileVelocity + " is negative or " +
											   "zero.");
		}

		this.baseDamage = baseDamage;
		this.damageScale = damageScale;
		this.baseBlastRadius = baseBlastRadius;
		this.blastRadiusScale = blastRadiusScale;
		this.projectileVelocity = projectileVelocity;
		this.baseRechargeTime = baseRechargeTime;
		this.rechargeScale = rechargeScale;
		rechargeTimeLeft = 0;
		firingOrder = null;
		projectileToFire = null;
	}

	public Projectile shoot() {

		float damage = baseDamage + getPower() * damageScale;
		int blastRadius = baseBlastRadius + (int) (getPower() * blastRadiusScale);

		return new Projectile(firingOrder.getOriginX(), firingOrder.getOriginY(), firingOrder.getTargetX(),
							  firingOrder.getTargetY(), projectileVelocity, firingOrder.getTargetShip(), damage, blastRadius);

	}

	private boolean isRecharging() {
		return rechargeTimeLeft > 0;
	}

	/**
	 * Updates the weaponComponent, recharge the weapon by one and if there is a standing firingOrder and the weapon can shoot,
	 * a shot will be fired.
	 */
	public void updateWeapon(float deltaSeconds) {
		projectileToFire = null;
		rechargeTimeLeft -= deltaSeconds;
		if (hasOrder() && canShoot()) {
			projectileToFire = shoot();
			firingOrder = null;
			float rechargeTimeCoefficient = 1 / (1 + getPower() * rechargeScale);
			currentRechargeTime = baseRechargeTime * rechargeTimeCoefficient;
			rechargeTimeLeft = currentRechargeTime;
		}
	}

	@Override public void update() {}

	@Override public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY) {
		super.draw(g, scale, virtualX, virtualY);

		if (isRecharging()) {
			int indicatorScreenX = (int) (virtualX * scale);
			int indicatorScreenY = (int) (virtualY * scale);

			int pixelsAcrossComponent = (int) scale;
			int pixelsAcrossIndicator = pixelsAcrossComponent / 5;

			StatbarDrawer.drawOval(g, indicatorScreenX, indicatorScreenY, pixelsAcrossIndicator, pixelsAcrossIndicator,
								   rechargeTimeLeft, currentRechargeTime, Color.ORANGE);
		}
	}

	/**
	 * Sets the firingOrder for the weapon.
	 *
	 * @param order the firingOrder to give the weapon.
	 */
	public void setFiringOrder(FiringOrder order) {
		this.firingOrder = order;
	}

	/**
	 * @return a projectile if the weapon has fired.
	 */
	public Projectile getProjectileToFire() {
		return projectileToFire;
	}

	/**
	 * @return true if an firingOrder exists.
	 */
	public boolean hasOrder() {
		return firingOrder != null;
	}

	/**
	 * Checks wheter the weaponry timer has reached its recharge time and also if the components life and power are more than
	 * zero.
	 *
	 * @return true if the weapon can fire
	 */
	public boolean canShoot() {
		return !isRecharging() && isIntact() && hasPower();
	}

	@Override public void registerOwner(final Starship owner) {
		super.registerOwner(owner);
		owner.registerWeaponComponent(this);
	}


	@Override public void deregisterOwner() {
		if (getOwner() != null) {
			getOwner().deregisterWeaponComponent(this);
			super.deregisterOwner();
		}
	}

	@Override public void restore() {
		super.restore();
		rechargeTimeLeft = 0;
		firingOrder = null;
		projectileToFire = null;
	}

	@Override public ShipComponent copy() {
		return new WeaponComponent(maxIntegrity, baseDamage, damageScale, baseBlastRadius, blastRadiusScale,
								   projectileVelocity,
								   baseRechargeTime, rechargeScale, getMaxPower(), getSymbolRepresentation());
	}

	@Override public boolean equals(final Object o) {
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }
		if (!super.equals(o)) { return false; }

		final WeaponComponent that = (WeaponComponent) o;

		if (Float.compare(that.baseDamage, baseDamage) != 0) { return false; }
		if (Float.compare(that.damageScale, damageScale) != 0) { return false; }
		if (baseBlastRadius != that.baseBlastRadius) { return false; }
		if (Float.compare(that.blastRadiusScale, blastRadiusScale) != 0) { return false; }
		if (Float.compare(that.baseRechargeTime, baseRechargeTime) != 0) { return false; }
		if (Float.compare(that.rechargeScale, rechargeScale) != 0) { return false; }
		if (Float.compare(that.projectileVelocity, projectileVelocity) != 0) { return false; }

		return true;
	}
}
