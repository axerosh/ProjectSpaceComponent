package weaponry;

import weaponry.projectiles.Projectile;

public interface Weapon {

    public void update();

    public void giveFiringOrder(FiringOrder order);

    public void cancelFiringOrder();

    public boolean hasOrder();

    public boolean canShoot();

    public Projectile shoot();

    public Projectile updateWeapon();

}
