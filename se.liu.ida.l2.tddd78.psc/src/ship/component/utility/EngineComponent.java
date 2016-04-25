package ship.component.utility;

import ship.Starship;
import ship.component.ShipComponent;

import java.awt.Color;
import java.awt.Graphics;

/**
 * A utility components that contributes with dodge rate.
 */
public class EngineComponent extends UtilityComponent {

	private float dodgeScaling;

	public EngineComponent(final float integrity, final float dodgeScaling, final char symbolRepresentation) {
		super(integrity, 0, symbolRepresentation);
		this.dodgeScaling = dodgeScaling;


	}

	@Override public void update() {
		setOutput((int)(getPower() * dodgeScaling));
	}

	@Override public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY) {
		final Color orange = new Color(255, 110, 0);
		draw(g, scale, virtualX, virtualY, orange);
	}

	@Override public void registerOwner(final Starship owner) {
		super.registerOwner(owner);
		owner.registerEngineComponent(this);
	}

	@Override public final ShipComponent copy() {
		return new EngineComponent(maxIntegrity, output, getSymbolRepresentation());
	}
}
