package shipcomponents.weaponscomponents;

import game.StarShip;
import projectiles.Projectile;
import shipcomponents.AbstractShipComponent;
import temp.Order;


public abstract class AbstractWeaponComponent extends AbstractShipComponent implements Weapon
{
    int rechargeTime;
    int rechargeCounter;
    Order order;

    public AbstractWeaponComponent(final int maxHp, final int rechargeTime) {
	super(maxHp);
	this.rechargeTime = rechargeTime;
	rechargeCounter = 0;
	order = null;
    }

    /**
     * Updates the weaponComponent,
     * recharge the weapon by one
     * and if there is a standing order and the weapon can shoot,
     * a shot will be fired.
     */
    @Override public Projectile updateWeapon(){
	rechargeCounter++;
	if(hasOrder() && canShoot()){
	    order = null;
	    return shoot();
	}
	return null;
    }

    /**
     * Sets the order for the weapon.
     * @param order the order to give the weapon.
     */
    public void giveOrder(Order order){
	this.order = order;
    }

    /**
     * Cancel the standing order of the weapon.
     */
    public void cancelOrder(){
	order = null;
    }

    /**
     * @return true if an order exists.
     */
    public boolean hasOrder(){
	return order != null;
    }

    /**
     * Checks wheter the weapons timer has reached its recharge time and also
     * if the components life and power are more than zero.
     * @return true if the weapon can fire
     */
    public boolean canShoot(){
	if (rechargeCounter == rechargeTime && hp > 0 && power > 0){
	    return true;
	}else{
	    return false;
	}
    }

    @Override public void registerFunctionality(final StarShip ship) {
	ship.registerWeaponComponent(this);
    }
}
