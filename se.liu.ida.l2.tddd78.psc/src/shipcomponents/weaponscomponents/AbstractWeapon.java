package shipcomponents.weaponscomponents;

import projectiles.Projectile;
import shipcomponents.AbstractShipComponent;
import temp.Order;

public abstract class AbstractWeapon extends AbstractShipComponent{
    int rechargeTime;
    int rechargeCounter;
    Order order;

    public AbstractWeapon(final int maxHp, final int rechargeTime) {
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
    public void update(){
		rechargeCounter++;
		if(hasOrder() && canShoot()){
			shoot();
			order = null;
		}
    }

    public void setOrder(Order order){
	this.order = order;
    }

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
     * if the components life is more than 0.
     * @return true if the weapon can fire
     */
    public boolean canShoot(){
		if (rechargeCounter == rechargeCounter && hp > 0){
			return true;
		} else {
			return false;
		}
    }

    public abstract Projectile shoot();
}
