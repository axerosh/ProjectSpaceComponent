package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for loading properties files and read properties from Properties objects
 */
public final class PropertiesIO {

	private PropertiesIO() {}

	/**
	 * Loads the properties file with the specified name.
	 *
	 * @param fileName the fiel name (excluding file extension)
	 *
	 * @return the loaded Properties object
	 */
	//Static because one should not have to instanciate PropertiesIO to load files.
	public static Properties loadProperties(String fileName) {
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

		return properties;
	}

	//Static because one should not have to instanciate PropertiesIO to read properties.
	public static char getCharacterProperty(Properties properties, String propertyName, char defaultValue) {
		String property = properties.getProperty(propertyName);
		if (property != null && !property.isEmpty()) {
			return property.charAt(0);
		} else {
			Logger.getGlobal().log(Level.SEVERE, "Couldn't find a value for property " + propertyName +
												 ". Using the default value of " + defaultValue);
			return defaultValue;
		}
	}

	//Static because one should not have to instanciate PropertiesIO to read properties.
	public static int getIntegerProperty(Properties properties, String propertyName, int defaultValue) {
		String value = properties.getProperty(propertyName);
		if (value != null) {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException e) {
				Logger.getGlobal().log(Level.SEVERE, "Couldn't use value " + value + " for property " + propertyName +
													 " because it isn't a valid integer. Using the default value of " +
													 defaultValue, e);
				return defaultValue;
			}
		} else {
			Logger.getGlobal().log(Level.SEVERE, "Couldn't find a value for property " + propertyName +
												 ". Using the default value of " + defaultValue);
			return defaultValue;
		}
	}

	//Static because one should not have to instanciate PropertiesIO to read properties.
	public static float getFloatProperty(Properties properties, String propertyName, float defaultValue) {
		String value = properties.getProperty(propertyName);
		if (value != null) {
			try {
				return Float.parseFloat(value);
			} catch (NumberFormatException e) {
				Logger.getGlobal().log(Level.SEVERE, "Couldn't use value " + value + " for property " + propertyName +
													 " because it isn't a valid float number. Using the default value of " +
													 defaultValue, e);
				return defaultValue;
			}
		} else {
			Logger.getGlobal().log(Level.SEVERE, "Couldn't find a value for property " + propertyName +
												 ". Using the default value of " + defaultValue);
			return defaultValue;
		}
	}
}
