package ship.component.utility;

import ship.Starship;

import java.awt.*;

/**
 * A utility components that contributes with power.
 */
public class ShieldComponent extends UtilityComponent
{

	public ShieldComponent(final float integrity, final int shieldOutput) {
		super(integrity, shieldOutput);
	}

	@Override public void update() {
		final int outputPerPower = 2;
		setOutput(getPower() * outputPerPower);
	}

	@Override public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY) {
		draw(g, scale, virtualX, virtualY, Color.CYAN);
	}

	@Override public void registerOwner(final Starship owner) {
		super.registerOwner(owner);
		owner.registerShieldComponent(this);
	}

	@Override public char getSymbolRepresentation() {
		return 'S';
	}
}
