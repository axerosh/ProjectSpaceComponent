package game;

import control.BasicAI;
import control.MouseController;
import graphics.Displayer;
import graphics.PSCFrame;
import io.PropertiesIO;
import io.ShipIO;
import ship.Starship;

import javax.swing.AbstractAction;
import javax.swing.Timer;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A session of the game ProjectSpaceComponent.
 */
public class ProjectSpaceComponent implements Runnable {

	private final PSCFrame frame;
	private final Timer timer;
	private final Workshop workshop;
	private final BattleSpace battleSpace;
	private final MouseController playerController;
	private Displayer gameDisplayer;

	private int workshopWidth;
	private int workshopHeight;
	private float workshopScale;
	private int battleSpaceWidth;
	private int battleSpaceHeight;
	private float battleSpaceScale;
	private int shipWidth;
	private int shipHeight;
	private float maxFramerate;
	private Gamemode gamemode;
	private Starship playerShip;
	private Set<BasicAI> ais;

	public ProjectSpaceComponent() {

		loadSettings();

		workshop = new Workshop(workshopWidth, workshopHeight, shipWidth, shipHeight);
		battleSpace = new BattleSpace(battleSpaceWidth, battleSpaceHeight);

		gameDisplayer = new Displayer(workshop, workshopScale);
		ais = new HashSet<>();

		frame = new PSCFrame(this);

		frame.add(gameDisplayer);
		frame.setVisible(true);
		frame.pack();

		gamemode = Gamemode.WORKSHOP;
		playerController = new MouseController(this, gamemode);
		int screenWidth = (int) (workshopWidth * workshopScale);
		int screenHeight = (int) (workshopHeight * workshopScale);
		playerController.setBounds(0, 0, screenWidth, screenHeight);
		frame.add(playerController);


		final long millisPerSecond = 1000;
		int wantedBetweenUpdates = Math.round(millisPerSecond / maxFramerate);
		int timeBetweenUpdates = (int) Math.ceil(wantedBetweenUpdates);

		timer = new Timer(timeBetweenUpdates, new AbstractAction() {
			private long lastTime = System.nanoTime();

			@Override public void actionPerformed(final ActionEvent e) {
				final long nanosPerSecond = 1000000000;
				float passedSeconds = (float) (System.nanoTime() - lastTime) / nanosPerSecond;
				lastTime = System.nanoTime();

				if (gamemode == Gamemode.BATTLE) {
					for (BasicAI ai : ais) {
						ai.update();
					}

					battleSpace.update(passedSeconds);
				}
				gameDisplayer.repaint();

			}
		});

		timer.setCoalesce(false);

		final Team team1 = new Team("Team 1");
		final Team team2 = new Team("Team 2");
		battleSpace.addTeam(team1);
		battleSpace.addTeam(team2);

		final int defaultShipIntegrity = 10;

		playerShip = ShipIO.load("the_manta");
		if (playerShip == null) {
			playerShip = new Starship(shipWidth, shipHeight, defaultShipIntegrity);
		}
		playerController.setControlledShip(playerShip);
		battleSpace.addShip(playerShip, team1);
		workshop.addWorkingShip(playerShip);

		Starship enemyShip1 = ShipIO.load("the_governator");
		if (enemyShip1 == null) {
			enemyShip1 = new Starship(shipWidth, shipHeight, defaultShipIntegrity);
		}
		enemyShip1.rotate180();
		ais.add(new BasicAI(battleSpace, enemyShip1));
		battleSpace.addShip(enemyShip1, team2);

		Starship enemyShip2 = ShipIO.load("the_manta");
		if (enemyShip2 == null) {
			enemyShip2 = new Starship(shipWidth, shipHeight, defaultShipIntegrity);
		}
		enemyShip2.rotate180();
		ais.add(new BasicAI(battleSpace, enemyShip2));
		battleSpace.addShip(enemyShip2, team2);

		Starship friendlyShip = ShipIO.load("the_governator");
		if (friendlyShip == null) {
			friendlyShip = new Starship(shipWidth, shipHeight, defaultShipIntegrity);
		}
		ais.add(new BasicAI(battleSpace, friendlyShip));
		battleSpace.addShip(friendlyShip, team1);

	}

