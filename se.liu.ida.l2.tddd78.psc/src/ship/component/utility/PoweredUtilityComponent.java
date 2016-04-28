package ship.component.utility;

import ship.component.AbstractShipComponent;

import java.awt.Color;

/**
 * A ship component that supplies its ship with its output when it is powered.
 */
public abstract class PoweredUtilityComponent extends AbstractShipComponent {

	protected final float baseOutput;
	protected final float outputScaling;
	private float output;

	protected PoweredUtilityComponent(final float integrity, final float baseOutput, final float outputScaling,
									  final int maxPower,
									  final char symbolRepresentation, final Color color)
	{
		super(integrity, maxPower, symbolRepresentation, color);
		this.baseOutput = baseOutput;
		this.outputScaling = outputScaling;
		output = 0;
	}

	/**
	 * Updates the output of the PoweredUtilityComponent
	 */
	@Override public void update() {
		if (isIntact() && isActive() && hasPower()) {
			output = baseOutput + getPower() * outputScaling;
		} else {
			output = 0;
		}
	}


	public float getOutput() {
		return output;
	}

	@Override public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		if (!super.equals(o))
			return false;

		final PoweredUtilityComponent that = (PoweredUtilityComponent) o;

		if (Float.compare(that.baseOutput, baseOutput) != 0)
			return false;
		if (Float.compare(that.outputScaling, outputScaling) != 0)
			return false;

		return true;
	}
}
