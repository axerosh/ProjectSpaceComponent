package io;

import ship.ShipFactory;
import ship.Starship;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for saving and loading Staship designs from textrepresentation
 *
 * @see Starship
 */
public final class ShipIO {

	//Static because there is but one save location for ships.
	private final static File SAVE_LOCATION = new File("psc_ship_designs");

	//Static because there is but one file extension for ships.
	private final static String FILE_EXTENSION = ".ship";

	//Static because ships are to always be saved with the same charset.
	private final static Charset CHARSET = StandardCharsets.UTF_8;

	private ShipIO() {
	}

	/**
	 * Saves the specified ship to the specified path.
	 *
	 * @param ship     a starship to save
	 * @param fileName the name of the file to which the ship is saved (excluding file extension)
	 */
	//static so that ships can be saved from anywhere without needing to create an instance of ShipIO
	public static void save(Starship ship, String fileName) {
		File filePath = new File(SAVE_LOCATION, fileName + FILE_EXTENSION);
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), CHARSET))) {
			String textRepresentation = ship.getTextRepresentation();
			writer.write(textRepresentation);
		} catch (IOException | SecurityException e) {
			Logger.getGlobal().log(Level.SEVERE, e.toString(), e);
		}
	}

	/**
	 * Returns a loaded ship with the specified name.
	 *
	 * @param fileName the name of the file to which the ship is saved (excluding file extension)
	 *
	 * @return a ship loaded from the specified path
	 */
	//static so that ships can be loaded from anywhere without needing to create an instance of ShipIO
	public static Starship load(String fileName) {
		String textRepresentation = loadTextRepresentation(fileName);

		if (textRepresentation == null) {
			return null;
		} else {
			return ShipFactory.getStarship(textRepresentation);
		}
	}

	/**
	 * Sets the components of the ships in the specifed ship list, so that they match the a loaded ship representation with the
	 * specified name.
	 *
	 * @param fileName  the name if the ship representation
	 * @param starShips the ships that are to match the ship representation
	 */
	public static void loadToShips(String fileName, Iterable<Starship> starShips) {
		String textRepresentation = loadTextRepresentation(fileName);

		if (textRepresentation == null) {
			JOptionPane.showMessageDialog(null, "No ship with that name exits.");
		} else {
			for (Starship starShip : starShips) {
				starShip.clearComponents();
				ShipFactory.setComponents(starShip, textRepresentation);
			}
		}
	}

	/**
	 * Loads the ship representation with the specified name.
	 *
	 * @param fileName the name of the ship representation
	 *
	 * @return the loaded ship representation; null if a failure occurs
	 */
	private static String loadTextRepresentation(String fileName) {
		File filePath = new File(SAVE_LOCATION, fileName + FILE_EXTENSION);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), CHARSET))) {
			StringBuilder textRepresentation = new StringBuilder();

			String nextLine = reader.readLine();
			while (nextLine != null) {
				textRepresentation.append(nextLine);
				nextLine = reader.readLine();
			}
			return textRepresentation.toString();

		} catch (IOException | SecurityException e) {
			Logger.getGlobal().log(Level.WARNING, e.toString(), e);
			return null;
		}
	}
}
