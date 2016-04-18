package game;

/**
 * Starts the game
 */
public final class Main {


	private Main() {

	}

	public static void main(String[] args) {

		Runnable psc = new ProjectSpaceComponent();

		psc.run();
	}
}
