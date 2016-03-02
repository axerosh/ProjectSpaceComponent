package temp;

import game.StarShip;

public class Order {
    float originX, originY;
    float targetX, targetY;
    StarShip targetShip;

    public Order(final float originX, final float originY, final float targetX, final float targetY,
		 final StarShip targetShip)
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

    public StarShip getTargetShip() {
	return targetShip;
    }
}
