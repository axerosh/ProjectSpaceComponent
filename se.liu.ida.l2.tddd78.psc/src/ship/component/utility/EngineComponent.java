package ship.component.utility;

import ship.Starship;

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
		if (getPower() == 0) {
			setOutput(0);
		} else {
			final float outputPerPower = 1.5f;
			final int baseOutput = 6;
			setOutput((int) (getPower() * outputPerPower) + baseOutput);
		}

	}

	@Override public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY) {
		final Color green = new Color(0, 230, 0);
		draw(g, scale, virtualX, virtualY, green);
	}

	@Override public void registerOwner(final Starship owner) {
		super.registerOwner(owner);
		owner.registerEngineComponent(this);
	}

	@Override public char getSymbolRepresentation() {
		return 'E';
	}
}
