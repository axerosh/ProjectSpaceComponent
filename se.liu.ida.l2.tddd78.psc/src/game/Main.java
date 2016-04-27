package game;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Starts the game
 */
public final class Main {


	private Main() {}

	public static void main(String[] args) {
		try {
			String directoryName = "psc_logs";
			boolean directoryCreated = new File(directoryName).mkdir();

			final int maxNumberOfBytes = 100000;
			final int numberOfFiles = 1;
			Handler logHandler = new FileHandler(directoryName + "/psc_error%u.log", maxNumberOfBytes, numberOfFiles);
			Logger globalLogger = Logger.getGlobal();
			globalLogger.addHandler(logHandler);

			if (directoryCreated) {
				globalLogger.log(Level.INFO, "Directory " + directoryName + " created.");
			} else {
				globalLogger.log(Level.INFO, "Directory " + directoryName + " already created.");
			}
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, e.toString(), e);
		}

		Runnable psc = new ProjectSpaceComponent();
		psc.run();
	}
}
