package projectiles;

import java.awt.Point;
import game.StarShip;

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
     * if so applys it effect
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

    }

    /**
     * Checks if the projectile is at its intended target.
     * @return boolean
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
