package ship_components.weapon_components;

import game.Starship;
import ship_components.AbstractShipComponent;
import weaponry.FiringOrder;
import weaponry.Weapon;
import weaponry.projectiles.Projectile;


public abstract class AbstractWeaponComponent extends AbstractShipComponent implements Weapon
{
    private int rechargeTime;
    private int rechargeCounter;
    protected FiringOrder firingOrder;

    protected AbstractWeaponComponent(final float integrity, final int rechargeTime) {
		super(integrity);
		this.rechargeTime = rechargeTime;
		rechargeCounter = 0;
		firingOrder = null;
    }

    /**
     * Updates the weaponComponent,
     * recharge the weapon by one
     * and if there is a standing firingOrder and the weapon can shoot,
     * a shot will be fired.
     */
    @Override public Projectile updateWeapon(){
	rechargeCounter++;
	if(hasOrder() && canShoot()){
	    Projectile p = shoot();
		firingOrder = null;
	    return p;
	}
	return null;
    }

    /**
     * Sets the firingOrder for the weapon.
     * @param order the firingOrder to give the weapon.
     */
    @Override public void giveFiringOrder(FiringOrder order){
	this.firingOrder = order;
    }

    /**
     * Cancel the standing firingOrder of the weapon.
     */
	@Override public void cancelFiringOrder(){
		firingOrder = null;
    }

    /**
     * @return true if an firingOrder exists.
     */
	@Override public boolean hasOrder(){
	return firingOrder != null;
    }

    /**
     * Checks wheter the weaponry timer has reached its recharge time and also
     * if the components life and power are more than zero.
     * @return true if the weapon can fire
     */
	@Override public boolean canShoot(){
		return rechargeCounter > rechargeTime && isIntact() && hasPower();
    }

    @Override public void registerFunctionality(final Starship ship) {
	ship.registerWeaponComponent(this);
    }
}