	@Override public void run() {
		Class<?> gameEnvironment = gameDisplayer.getDisplayedEnvironment().getClass();

		boolean matchingBattle = gameEnvironment.equals(BattleSpace.class) && gamemode == Gamemode.BATTLE;
		boolean matchingWorkshop = gameEnvironment.equals(Workshop.class) && gamemode == Gamemode.WORKSHOP;

		if (!(matchingBattle || matchingWorkshop)) {
			String message =
					"The game environment " + gameEnvironment + " should not be displayed during gamemode " + gamemode +
					" do not match.";
			IllegalStateException exception = new IllegalStateException(message);
			Logger.getGlobal().log(Level.SEVERE, message, exception);
			System.exit(1);
		}

		timer.start();

	}

	/**
	 * Loads game settings from properties.
	 */
	private void loadSettings() {

		final int defaultWorkshopWidth = 16;
		final int defaultWorkshopHeight = 9;
		final float defaultWorkshopScale = 80;

		final int defaultArenaWidth = 32;
		final int defaultArenaHeight = 18;
		final float defaultArenaScale = 40;

		final int defaultShipWidth = 14;
		final int defaultShipHeight = 8;

		final int defaultMaxFramerate = 60;

		final Properties properties = PropertiesIO.loadProperties("game");

		workshopWidth = PropertiesIO.getIntegerProperty(properties, "workshop_width", defaultWorkshopWidth);
		workshopHeight = PropertiesIO.getIntegerProperty(properties, "workshop_height", defaultWorkshopHeight);
		workshopScale = PropertiesIO.getFloatProperty(properties, "workshop_scale", defaultWorkshopScale);

		battleSpaceWidth = PropertiesIO.getIntegerProperty(properties, "battlespace_width", defaultArenaWidth);
		battleSpaceHeight = PropertiesIO.getIntegerProperty(properties, "battlespace_height", defaultArenaHeight);
		battleSpaceScale = PropertiesIO.getFloatProperty(properties, "battlespace_scale", defaultArenaScale);

		shipWidth = PropertiesIO.getIntegerProperty(properties, "ship_width", defaultShipWidth);
		shipHeight = PropertiesIO.getIntegerProperty(properties, "ship_height", defaultShipHeight);

		maxFramerate = PropertiesIO.getIntegerProperty(properties, "max_framerate", defaultMaxFramerate);
	}

	public void changeGamemode() {

		int screenWidth = 0;
		int screenHeight = 0;
		int frameWidth = frame.getWidth() - gameDisplayer.getWidth();
		int frameHeight = frame.getHeight() - gameDisplayer.getHeight();


		switch (gamemode) {

			case WORKSHOP:
				playerShip = workshop.getWorkingShip();
				workshop.removeShip();
				frame.getJMenuBar().remove(frame.getSaveLoad());
				gamemode = Gamemode.BATTLE;
				battleSpace.reset();
				battleSpace.pack(shipWidth, shipHeight);
				screenWidth = (int) (battleSpaceWidth * battleSpaceScale);
				screenHeight = (int) (battleSpaceHeight * battleSpaceScale);
				gameDisplayer.setDisplayedEnvironment(battleSpace, battleSpaceScale);
				break;

			case BATTLE:
				gamemode = Gamemode.WORKSHOP;
				frame.getJMenuBar().add(frame.getSaveLoad());
				workshop.addWorkingShip(playerShip);
				playerShip.restore();
				screenWidth = (int) (workshopWidth * workshopScale);
				screenHeight = (int) (workshopHeight * workshopScale);
				gameDisplayer.setDisplayedEnvironment(workshop, workshopScale);
				break;
		}

		playerController.setGamemode(gamemode);
		playerController.setBounds(0, 0, screenWidth, screenHeight);

		/*frame.pack() does not get the job done unless the frame has been updated by adding/removing components and we only
		resize an excisting component. Therefor its prefferedSize() method is not called and the frame's size becomes
		unaccurate.
		Instead, we needed our own pack.*/
		Dimension gameDisplayerSize = gameDisplayer.getPreferredSize();
		frame.setSize(gameDisplayerSize.width + frameWidth, gameDisplayerSize.height + frameHeight);

		frame.repaint();
	}


	public Workshop getWorkshop() {
		return workshop;
	}

	public BattleSpace getBattleSpace() {
		return battleSpace;
	}

	public Displayer getGameDisplayer() {
		return gameDisplayer;
	}

	public Starship getPlayerShip() {
		return playerShip;
	}

	public static enum Gamemode {
		/**
		 * Workshop (ship editing) mode.
		 */
		WORKSHOP,

		/**
		 * Battle mode.
		 */
		BATTLE
	}
}
