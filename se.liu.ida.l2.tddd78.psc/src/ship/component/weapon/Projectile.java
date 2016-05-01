package ship.component.weapon;

import ship.Starship;
import ship.component.ShipComponent;

import java.awt.Color;
import java.awt.Graphics;

/**
 * A general projectile that will move towards a target ShipComponent and inflict damage it and surrounding
 * ShipComponents unpon impact if they are within the blastradius
 *
 * @see ShipComponent
 */
public class Projectile {

	private float selfX, selfY;
	private float targetX, targetY;
	private float xVelocity;
	private float yVelocity;
	private Starship targetShip;
	private float damageOnImpact;
	private int blastRadius;

	/**
	 * Constructs a Projectile.
	 *
	 * @param selfX          the virtual start x-coordinate of the Projectile
	 * @param selfY          the virtual start y-coordinate of the Projectile
	 * @param targetX        the virtual x-coordinate of the Projectile's target
	 * @param targetY        the virtual y-coordinate of the Projectile's target
	 * @param velocity       the velocity if the Projectile
	 * @param targetShip     the Starship that the Projectile willk try to hit when it has reached its target locationb
	 * @param damageOnImpact the damage that the Projectile will deal for ecery ShipComponet that is hits on impact.
	 * @param blastRadius    the radius of the blast caused by the Projectile on impact. the blast area is diamond-shaped with
	 *                       the length (blastRadius * 2 - 1) fromn left corner to right corner as well as top croenr to bottom
	 *                       corner. All ShipCompoennts in this area will take the specified damageOnImpact if the Projectile
	 *                       hits its tagretShip.
	 *
	 * @throws IllegalArgumentException if either of the specified blastRadius or velcotiy is negative or zero.
	 * @see ShipComponent
	 */
	public Projectile(final float selfX, final float selfY, final float targetX, final float targetY, final float velocity,
					  final Starship targetShip, final float damageOnImpact, final int blastRadius)
	throws IllegalArgumentException {
		if (blastRadius <= 0) {
			throw new IllegalArgumentException("The specified base blast radius " + blastRadius + " is negative or zero.");
		}

		if (velocity <= 0) {
			throw new IllegalArgumentException("The specified projectile velocity " + velocity+ " is negative or " +
											   "zero.");
		}
		this.selfX = selfX;
		this.selfY = selfY;
		this.targetX = targetX;
		this.targetY = targetY;
		this.targetShip = targetShip;
		this.damageOnImpact = damageOnImpact;
		this.blastRadius = blastRadius;

		double angle = Math.atan2(targetY - selfY, targetX - selfX);
		xVelocity = (float) Math.cos(angle) * velocity;
		yVelocity = (float) Math.sin(angle) * velocity;
	}

	/**
	 * Updates the projectile position, checks if the the projetile is at its target, if so applies its effect
	 */
	public void updateMovement(float deltaSeconds) {
		selfX += xVelocity * deltaSeconds;
		selfY += yVelocity * deltaSeconds;

		if (hasImpact()) {
			impact();
		}
	}

	/**
	 * Apply the projectile effect on target component(s).
	 */
	private void impact() {
		if (!targetShip.successfullyDodged() && targetShip.getComponentAt(targetX, targetY) != null) {
			for (int relativeRow = -blastRadius + 1; relativeRow <= blastRadius - 1; relativeRow++) {

				int startCol = Math.abs(relativeRow) + 1 - blastRadius;
				int width = 2 * blastRadius - 1 - 2 * Math.abs(relativeRow);
				for (int relativeCol = startCol; relativeCol < startCol + width; relativeCol++) {
					dealDamage(targetX + relativeCol, targetY + relativeRow);
				}
			}
		}
	}

	/**
	 * Deals damage to any ship component at the specified position
	 *
	 * @param x the x-coordinate of the position
	 * @param y the x-coordinate of the position
	 */
	private void dealDamage(float x, float y) {
		ShipComponent target = targetShip.getComponentAt(x, y);
		if (target != null) {
			target.inflictDamage(damageOnImpact);
		}
	}

	/**
	 * @return true if the projectile has reached its target.
	 */
	public boolean hasImpact() {
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
	public void draw(final Graphics g, final float scale) {
		g.setColor(Color.YELLOW);
		g.drawLine((int) (scale * selfX), (int) (scale * selfY), (int) ((selfX + xVelocity / 10) * scale),
				   (int) ((selfY + yVelocity / 10) * scale));
	}
}