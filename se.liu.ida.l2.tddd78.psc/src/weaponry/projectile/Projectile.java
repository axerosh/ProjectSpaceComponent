package weaponry.projectile;

import java.awt.*;

/**
 * A projectile which can be fired and inflict damage.
 */
public interface Projectile {

	/**
	 * Updates the projectile position, checks if the the projetile is at its target, if so applies its effect
	 */
	public void updateMovement(float deltaSeconds);

	public boolean hasImpact();

	/**
	 * Draws the projetile on the screen
	 *
	 * @param g     Graphics object to draw with.
	 * @param scale scale of which all positions and sizes will be scaled with.
	 */
	public void draw(final Graphics g, final float scale);
}
