package weaponry.projectile;

import java.awt.*;

/**
 * A projectile which can be fired and inflict damage.
 */
public interface Projectile
{

	public void updateMovement(float deltaSeconds);

	public void impact();

	public boolean hasImpact();

	public void draw(final Graphics g, final float scale);
}
