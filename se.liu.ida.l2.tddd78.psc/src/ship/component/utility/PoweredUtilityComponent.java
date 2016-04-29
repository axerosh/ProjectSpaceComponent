package ship.component.utility;

import ship.component.AbstractShipComponent;

import java.awt.Color;

/**
 * A AbstractShipComponent that supplies its output to its Starship when it is powered.
 *
 * @see AbstractShipComponent
 * @see ship.Starship
 */
public abstract class PoweredUtilityComponent extends AbstractShipComponent {

	protected final float baseOutput;
	protected final float outputScaling;
	private float output;

	/**
	 * Constructs a powered utility component.
	 *
	 * @param integrity            the integrity of the component (damage it can take before it is destroyed)
	 * @param baseOutput           the output, that is supplied to its ship, if scaling is disregarded
	 * @param outputScaling        the extra output, that is supplied to its ship, per each level of power that is supplied to
	 *                             the utility component
	 * @param maxPower             the maximum power that the weapon can be supplied at once
	 * @param symbolRepresentation the symbol that is to represent this kind of component. Used for starship text
	 *                             representations
	 * @param color                the color with which the component is drawn
	 *
	 * @throws IllegalArgumentException if the specified integrity is negative
	 */
	protected PoweredUtilityComponent(final float integrity, final float baseOutput, final float outputScaling,
									  final int maxPower, final char symbolRepresentation, final Color color) throws IllegalArgumentException {
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
		if (this == o) { return true; }
		if (o == null || getClass() != o.getClass()) { return false; }
		if (!super.equals(o)) { return false; }

		final PoweredUtilityComponent that = (PoweredUtilityComponent) o;

		if (Float.compare(that.baseOutput, baseOutput) != 0) { return false; }
		if (Float.compare(that.outputScaling, outputScaling) != 0) { return false; }

		return true;
	}
}
