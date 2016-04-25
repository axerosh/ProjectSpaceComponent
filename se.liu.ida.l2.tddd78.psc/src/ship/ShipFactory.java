package ship;

import io.PropertiesIO;
import ship.component.ShipComponent;
import ship.component.utility.EngineComponent;
import ship.component.utility.ReactorComponent;
import ship.component.utility.ShieldComponent;
import ship.component.weapon.MissileComponent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for creating starships
 *
 * @see Starship
 */
public final class ShipFactory {

	private final static char ENGINE_SYMBOL;
	private final static char MISSILE_SYMBOL;
	private final static char REACTOR_SYMBOL;
	private final static char SHIELD_SYMBOL;
	private final static float REACTOR_OUTPUT;
	private final static float ENGINE_OUTPUT_SCALE;
	private final static float SHIELD_OUTPUT_SCALE;
	private final static float MISSILE_DAMAGE_SCALE;
	private final static float MISSILE_RADIUS_SCALE;
	private final static float COMPONENT_INTEGRITY;

	static {
		final String fileName = "ship";
		final String fileExtension = ".properties";
		final File resources = new File("resources");
		final File saveLocation = new File(resources, "properties");
		final File filePath = new File(saveLocation, fileName + fileExtension);

		final Properties properties = new Properties();
		try (InputStream in = new FileInputStream(filePath)) {
			properties.load(in);
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, e.toString(), e);
		}

		final char defaultEngineSymbol = 'E';
		final char defaultMissileSymbol = 'M';
		final char defaultReactorSymbol = 'R';
		final char defaultShieldSymbol = 'S';

		final float defaultReactorOutput = 3;
		final float defaultEngineOutputScale = 1.5f;
		final float defaultShieldOutputScale = 2;
		final float defaultMissileDamageScale = 0.5f;
		final float defaultMissileRadiusScale = 0.5f;

		final float defaultComponentIntegrity = 2;

		ENGINE_SYMBOL = PropertiesIO.getCharacterProperty(properties, "engine_symbol", defaultEngineSymbol);
		MISSILE_SYMBOL = PropertiesIO.getCharacterProperty(properties, "missile_symbol", defaultMissileSymbol);
		REACTOR_SYMBOL = PropertiesIO.getCharacterProperty(properties, "reactor_symbol", defaultReactorSymbol);
		SHIELD_SYMBOL = PropertiesIO.getCharacterProperty(properties, "shield_symbol", defaultShieldSymbol);

		REACTOR_OUTPUT = PropertiesIO.getFloatProperty(properties, "reactor_output", defaultReactorOutput);
		ENGINE_OUTPUT_SCALE = PropertiesIO.getFloatProperty(properties, "engine_output_scale", defaultEngineOutputScale);
		SHIELD_OUTPUT_SCALE = PropertiesIO.getFloatProperty(properties, "shield_output_scale", defaultShieldOutputScale);
		MISSILE_DAMAGE_SCALE = PropertiesIO.getFloatProperty(properties, "missile_damage_scale", defaultMissileDamageScale);
		MISSILE_RADIUS_SCALE = PropertiesIO.getFloatProperty(properties, "missile_radius_scale", defaultMissileRadiusScale);

		COMPONENT_INTEGRITY = PropertiesIO.getFloatProperty(properties, "component_integrity", defaultComponentIntegrity);
	}

	private ShipFactory() {}

	/**
	 * Creates a star ship with at specifed position, acoording to the specified text representation.
	 *
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
	public static Starship getStarship(final String textRepresentation) {
		String cleanRep = textRepresentation.replaceAll("\\s+", "");

		Map<String, Float> parameterValues = getParameterMap(cleanRep);

		Float width = parameterValues.get("width");
		Float height = parameterValues.get("height");
		Float integrity = parameterValues.get("integrity");
		Float maxIntegrity = parameterValues.get("maxIntegrity");
		if (width == null || height == null || integrity == null || maxIntegrity == null) {
			String message =
					"Missing ship properties in text representation. width, height, integrity and maxIntegrity required.";
			IllegalArgumentException exception = new IllegalArgumentException(message);
			Logger.getGlobal().log(Level.SEVERE, message);
			throw exception;
		}

		Starship ship = new Starship(width.intValue(), height.intValue(), integrity, maxIntegrity);
		addComponents(ship, cleanRep);
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

		int endIndex = textRepresentation.indexOf(';');
		if (endIndex < 0) {
			endIndex = textRepresentation.length();
		}

		String parameters = textRepresentation.substring(0, endIndex);
		int startIndex = 0;
		while (startIndex < endIndex) {

			int nextStop = parameters.indexOf(',', startIndex);
			if (nextStop < 0) {
				nextStop = endIndex;
			}

			String parameter = parameters.substring(startIndex, nextStop);

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

	private static void addComponents(Starship ship, String textRepresentation) {
		int cursor = textRepresentation.indexOf(';') + 1;
		int row = 0;
		int col = 0;

		int endIndex = textRepresentation.indexOf(';', cursor);
		if (endIndex < 0) {
			endIndex = textRepresentation.length();
		}

		while (cursor < endIndex) {
			ShipComponent componentToAdd = null;

			char symbol = textRepresentation.charAt(cursor);

			if (symbol == ENGINE_SYMBOL) {
					int engineOutput = 10;
				componentToAdd = new EngineComponent(COMPONENT_INTEGRITY, ENGINE_OUTPUT_SCALE);
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
				case '.':
					break;
				case ',':
					col = 0;
					row++;
					cursor++;
					continue;
				default:
					cursor++;
					continue;
			}
			if (componentToAdd != null) {
				ship.setComponent(componentToAdd, col, row);
			}

			col++;
			cursor++;
		}
	}
}
