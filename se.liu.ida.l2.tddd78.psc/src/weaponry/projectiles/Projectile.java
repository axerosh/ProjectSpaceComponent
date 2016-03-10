package weaponry.projectiles;

import java.awt.*;

/**
 * A projectile which can be fired and inflict damage.
 */
public interface Projectile
{

	public void update();

	public void impact();

	public boolean hasImpact();

	public void draw(final Graphics g, final float scale);
}
