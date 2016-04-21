package game;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Starts the game
 */
public final class Main {


	private Main() {

	}

	public static void main(String[] args) {

		Logger logger = Logger.getLogger("psc");

		try {
			final int maxNumberOfBytes = 100000;
			final int numberOfFiles = 1;
			Handler logHandler = new FileHandler("psc_errors.log", maxNumberOfBytes, numberOfFiles);
			logger.addHandler(logHandler);
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
		}

		Runnable psc = new ProjectSpaceComponent(logger);
		psc.run();
	}
}
