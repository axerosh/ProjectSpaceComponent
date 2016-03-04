package weaponry;

import game.StarShip;

/**
 * A firing order providing information of where a shot comes from, where it is targeted at and what vessel it is targeting.
 */
public class FiringOrder {

	private float originX, originY;
	private float targetX, targetY;
	private StarShip targetShip;

	public FiringOrder(final float originX, final float originY, final float targetX, final float targetY,
					   final StarShip targetShip) {
		this.originX = originX;
		this.originY = originY;
		this.targetX = targetX;
		this.targetY = targetY;
		this.targetShip = targetShip;
	}

	public float getTargetX() {
		return targetX;
	}

	public float getTargetY() {
		return targetY;
	}

	public float getOriginY() {
		return originY;
	}

	public float getOriginX() {
		return originX;
	}

	public StarShip getTargetShip() {
		return targetShip;
	}
}
