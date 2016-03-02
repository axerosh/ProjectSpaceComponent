package shipcomponents.weaponscomponents;

import projectiles.Projectile;
import temp.Order;

public interface Weapon
{

    public void update();

    public void giveOrder(Order order);

    public void cancelOrder();

    public boolean hasOrder();

    public boolean canShoot();

    public Projectile shoot();

    public Projectile updateWeapon();

}
