package shipcomponents.utilitycomponents;

import game.StarShip;

import java.awt.*;

/**
 * Component that add shield to the ships shield pool.
 */
public class ShieldComponent extends UtilityComponent{

    public ShieldComponent(final int maxHp, final int shieldOutput) {
        super(maxHp, shieldOutput);
    }

    public void update(){

	if(active){
	    if(hp == 0){
		output = 0;
	    }else{
		output = power * 2;
	    }
	}
    }

    @Override public void performAction() {

    }

    @Override public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY) {
		draw(g, scale, virtualX, virtualY, Color.CYAN);
    }

    @Override public void registerFunctionality(final StarShip ship) {
		ship.registerShieldComponent(this);
	}
}
