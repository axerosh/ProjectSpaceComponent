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

		String fileName = "componentRepresentations";
		String saveFormat = ".properties";

		File project = new File("se.liu.ida.l2.tddd78.psc");
		File resources = new File(project, "resources");
		File saveLocation = new File(resources, "properties");
		File filePath = new File(saveLocation, fileName + saveFormat);

		Properties shipProperties = new Properties();
		shipProperties.setProperty("engine", "E");
		shipProperties.setProperty("reactor", "R");
		shipProperties.setProperty("shield", "S");
		shipProperties.setProperty("missile", "M");

		try (FileOutputStream out = new FileOutputStream(filePath)) {
			shipProperties.store(out, "the characters that represent each component type. If more than one character is " +
									  "defined for a singel component, the first oen will be used.");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
