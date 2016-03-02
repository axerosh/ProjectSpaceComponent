package shipcomponents.utilitycomponents;

import game.StarShip;

import java.awt.*;

/**
 * Components that adds power to a ships power pool.
 */
public class ReactorComponent extends UtilityComponent{

    public ReactorComponent(final int maxHp, final int powerOutput) {
		super(maxHp, powerOutput);
		output = 3;
    }

    public void update(){
	if(active){
	    if(hp == 0){
		output = 0;
	    }else{
		output = 5;
	    }
	}
    }

    @Override public void performAction() {

    }

    @Override public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY) {
		draw(g, scale, virtualX, virtualY, Color.GREEN);
    }

	@Override public void registerFunctionality(final StarShip ship) {
		ship.registerReactorComponent(this);
	}
}
