package weaponry;

import game.Starship;

public class FiringOrder
{
    private float originX, originY;
    private float targetX, targetY;
    private Starship targetShip;

    public FiringOrder(final float originX, final float originY, final float targetX, final float targetY,
					   final Starship targetShip)
    {
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

    public Starship getTargetShip() {
	return targetShip;
    }
}
