package ship;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Entry point for generating Properties files.
 */
public final class PropertiesCreator {

	private PropertiesCreator() {}

	public static void main(String[] args) {

		final String fileName = "game";
		//final String fileName = "shipsaves";

		final String fileExtension = ".properties";
		final File project = new File("se.liu.ida.l2.tddd78.psc");
		final File resources = new File(project, "resources");
		final File saveLocation = new File(resources, "properties");
		final File filePath = new File(saveLocation, fileName + fileExtension);
		final Properties properties = new Properties();

		properties.setProperty("max_framerate", "60");
		properties.setProperty("menu_width", "10");
		properties.setProperty("menu_height", "20");
		properties.setProperty("menu_scale", "40.0");
		properties.setProperty("workshop_width", "16");
		properties.setProperty("workshop_height", "9");
		properties.setProperty("workshop_scale", "80.0");
		properties.setProperty("battlespace_width", "32");
		properties.setProperty("battlespace_height", "18");
		properties.setProperty("battlespace_scale", "40.0");
		properties.setProperty("ship_width", "14");
		properties.setProperty("ship_height", "8");

		/*shipProperties.setProperty("savePath", "se.liu.ida.l2.tddd78.psc/resources/ship_designs");
		shipProperties.setProperty("fileExtension", ".ship");
		shipProperties.setProperty("engine", "E");
		shipProperties.setProperty("reactor", "R");
		shipProperties.setProperty("shield", "S");
		shipProperties.setProperty("missile", "M");*/

		try (FileOutputStream out = new FileOutputStream(filePath)) {
			properties.store(out, "Project Space Component game settings");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
