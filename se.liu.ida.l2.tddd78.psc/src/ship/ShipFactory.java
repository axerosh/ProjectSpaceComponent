package ship;

import ship.component.ShipComponent;
import ship.component.utility.EngineComponent;
import ship.component.utility.ReactorComponent;
import ship.component.utility.ShieldComponent;
import ship.component.weapon.MissileComponent;

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
	public static Starship getStarship(final float x, final float y, String textRepresentation) {
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
	private static Map<String, Float> getParameterMap(String textRepresentation) {
		Map<String, Float> parameterValues = new HashMap<>();

		Class rolf = EngineComponent.class;

		int endIndex = textRepresentation.indexOf(';');
		int startIndex = 0;
		while (startIndex < endIndex) {

			int nextStop = textRepresentation.indexOf(',');
			if (nextStop < 0) {
				nextStop = endIndex;
			}

			String parameter = textRepresentation.substring(startIndex, nextStop);
			parameter = parameter.replaceAll("\\s", "");

			int seperationIndex = parameter.indexOf('=');
			if (seperationIndex < 0) {
				startIndex = nextStop + 1;
				continue;
			}

			String parameterName = parameter.substring(0, seperationIndex);
			String parameterValueText = parameter.substring(seperationIndex + 1, parameter.length());
			System.out.println(parameterValueText);
			Float parameterValue = Float.valueOf(parameterValueText);

			parameterValues.put(parameterName, parameterValue);

			startIndex = nextStop + 1;
		}

		return parameterValues;
	}

	private static void addComponents(Starship ship, String textRepresentation) {
		int cursor = textRepresentation.indexOf(';') + 1;
		int row = 0;
		int col = 0;

		while (cursor < textRepresentation.length()) {
			char symbol = textRepresentation.charAt(cursor);
			ShipComponent componentToAdd = null;
			float componentIntegrity = 2;

			switch (symbol) {
				case 'E':
					int engineOutput = 10;
					componentToAdd = new EngineComponent(componentIntegrity, engineOutput);
					break;
				case 'R':
					int reactorOutput = 3;
					componentToAdd = new ReactorComponent(componentIntegrity, reactorOutput);
					break;
				case 'S':
					int shieldOutput = 4;
					componentToAdd = new ShieldComponent(componentIntegrity, shieldOutput);
					break;
				case 'M':
					int missileRechargeTime = 5;
					componentToAdd = new MissileComponent(componentIntegrity, missileRechargeTime);
					break;
				case ';':
					cursor++;
					row++;
					col = 0;
					continue;
				case '\\':
					int nextPosition = cursor + 1;
					if (nextPosition < textRepresentation.length()) {
						if (textRepresentation.charAt(nextPosition) == 'n') {
							cursor += 2;
							continue;
						}
					}
					break;
			}
			if (componentToAdd != null) {
				ship.setComponent(componentToAdd, col, row);
			}

			cursor++;
			col++;
		}
	}
}
