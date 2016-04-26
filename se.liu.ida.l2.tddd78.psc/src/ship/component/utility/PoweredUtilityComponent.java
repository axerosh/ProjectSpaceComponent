package ship.component.utility;

import ship.component.AbstractShipComponent;

import java.awt.*;

/**
 * A ship component that supplies its ship with its output when it is powered.
 */
public class PoweredUtilityComponent extends AbstractShipComponent {

	protected final float baseOutput;
	protected final float outputScaling;
	private float output;

	public PoweredUtilityComponent(final float integrity, final float baseOutput, final float outputScaling, final float weight,
								   final char symbolRepresentation, final Color color)
	{
		super(integrity, weight, symbolRepresentation, color);
		this.baseOutput = baseOutput;
		this.outputScaling = outputScaling;
		output = 0;
	}

	/**
	 * Updates the output of the PoweredUtilityComponent
	 */
	@Override public void update() {
		if (isIntact() && isActive() && hasPower()) {
			output = (int) (baseOutput + getPower() * outputScaling);
		} else {
			output = 0;
		}
	}


	public float getOutput() {
		return output;
	}
}
