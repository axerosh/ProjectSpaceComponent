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
 * Utility class for saving and loading Staship designs.
 *
 * @see Starship
 */
public final class ShipIO {

	//Static because there is but one save location for ships.
	private final static File SAVE_LOCATION;

	//Static because there is but one file extension for ships.
	private final static String FILE_EXTENSION = ".ship";

	//Static because ships are to always be saved with the same charset.
	private final static Charset CHARSET = StandardCharsets.UTF_8;

	static {
		final File resources = new File("resources");
		SAVE_LOCATION = new File(resources, "ship_designs");
	}

	private ShipIO() {
	}

	/**
	 * Saves the specified ship to the specified path.
	 *
	 * @param ship a starship to save
	 * @param fileName the name of the file to which the ship is saved (excluding file extension)
	 */
	//static so that ships can be saved from anywhere without needing to create an instance of ShipIO
	public static void save(Starship ship, String fileName) {
		File filePath = new File(SAVE_LOCATION, fileName + FILE_EXTENSION);
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), CHARSET))) {
			String textRepresentation = ship.getTextRepresentation();
			writer.write(textRepresentation);
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, e.toString(), e);
		}
	}

	/**
	 * Returns a ship loaded from the specified path.
	 *
	 * @param fileName the name of the file to which the ship is saved (excluding file extension)
	 * @return a ship loaded from the specified path
	 */
	//static so that ships can be loaded from anywhere without needing to create an instance of ShipIO
	public static Starship load(String fileName) {
		File filePath = new File(SAVE_LOCATION, fileName + FILE_EXTENSION);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), CHARSET))) {
			StringBuilder textRepresentation = new StringBuilder();

			String nextLine = reader.readLine();
			while (nextLine != null) {
				textRepresentation.append(nextLine);
				nextLine = reader.readLine();
			}

			return ShipFactory.getStarship(textRepresentation.toString());

		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, e.toString(), e);
		}
		return null;
	}


	public static void loadToShips(String fileName, Iterable<Starship> starShips) {
		File filePath = new File(SAVE_LOCATION, fileName + FILE_EXTENSION);


		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), CHARSET))) {
			StringBuilder textRepresentation = new StringBuilder();

			String nextLine = reader.readLine();
			while (nextLine != null) {
				textRepresentation.append(nextLine);
				nextLine = reader.readLine();
			}
			for (Starship starShip : starShips) {
				starShip.clearComponents();
				ShipFactory.setComponents(starShip, textRepresentation.toString());
			}

		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, e.toString(), e);
			JOptionPane.showMessageDialog(null, "No ship with that name exits.");
		}
	}
}
