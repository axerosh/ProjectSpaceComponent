package ship_components.utility_components;

import game.Starship;

import java.awt.*;

/**
 * A utility components that contributes with dodge rate.
 */
public class EngineComponent extends UtilityComponent
{

	public EngineComponent(final float integrity, final int dodgePercentage) {
		super(integrity, dodgePercentage);
	}

	@Override public void update() {
		if(getPower() == 0){
			setOutput(0);
		}else{
			setOutput((int)(getPower()*1.5) + 6);
		}

	}

	@Override public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY) {
		draw(g, scale, virtualX, virtualY, Color.RED);
	}

	@Override public void registerOwner(final Starship owner) {
		super.registerOwner(owner);
		owner.registerEngineComponent(this);
	}
}
