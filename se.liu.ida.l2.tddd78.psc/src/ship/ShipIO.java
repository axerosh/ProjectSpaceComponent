package ship;

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

	private final static File SAVE_LOCATION;
	private final static String SAVE_EXTENSION = ".ship";
	private final static Charset CHARSET = StandardCharsets.UTF_8;

	static {
		final File project = new File("se.liu.ida.l2.tddd78.psc");
		final File resources = new File(project, "resources");
		SAVE_LOCATION = new File(resources, "ship_designs");
		//SAVE_LOCATION = new File(resources, "ship_designs");
	}

	private ShipIO() {
	}

	/**
	 * Saves the specified ship to the specified path.
	 *
	 * @param ship a starship to save
	 * @param fileName the name of the file to which the ship is saved (excluding file extension)
	 * @param logger the Logger that is to log any exceptions that might occur.
	 */
	public static void save(Starship ship, String fileName, Logger logger) {
		File filePath = new File(SAVE_LOCATION, fileName + SAVE_EXTENSION);
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), CHARSET))) {
			String textRepresentation = ship.getTextRepresentation();
			writer.write(textRepresentation);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
	}

	/**
	 * Returns a ship loaded from the specified path.
	 *
	 * @param fileName the name of the file to which the ship is saved (excluding file extension)
	 * @param logger the Logger that is to log any exceptions that might occur.
	 * @return a ship loaded from the specified path
	 */
	public static Starship load(String fileName, Logger logger) {
		File filePath = new File(SAVE_LOCATION, fileName + SAVE_EXTENSION);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), CHARSET))) {
			StringBuilder textRepresentation = new StringBuilder();

			String nextLine = reader.readLine();
			while (nextLine != null) {
				textRepresentation.append(nextLine);
				nextLine = reader.readLine();
			}

			return ShipFactory.getStarship(textRepresentation.toString());

		} catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}
		return null;
	}
}