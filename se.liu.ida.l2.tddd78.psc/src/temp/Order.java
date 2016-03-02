package temp;

import game.StarShip;

public class Order {
    int targetX, targetY;
    StarShip targetShip;

    public int getTargetX() {
	return targetX;
    }

    public int getTargetY() {
	return targetY;
    }

    public StarShip getTargetShip() {
	return targetShip;
    }
}
