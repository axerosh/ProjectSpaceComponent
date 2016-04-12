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

/**
 * Utility class for saving and loading Staship designs.
 *
 * @see Starship
 */
public final class ShipIO {

	private final static File SAVE_LOCATION;
	private final static String SAVE_FORMAT = ".ship";
	private final static Charset CHARSET = StandardCharsets.UTF_8;

	static {
		File project = new File("se.liu.ida.l2.tddd78.psc");
		File resources = new File(project, "resources");
		SAVE_LOCATION = new File(resources, "ship_designs");
	}

	private ShipIO() {
	}

	/**
	 * Saves the specified ship to the specified path.
	 *
	 * @param ship a starship to save
	 */
	public static void save(Starship ship, String fileName) {
		File filePath = new File(SAVE_LOCATION, fileName + SAVE_FORMAT);
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), CHARSET))) {
			String textRepresentation = ship.getTextRepresentation();
			writer.write(textRepresentation);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Returns a ship loaded from the specified path.
	 *
	 * @param x       the x position at which the ship is to be located
	 * @param y       the y position at which the ship is to be located
	 * @return a ship loaded from the specified path
	 */
	public static Starship load(float x, float y, String fileName) {
		File filePath = new File(SAVE_LOCATION, fileName + SAVE_FORMAT);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), CHARSET))) {
			StringBuilder textRepresentation = new StringBuilder();

			String nextLine = reader.readLine();
			while (nextLine != null) {
				textRepresentation.append(nextLine);
				nextLine = reader.readLine();
			}

			return ShipFactory.getStarship(x, y, textRepresentation.toString());

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}
