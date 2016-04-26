package ship;

import io.PropertiesIO;
import ship.component.ShipComponent;
import ship.component.utility.EngineComponent;
import ship.component.utility.ReactorComponent;
import ship.component.utility.ShieldComponent;
import ship.component.weapon.WeaponComponent;

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
	private final static char WEAPON_SYMBOL;
	private final static char REACTOR_SYMBOL;
	private final static char SHIELD_SYMBOL;

	private final static float REACTOR_BASE_OUTPUT;
	private final static float REACTOR_OUTPUT_SCALING;
	private final static float ENGINE_BASE_OUTPUT;
	private final static float ENGINE_OUTPUT_SCALING;
	private final static float SHIELD_BASE_OUTPUT;
	private final static float SHIELD_OUTPUT_SCALING;
	private final static float WEAPON_BASE_DAMAGE;
	private final static float WEAPON_DAMAGE_SCALING;
	private final static float WEAPON_BASE_RADIUS;
	private final static float WEAPON_RADIUS_SCALING;
	private final static float WEAPON_BASE_RECHARGE;
	private final static float WEAPON_RECHARGE_SCALING;
	private final static float PROJECTILE_VELOCITY;

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
		final char defaultWeaponSymbol = 'W';
		final char defaultReactorSymbol = 'R';
		final char defaultShieldSymbol = 'S';

		final float defaultComponentIntegrity = 2;

		final float defaultReactorBaseOutput = 3;
		final float defaultReactorOutputScaling = 0.5f;

		final float defaultEngineBaseOutput = 1.5f;
		final float defaultEngineOutputScaling = 0.5f;

		final float defaultShieldBaseOutput = 2;
		final float defaultShieldOutputScaling = 2;

		final float defaultWeaponBaseDamage = 0.5f;
		final float defaultWeaponDamageScaling = 0.5f;

		final float defaultWeaponBaseRadius = 0.5f;
		final float defaultWeaponRadiusScaling = 0.5f;

		final float defaultWeaponBaseRecharge = 2;
		final float defaultWeaponRechargeScale = 0.5f;

		final float defaultProjectileVelocity = 5;

		ENGINE_SYMBOL = PropertiesIO.getCharacterProperty(properties, "engine_symbol", defaultEngineSymbol);
		WEAPON_SYMBOL = PropertiesIO.getCharacterProperty(properties, "weapon_symbol", defaultWeaponSymbol);
		REACTOR_SYMBOL = PropertiesIO.getCharacterProperty(properties, "reactor_symbol", defaultReactorSymbol);
		SHIELD_SYMBOL = PropertiesIO.getCharacterProperty(properties, "shield_symbol", defaultShieldSymbol);

		COMPONENT_INTEGRITY = PropertiesIO.getFloatProperty(properties, "component_integrity", defaultComponentIntegrity);

		REACTOR_BASE_OUTPUT = PropertiesIO.getFloatProperty(properties, "reactor_base_output", defaultReactorBaseOutput);
		REACTOR_OUTPUT_SCALING = PropertiesIO.getFloatProperty(properties, "reactor_output_scaling", defaultReactorOutputScaling);

		ENGINE_BASE_OUTPUT = PropertiesIO.getFloatProperty(properties, "engine_base_output", defaultEngineBaseOutput);
		ENGINE_OUTPUT_SCALING = PropertiesIO.getFloatProperty(properties, "engine_output_scaling", defaultEngineOutputScaling);

		SHIELD_BASE_OUTPUT = PropertiesIO.getFloatProperty(properties, "shield_base_output", defaultShieldBaseOutput);
		SHIELD_OUTPUT_SCALING = PropertiesIO.getFloatProperty(properties, "shield_output_scaling", defaultShieldOutputScaling);

		WEAPON_BASE_DAMAGE = PropertiesIO.getFloatProperty(properties, "weapon_base_damage", defaultWeaponBaseDamage);
		WEAPON_DAMAGE_SCALING = PropertiesIO.getFloatProperty(properties, "weapon_damage_scaling", defaultWeaponDamageScaling);

		WEAPON_BASE_RADIUS = PropertiesIO.getFloatProperty(properties, "weapon_base_radius", defaultWeaponBaseRadius);
		WEAPON_RADIUS_SCALING = PropertiesIO.getFloatProperty(properties, "weapon_radius_scaling", defaultWeaponRadiusScaling);

		WEAPON_BASE_RECHARGE = PropertiesIO.getFloatProperty(properties, "weapon_base_recharge", defaultWeaponBaseRecharge);
		WEAPON_RECHARGE_SCALING =
				PropertiesIO.getFloatProperty(properties, "weapon_recharge_scaling", defaultWeaponRechargeScale);

		PROJECTILE_VELOCITY = PropertiesIO.getFloatProperty(properties, "projectile_velocity", defaultProjectileVelocity);
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
				componentToAdd = getEngineComponent();
			} else if (symbol == REACTOR_SYMBOL) {
				componentToAdd = getReactorComponent();
			} else if (symbol == SHIELD_SYMBOL) {
				componentToAdd = getShieldComponent();
			} else if (symbol == WEAPON_SYMBOL) {
				componentToAdd = getWeaponComponent();
			} else if (symbol == ',') {
				col = 0;
				row++;
				cursor++;
				continue;
			} else if (symbol != '.') {
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

	public static EngineComponent getEngineComponent() {
		return new EngineComponent(COMPONENT_INTEGRITY, ENGINE_BASE_OUTPUT, ENGINE_OUTPUT_SCALING, ENGINE_SYMBOL);
	}

	public static ReactorComponent getReactorComponent() {
		return new ReactorComponent(COMPONENT_INTEGRITY, REACTOR_BASE_OUTPUT, REACTOR_SYMBOL);
	}

	public static ShieldComponent getShieldComponent() {
		return new ShieldComponent(COMPONENT_INTEGRITY, SHIELD_BASE_OUTPUT, SHIELD_OUTPUT_SCALING, SHIELD_SYMBOL);
	}

	public static WeaponComponent getWeaponComponent() {
		return new WeaponComponent(COMPONENT_INTEGRITY, WEAPON_BASE_DAMAGE, WEAPON_DAMAGE_SCALING, WEAPON_BASE_RADIUS,
								   WEAPON_RADIUS_SCALING, PROJECTILE_VELOCITY, WEAPON_BASE_RECHARGE, WEAPON_RECHARGE_SCALING,
								   WEAPON_SYMBOL);
	}
}
