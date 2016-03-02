package shipcomponents.weaponscomponents;

import shipcomponents.AbstractShipComponent;

public abstract class AbstractWeapon extends AbstractShipComponent{
    int rechargeTime;

    public AbstractWeapon(final int maxHp, final int rechargeTime) {
	super(maxHp);
	this.rechargeTime = rechargeTime;
    }

    public boolean canShoot(){
	return false;
    }

    public boolean shoot(){
	return false;
    }
}
