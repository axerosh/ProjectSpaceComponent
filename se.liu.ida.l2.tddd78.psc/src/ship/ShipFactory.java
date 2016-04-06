package ship;

import ship.component.utility.EngineComponent;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for creating starships
 *
 * @see Starship
 */
public final class ShipFactory {

	/**
	 * Creates a star ship with at specifed position, acoording to the specified text representation.
	 *
	 * @param x                  the x-position of the ship
	 * @param y                  the y-position of the ship
	 * @param textRepresentation a text representationof the ship to create
	 *
	 * @return the created ship
	 * @throws IllegalArgumentException if one of the following is true: <ul> <li>there is no width specified in the text
	 *                                  representation</li> <li>there is no height specified in the text representation</li>
	 *                                  <li>there is no integrity specified in the text representation</li> <li>there is no
	 *                                  maxIntegrity specified in the text representation</li> <li>the specified width is
	 *                                  negative or 0</li> <li>the specified height is negative or 0</li> <li>the specified
	 *                                  integrity is negative or 0</li> <li>the specified maxIntegrity is less than the
	 *                                  specified integrity</li> </ul>
	 * @throws IllegalArgumentException if one of the following is true:
	 */
	public Starship getStarship(final float x, final float y, String textRepresentation) {
		Map<String, Float> parameterValues = getParameterMap(textRepresentation);

		Float width = parameterValues.get("width");
		if (width == null) {
			throw new IllegalArgumentException("There was no width specified in the text representation.");
		}

		Float height = parameterValues.get("height");
		if (height == null) {
			throw new IllegalArgumentException("There was no height specified in the text representation.");
		}

		Float integrity = parameterValues.get("integrity");
		if (integrity == null) {
			throw new IllegalArgumentException("There was no integrity specified in the text representation.");
		}

		Float maxIntegrity = parameterValues.get("maxIntegrity");
		if (maxIntegrity == null) {
			throw new IllegalArgumentException("There was no maxIntegrity specified in the text representation.");
		}

		Starship ship = new Starship(x, y, width.intValue(), height.intValue(), integrity, maxIntegrity);
		addComponents(ship, textRepresentation);
		return ship;
	}

	/**
	 * Reads parameter values from the specified text representation of a ship.
	 *
	 * @param textRepresentation a text representation of a ship
	 *
	 * @return a map of the parameter names and their respecive values
	 * @throws NumberFormatException if one of the parameter values is not a parsable number.
	 */
	private Map<String, Float> getParameterMap(String textRepresentation) {
		Map<String, Float> parameterValues = new HashMap<>();

		Class rolf = EngineComponent.class;

		int endIndex = textRepresentation.indexOf(';');
		int startIndex = 0;
		while (startIndex < endIndex) {

			int nextStop = textRepresentation.indexOf(',');
			if (nextStop < 0) {
				nextStop = endIndex;
			}

			String parameter = textRepresentation.substring(startIndex, endIndex);
			parameter = parameter.replaceAll("\\s", "");

			int seperationIndex = parameter.indexOf('=');
			if (seperationIndex < 0) {
				startIndex = nextStop + 1;
				continue;
			}

			String parameterName = parameter.substring(0, seperationIndex);
			String parameterValueText = parameter.substring(seperationIndex + 1, parameter.length());
			Float parameterValue = Float.valueOf(parameterValueText);

			parameterValues.put(parameterName, parameterValue);

			startIndex = nextStop + 1;
		}

		return parameterValues;
	}

	private void addComponents(Starship ship, String textRepresentation) {
		int startIndex = textRepresentation.indexOf(';') + 1;

		char characterThingy = '2';
		switch (characterThingy) {

		}
	}
}
