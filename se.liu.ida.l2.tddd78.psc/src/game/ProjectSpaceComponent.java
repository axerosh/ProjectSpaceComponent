package game;

import control.BasicAI;
import control.MouseController;
import graphics.Displayer;
import graphics.PSCFrame;
import ship.ShipIO;
import ship.Starship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A session of the game ProjectSpaceComponent.
 */
public class ProjectSpaceComponent implements Runnable {

	private final JFrame frame;
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
		//gameDisplayer = new Displayer(battleSpace, battleSpaceScale);
		ais = new HashSet<>();

		frame = new PSCFrame(this);

		frame.add(gameDisplayer);
		frame.setVisible(true);
		frame.pack();

		gamemode = Gamemode.WORKSHOP;
		playerController = new MouseController(this, gamemode);
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

		timer.setCoalesce(true);

		//TODO Add to a match generator or something
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

		Starship enemyShip = ShipIO.load("the_governator");
		if (enemyShip == null) {
			enemyShip = new Starship(shipWidth, shipHeight, defaultShipIntegrity);
		}
		ais.add(new BasicAI(battleSpace, enemyShip));
		battleSpace.addShip(enemyShip, team2);

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
		System.out.println(matchingBattle || matchingWorkshop);

		if (!(matchingBattle || matchingWorkshop)) {
			String message = "The game environment " + gameEnvironment + " should not be displayed during gamemode " + gamemode +
							" do not match.";
			IllegalStateException exception = new IllegalStateException(message);
			Logger.getGlobal().log(Level.SEVERE, message, exception);
			throw exception;
		}

		timer.start();

	}

	/**
	 * Loads game settings from properties.
	 */
	private void loadSettings() {
		final String fileName = "game";
		final String fileExtension = ".properties";
		final File project = new File("se.liu.ida.l2.tddd78.psc");
		final File resources = new File(project, "resources");
		final File saveLocation = new File(resources, "properties");
		final File filePath = new File(saveLocation, fileName + fileExtension);

		final Properties properties = new Properties();
		try (InputStream in = new FileInputStream(filePath)) {
			properties.load(in);
		} catch (IOException e) {
			Logger.getGlobal().log(Level.SEVERE, e.toString(), e);
		}

		final int defaultWorkshopWidth = 16;
		final int defaultWorkshopHeight = 9;
		final float defaultWorkshopScale = 80;

		final int defaultArenaWidth = 32;
		final int defaultArenaHeight = 18;
		final float defaultArenaScale = 40;

		final int defaultShipWidth = 14;
		final int defaultShipHeight = 8;

		final int defaultMaxFramerate = 60;

		workshopWidth = getIntegerProperty(properties, "workshop_width", defaultWorkshopWidth);
		workshopHeight = getIntegerProperty(properties, "workshop_height", defaultWorkshopHeight);
		workshopScale = getFloatProperty(properties, "workshop_scale", defaultWorkshopScale);

		battleSpaceWidth = getIntegerProperty(properties, "battlespace_width", defaultArenaWidth);
		battleSpaceHeight = getIntegerProperty(properties, "battlespace_height", defaultArenaHeight);
		battleSpaceScale = getFloatProperty(properties, "battlespace_scale", defaultArenaScale);

		shipWidth = getIntegerProperty(properties, "ship_width", defaultShipWidth);
		shipHeight = getIntegerProperty(properties, "ship_height", defaultShipHeight);

		maxFramerate = getIntegerProperty(properties, "max_framerate", defaultMaxFramerate);
	}

	private int getIntegerProperty(Properties properties, String propertyName, int defaultValue) {
		String property = properties.getProperty(propertyName);
		if(property != null){
			return Integer.parseInt(property);
		} else {
			Logger.getGlobal().log(Level.SEVERE, "Couldn't find a value for property" + propertyName +
												 ". Using the default value of " + defaultValue);
			return defaultValue;
		}
	}

	private float getFloatProperty(Properties properties, String propertyName, float defaultValue) {
		String property = properties.getProperty(propertyName);
		if(property != null){
			return Float.parseFloat(property);
		} else {
			Logger.getGlobal().log(Level.SEVERE, "Couldn't find a value for property" + propertyName +
												 ". Using the default value of " + defaultValue);
			return defaultValue;
		}
	}

	public void changeGamemode() {

		int screenWidth = 0;
		int screenHeight = 0;
		int frameWidth = frame.getWidth() - gameDisplayer.getWidth();
		int frameHeight = frame.getHeight() - gameDisplayer.getHeight();


		switch (gamemode) {

			case WORKSHOP:
				workshop.removeShip();
				gamemode = Gamemode.BATTLE;
				battleSpace.pack(shipWidth, shipHeight);
				/*gameDisplayer.setDisplayedEnvironment(battleSpace);
				screenWidth = (int )(battleSpaceWidth * battleSpaceScale);
				screenHeight = (int) (battleSpaceHeight * battleSpaceScale);*/
				screenWidth = (int )(battleSpaceWidth * battleSpaceScale);
				screenHeight = (int) (battleSpaceHeight * battleSpaceScale);
				gameDisplayer.setDisplayedEnvironment(battleSpace, battleSpaceScale);


				break;

			case BATTLE:
				gamemode = Gamemode.WORKSHOP;
				workshop.addWorkingShip(playerShip);
				screenWidth = (int )(workshopWidth * workshopScale);
				screenHeight = (int) (workshopHeight * workshopScale);
				gameDisplayer.setDisplayedEnvironment(workshop, workshopScale);
				break;
		}

		playerController.setGamemode(gamemode);
		playerController.setBounds(0, 0, screenWidth , screenHeight);

		/*frame.pack() does not get the job done unless the frame has been updated by adding/removing components and we only
		resize an excisting component. Therefor its prefferedSize() method is not called and the frame's size becomes unaccurate.
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
