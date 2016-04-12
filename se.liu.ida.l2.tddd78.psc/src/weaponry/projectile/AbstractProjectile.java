package weaponry.projectile;

import ship.Starship;
import ship.component.ShipComponent;

import java.awt.Color;
import java.awt.Graphics;

/**
 * A general projectile that ill move towards a target and inflict damage.
 */
public class AbstractProjectile implements Projectile
{

	private float selfX, selfY;
	private float targetX, targetY;
	private float xVelocity;
	private float yVelocity;
	private Starship targetShip;
	private float damageOnImpact;
	private int blastRadius;

	public AbstractProjectile(final float selfX, final float selfY, final float targetX, final float targetY,
							  final float velocity, final Starship targetShip, final float damageOnImpact,
							  final int blastRadius)
	{
		this.selfX = selfX;
		this.selfY = selfY;
		this.targetX = targetX;
		this.targetY = targetY;
		this.targetShip = targetShip;
		this.damageOnImpact = damageOnImpact;
		this.blastRadius = blastRadius;
		System.out.println("Projectile spawned at " + selfX + " " + selfY);

		double angle = Math.atan2(targetY - selfY, targetX - selfX);
		xVelocity = (float) Math.cos(angle) * velocity;
		yVelocity = (float) Math.sin(angle) * velocity;
	}

	/**
	 * Updates the projectile position, checks if the the projetile is at its target, if so applies its effect
	 */
	@Override public void updateMovement(float deltaSeconds) {
		selfX += xVelocity * deltaSeconds;
		selfY += yVelocity * deltaSeconds;
		//System.out.println(selfX + " " + selfY);

		if (hasImpact()) {
			impact();
		}
	}

	/**
	 * Apply the projectile effect on target ship.component(s).
	 */
	@Override public void impact() {
		if (!targetShip.successfullyDodged() && targetShip.getComponentAt(targetX, targetY) != null) {
			System.out.println("Projectile hit target!");
			for (int relativeRow = -blastRadius + 1; relativeRow <= blastRadius - 1; relativeRow++) {

				int startCol = Math.abs(relativeRow) + 1 - blastRadius;
				int width = 2 * blastRadius - 1 - 2 * Math.abs(relativeRow);
				for (int relativeCol = startCol; relativeCol < startCol + width; relativeCol++) {
					dealDamage(targetX + relativeCol, targetY + relativeRow);
				}
			}
		} else {
			System.out.println("Missed!");
			System.out.println("Component at " + targetX + " " + targetY + ": " + targetShip.getComponentAt(targetX, targetY));
		}
	}

	/**
	 * Deals damage to any ship ship.component at the specified position
	 *
	 * @param x the x-coordinate of the position
	 * @param y the x-coordinate of the position
	 */
	public void dealDamage(float x, float y) {
		ShipComponent target = targetShip.getComponentAt(x, y);
		if (target != null) {
			target.inflictDamage(damageOnImpact);
		}
	}

	@Override public boolean hasImpact() {
		if (xVelocity < 0) {
			if (selfX <= targetX) {
				return true;
			}
		} else if (xVelocity > 0) {
			if (selfX >= targetX) {
				return true;
			}
		} else if (yVelocity < 0) {
			if ((selfY <= targetY)) {
				return true;
			}
		} else if (yVelocity > 0) {
			if ((selfY >= targetY)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Draws the projetile on the screen
	 *
	 * @param g     Graphics object to draw with.
	 * @param scale scale of which all positions and sizes will be scaled with.
	 */
	@Override public void draw(final Graphics g, final float scale) {
		g.setColor(Color.YELLOW);
		g.drawLine((int) (scale * selfX), (int) (scale * selfY), (int) ((selfX + xVelocity / 10) * scale),
				   (int) ((selfY + yVelocity / 10) * scale));
	}
}