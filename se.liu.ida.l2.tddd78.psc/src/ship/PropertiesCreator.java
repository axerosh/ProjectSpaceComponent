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

		String fileName = "ship_saves";
		String fileExtension = ".properties";

		File project = new File("se.liu.ida.l2.tddd78.psc");
		File resources = new File(project, "resources");
		File saveLocation = new File(resources, "properties");
		File filePath = new File(saveLocation, fileName + fileExtension);

		Properties shipProperties = new Properties();
		shipProperties.setProperty("savePath", "se.liu.ida.l2.tddd78.psc/resources/ship_designs");
		shipProperties.setProperty("fileExtension", ".ship");
		shipProperties.setProperty("engine", "E");
		shipProperties.setProperty("reactor", "R");
		shipProperties.setProperty("shield", "S");
		shipProperties.setProperty("missile", "M");

		try (FileOutputStream out = new FileOutputStream(filePath)) {
			shipProperties.store(out, "Starship save/load settings");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
