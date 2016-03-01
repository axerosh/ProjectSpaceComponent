package projectiles;

import java.awt.Point;
import game.StarShip;
import shipcomponents.ShipComponent;

public class AbstractProjectile implements Projectile{
    private float selfX, selfY;
    private float targetX, targetY;
    private float xVelocity;
    private float yVelocity;
    private StarShip enemyShip;

    private int damageOnImpact;
    private int areaOfEffect;

    public AbstractProjectile(final float selfX, final float selfY, final float targetX, final float targetY, final float velocity,
			      final StarShip enemyShip, final int damageOnImpact, final int areaOfEffect)
    {
	this.selfX = selfX;
	this.selfY = selfY;
	this.targetX = targetX;
	this.targetY = targetY;
	this.enemyShip = enemyShip;
	this.damageOnImpact = damageOnImpact;
	this.areaOfEffect = areaOfEffect;

	double angle = Math.atan2(targetY - selfY, targetX - selfX);
	xVelocity = (float)Math.cos(angle) * velocity;
	yVelocity = (float)Math.sin(angle) * velocity;
    }

    /**
     * Updates the projectiles position,
     * checks if the the projetile is at its target,
     * if so applies its effect
     */
    @Override public void update() {
	selfX += xVelocity;
	selfY += yVelocity;

	if(haveImpact()){
	    impact();
	}
    }

    /**
     * Apply the projectiles effect on target component(s).
     */
    @Override public void impact() {
	if(!enemyShip.successfullyDodged() && enemyShip.getComponentAt(targetX, targetY) != null){

	    for(int relativeRow = -areaOfEffect +1 ; relativeRow <= areaOfEffect -1; relativeRow++){

		int startCol = Math.abs(relativeRow) + 1 - areaOfEffect;
		int width = 2*areaOfEffect - 1 - 2*Math.abs(relativeRow);
		for(int relativeCol = startCol; relativeCol < startCol + width; relativeCol++){
		    dealDamage(targetX + relativeCol, targetY + relativeRow);
		}
	    }
	}
    }

    /**
     * Deals damage to any ship component at the specified position
     * @param x the x-coordinate of the position
     * @param y the x-coordinate of the position
     */
    public void dealDamage(float x, float y){
	ShipComponent target = enemyShip.getComponentAt(x, y);
	if (target != null) {
	    target.inflictDamage(damageOnImpact);
	}
    }

    /**
     * Checks if the projectile is at its intended target.
     * @return true if the projectile has reached its target
     */
    @Override public boolean haveImpact() {
	if (xVelocity < 0){
	    if (selfX <= targetX){
		return true;
	    }
	}else if(xVelocity > 0){
	    if (selfX >= targetX){
		return true;
	    }
	}else if(yVelocity < 0){
	    if((selfY <= targetY)){
		return true;
	    }
	}else if(yVelocity > 0){
	    if((selfY >= targetY)){
		return true;
	    }
	}
	return false;
    }
}