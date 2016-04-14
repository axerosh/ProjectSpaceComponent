package game;

import control.BasicAI;
import control.MouseAndKeyboard;
import graphics.PSCFrame;
import graphics.displayers.BattleSpaceDisplayer;
import graphics.displayers.MenuDisplayer;
import graphics.displayers.WorkshopDisplayer;
import ship.ShipIO;
import ship.Starship;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ProjectSpaceComponent implements Runnable {



	private int menuWidth;
	private int menuHeight;
	private float menuScale;
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
	private final JFrame frame;
	private final Timer timer;
	private final Menu menu;
	private final MenuDisplayer menuDisplayer;
	private final Workshop workshop;
	private final WorkshopDisplayer workshopDisplayer;
	private final BattleSpace battleSpace;
	private final BattleSpaceDisplayer battleSpaceDisplayer;
	private final MouseAndKeyboard playerController;
	private Starship playerShip;
	private BasicAI ai;

	public ProjectSpaceComponent() {

		loadSettings();

		gamemode = Gamemode.MENU;

		menu = new Menu();
		menuDisplayer = new MenuDisplayer(menu, menuScale, menuWidth, menuHeight);

		workshop = new Workshop(workshopWidth, workshopHeight, workshopScale);
		workshopDisplayer = new WorkshopDisplayer(workshop, workshopScale, workshopWidth, workshopHeight);

		battleSpace = new BattleSpace();
		battleSpaceDisplayer = new BattleSpaceDisplayer(battleSpace, battleSpaceScale, battleSpaceWidth, battleSpaceHeight);

		playerController = new MouseAndKeyboard(this, gamemode);//battleSpace, battleSpaceDisplayer, workshopDisplayer,
												//menuDisplayer, workshop, gamemode);

		frame = new PSCFrame();
		frame.add(menuDisplayer);
		frame.pack();
		//frame.setResizable(false);
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

				if (gamemode == Gamemode.WORKSHOP) {
					workshop.update();
					workshopDisplayer.repaint();
				} else if (gamemode == Gamemode.BATTLE) {
					ai.update();
					battleSpace.update(passedSeconds);
					battleSpaceDisplayer.repaint();
				}

			}
		});

		timer.setCoalesce(true);

		//TODO Add to a match generator or something
		final Team team1 = new Team("Team 1");
		final Team team2 = new Team("Team 2");
		battleSpace.addTeam(team1);
		battleSpace.addTeam(team2);


		playerShip = ShipIO.load("the_manta");
		playerController.setControlledShip(playerShip);
		battleSpace.addShip(playerShip, team1);

		Starship enemyShip = ShipIO.load("the_governator");
		ai = new BasicAI(battleSpace, enemyShip);
		battleSpace.addShip(enemyShip, team2);
		battleSpace.placeShip(enemyShip);
	}

	@Override public void run() {

		timer.start();
	}

	public static enum Gamemode {
		/**
		 * Menu mode.
		 */
		MENU,

		/**
		 * Workshop (ship editing) mode.
		 */
		WORKSHOP,

		/**
		 * Battle mode.
		 */
		BATTLE
	}

	private void loadSettings(){
		final String fileName = "game";
		final String fileExtension = ".properties";
		final File project = new File("se.liu.ida.l2.tddd78.psc");
		final File resources = new File(project, "resources");
		final File saveLocation = new File(resources, "properties");
		final File filePath = new File(saveLocation, fileName + fileExtension);

		final Properties properties = new Properties();
		try(InputStream in = new FileInputStream(filePath)){
			properties.load(in);
		}catch(IOException e){
			System.out.println(e.getMessage());
		}

		final int defaultMenuWidth = 10;
		final int defaultMenuHeight = 20;
		final float defaultMenuScale = 40;

		final int defaultWorkshopWidth = 16;
		final int defaultWorkshopHeight = 9;
		final float defaultWorkshopScale = 80;

		final int defaultArenaWidth = 32;
		final int defaultArenaHeight = 18;
		final float defaultArenaScale = 40;

		final int defaultShipWidth = 14;
		final int defaultShipHeight = 8;

		final int defaultMaxFramerate = 60;

		menuWidth = getIntegerProperty(properties, "menu_width", defaultMenuWidth);
		menuHeight = getIntegerProperty(properties, "menu_height", defaultMenuHeight);
		menuScale = getFloatProperty(properties, "menu_scale", defaultMenuScale);

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
			System.out.println("Couldn't find a value for " + propertyName + ". Using the default value of " + defaultValue);
			return defaultValue;
		}
	}

	private float getFloatProperty(Properties properties, String propertyName, float defaultValue) {
		String property = properties.getProperty(propertyName);
		if(property != null){
			return Float.parseFloat(property);
		} else {
			System.out.println("Couldn't find a value for " + propertyName + ". Using the default value of " + defaultValue);
			return defaultValue;
		}
	}



	public void changeGamemode(Gamemode desiredMode)
	{
		switch (gamemode) {
			case MENU:
				frame.remove(menuDisplayer);
				break;
			case WORKSHOP:
				frame.remove(workshopDisplayer);
				workshop.removeShip();
				break;
			case BATTLE:
				frame.remove(battleSpaceDisplayer);
				break;
		}

		switch (desiredMode) {
			case MENU:
				frame.add(menuDisplayer);
				playerController.setBounds(0, 0, (int) (menuWidth * menuScale), (int) (menuHeight * menuScale));
				break;
			case WORKSHOP:
				workshop.addWorkingShip(playerShip);
				frame.add(workshopDisplayer);
				playerController.setBounds(0, 0, (int) (workshopWidth * workshopScale), (int) (workshopHeight * workshopScale));
				break;
			case BATTLE:
				battleSpace.placeShip(playerShip);
				frame.add(battleSpaceDisplayer);
				playerController.setBounds(0, 0, (int) (battleSpaceWidth * battleSpaceScale), (int) (battleSpaceHeight * battleSpaceScale));
				break;
		}
		playerController.setGamemode(desiredMode);

		frame.pack();
		frame.repaint();
		gamemode = desiredMode;

	}

	public Menu getMenu() {
		return menu;
	}

	public MenuDisplayer getMenuDisplayer() {
		return menuDisplayer;
	}

	public Workshop getWorkshop() {
		return workshop;
	}

	public WorkshopDisplayer getWorkshopDisplayer() {
		return workshopDisplayer;
	}

	public BattleSpace getBattleSpace() {
		return battleSpace;
	}

	public BattleSpaceDisplayer getBattleSpaceDisplayer() {
		return battleSpaceDisplayer;
	}

	public Gamemode getGamemode() {
		return gamemode;
	}
}
