package ship;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Entry poitn for saving Properties for ships.
 */
public final class PropertiesCreator {

	private PropertiesCreator() {}

	public static void main(String[] args) {

		String fileName = "game";
		//String fileName = "shipsaves";

		String fileExtension = ".properties";
		File project = new File("se.liu.ida.l2.tddd78.psc");
		File resources = new File(project, "resources");
		File saveLocation = new File(resources, "properties");
		File filePath = new File(saveLocation, fileName + fileExtension);
		Properties shipProperties = new Properties();

		shipProperties.setProperty("max_framerate", "60");
		shipProperties.setProperty("game_width", "32");
		shipProperties.setProperty("game_height", "18");
		shipProperties.setProperty("workshop_width", "16");
		shipProperties.setProperty("workshop_height", "9");
		shipProperties.setProperty("menu_width", "10");
		shipProperties.setProperty("menu_height", "20");
		shipProperties.setProperty("ship_width", "14");
		shipProperties.setProperty("ship_height", "8");

		/*shipProperties.setProperty("savePath", "se.liu.ida.l2.tddd78.psc/resources/ship_designs");
		shipProperties.setProperty("fileExtension", ".ship");
		shipProperties.setProperty("engine", "E");
		shipProperties.setProperty("reactor", "R");
		shipProperties.setProperty("shield", "S");
		shipProperties.setProperty("missile", "M");*/

		try (FileOutputStream out = new FileOutputStream(filePath)) {
			shipProperties.store(out, "Project Space Component game settings");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
