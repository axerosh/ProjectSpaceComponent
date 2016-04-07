package ship;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;

/**
 * Utility class for saving and loading Staship designs.
 *
 * @see Starship
 */
public final class ShipIO {

	private final static String SAVE_FOLDER =
			"/home/axeno840/IdeaProjects/TDDD78/ProjectSpaceComponent/se.liu.ida.l2.tddd78.psc/resources/ship_designs/";
	private final static String SAVE_FORMAT = ".ship";

	private ShipIO() {
	}

	/**
	 * Saves the specified ship to the specified path.
	 *
	 * @param ship a starship to save
	 * @return a text representation of the the specified ship
	 */
	public static void save(Starship ship, String fileName) {
		String filePath = SAVE_FOLDER + fileName + SAVE_FORMAT;
		/*try (ObjectOutputStream os =
					 new ObjectOutputStream(
							 new BufferedOutputStream(
							 	new FileOutputStream(filePath)))) {
			String textRepresentation = ship.getTextRepresentation();
			os.writeUTF(textRepresentation);
			System.out.println("Saved to " + filePath);*/
		try (OutputStream os = new FileOutputStream(filePath)) {
			String textRepresentation = ship.getTextRepresentation();
			byte[] byteOutput = textRepresentation.getBytes("UTF-8");
			os.write(byteOutput);
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
		String filePath = SAVE_FOLDER + fileName + SAVE_FORMAT;
		try (ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)))) {
			String textRepresentation = is.readUTF();
			Starship ship = ShipFactory.getStarship(x, y, textRepresentation);
			return ship;
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}
