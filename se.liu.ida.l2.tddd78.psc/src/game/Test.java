package game;

import control.BasicAI;
import control.MouseAndKeyboard;
import graphics.PSCFrame;
import graphics.displayers.BattleSpaceDisplayer;
import graphics.displayers.MenuDisplayer;
import graphics.displayers.WorkshopDisplayer;
import ship.ShipIO;
import ship.Starship;
import ship.component.utility.EngineComponent;
import ship.component.utility.ReactorComponent;
import ship.component.utility.ShieldComponent;
import ship.component.weapon.MissileComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Class for testing the game.
 */
public final class Test {

	private static final int GAME_WIDTH = 32;
	private static final int GAME_HEIGHT = 18;
	private static final int WORKSHOP_WIDTH = GAME_WIDTH / 2;
	private static final int WORKSHOP_HEIGHT = GAME_HEIGHT / 2;
	private static final int MENU_WIDTH = 10;
	private static final int MENU_HEIGHT = 20;
	private static final float GAMESCALE = 40.0f;
	private static final float WORKSHOPSCALE = 2 * GAMESCALE;
	private static final float MENUSCALE = GAMESCALE;
	private static final float MAX_FRAMERATE = 60;
	/**
	 * The current Gamemode
	 */
	public static ProjectSpaceComponent.Gamemode gamemode = ProjectSpaceComponent.Gamemode.MENU;
	private static JFrame frame;// = new PSCFrame();
	private Test() {}

	public static void main(String[] args) {

		final Team team1 = new Team("Team 1");
		final Team team2 = new Team("Team 2");
		BattleSpace arena = new BattleSpace();
		//arena.addTeam(team1.getTeamName());
		//arena.addTeam(team2.getTeamName());


		/*String playerShipRepresentaiton = "width=5, height=5, integrity=10.0, maxIntegrity=10.0;\n" +
										  ".RSS.,\n" +
										  "EE.RS,\n" +
										  ".SSMR,\n" +
										  "EE.RS,\n" +
										  ".RSS.;";

		String enemyShipRepresentaiton = "width=5, height=5, integrity=10.0, maxIntegrity=10.0;\n" +
										 ".S.RE,\n" +
										 "SRSSE,\n" +
										 "..MR.,\n" +
										 "SRSSE,\n" +
										 ".S.RE;";*/

		Menu menu = new Menu();
		MenuDisplayer menuDisplayer = new MenuDisplayer(menu, MENUSCALE, MENU_WIDTH, MENU_HEIGHT);


		Workshop workshop = new Workshop(WORKSHOP_WIDTH, WORKSHOP_HEIGHT, WORKSHOPSCALE);
		WorkshopDisplayer workshopDisplayer = new WorkshopDisplayer(workshop, WORKSHOPSCALE, WORKSHOP_WIDTH, WORKSHOP_HEIGHT);


		Starship playerShip = ShipIO.load("the_manta");
		//Starship playerShip = new Starship(14, 8, shipIntegrity);
		//Starship playerShip = ShipFactory.getStarship(1, 1, playerShipRepresentaiton);
		//Starship playerShip = new Starship(1, 1, 5, 5, shipIntegrity); initShip(playerShip);
		arena.addShip(playerShip, team1);
		arena.placeShip(playerShip);


		Starship enemyShip = ShipIO.load("the_governator");
		//Starship enemyShip = new Starship(14, 8, shipIntegrity);
		//Starship enemyShip = ShipFactory.getStarship(7, 1, enemyShipRepresentaiton);
		//Starship enemyShip = new Starship(7, 1, 5, 5, shipIntegrity); initShip(enemyShip);
		arena.addShip(enemyShip, team2);
		arena.placeShip(enemyShip);
		BasicAI ai = new BasicAI(arena, enemyShip);

		//ShipIO.save(playerShip, "the_manta");
		//ShipIO.save(enemyShip, "the_governator");

		BattleSpaceDisplayer battleSpaceDisplayer = new BattleSpaceDisplayer(arena, GAMESCALE, GAME_WIDTH, GAME_HEIGHT);
		if (playerShip != null) {
			playerShip.addVisibleEntityListener(battleSpaceDisplayer);
		}
		//MouseAndKeyboard playerController = new MouseAndKeyboard(arena, battleSpaceDisplayer, workshopDisplayer, menuDisplayer, workshop, gamemode);
		//playerController.setControlledShip(playerShip);

		frame = new PSCFrame();
		frame.add(menuDisplayer);
		frame.pack();
		//frame.add(playerController);


		//playerShip.printShip();
		final long millisPerSecond = 1000;
		int wantedBetweenUpdates = Math.round(millisPerSecond / MAX_FRAMERATE);
		int timeBetweenUpdates = (int) Math.ceil(wantedBetweenUpdates);
		Timer timer = new Timer(timeBetweenUpdates, new AbstractAction() {
			private long lastTime = System.nanoTime();

			@Override public void actionPerformed(final ActionEvent e) {
				final long nanosPerSecond = 1000000000;
				float passedSeconds = (float) (System.nanoTime() - lastTime) / nanosPerSecond;
				lastTime = System.nanoTime();

				if (gamemode == ProjectSpaceComponent.Gamemode.WORKSHOP){
					workshop.update();
					workshopDisplayer.repaint();
				} else if (gamemode == ProjectSpaceComponent.Gamemode.BATTLE) {
					//ai.update();
					arena.update(passedSeconds);
					battleSpaceDisplayer.repaint();
				}


			}
		});
		timer.setCoalesce(true);
		timer.start();

		//playerShip.printShip();
	}

	public static void changeGamemode(ProjectSpaceComponent.Gamemode mode, BattleSpaceDisplayer battleSpaceDisplayer, WorkshopDisplayer workshopDisplayer,
									  MenuDisplayer menuDisplayer, BattleSpace arena, Workshop shop, Starship playerShip,
									  JComponent playerController)
	{
		switch (gamemode) {
			case MENU:
				frame.remove(menuDisplayer);
				break;
			case WORKSHOP:
				frame.remove(workshopDisplayer);
				shop.removeShip();
				break;
			case BATTLE:
				frame.remove(battleSpaceDisplayer);
				break;
		}

		switch (mode) {
			case MENU:
				frame.add(menuDisplayer);
				playerController.setBounds(0, 0, 400, 800);
				break;
			case WORKSHOP:
				shop.addWorkingShip(playerShip);
				frame.add(workshopDisplayer);
				playerController.setBounds(0, 0, (int) (32 * GAMESCALE), (int) (18 * GAMESCALE));
				break;
			case BATTLE:
				arena.placeShip(playerShip);
				frame.add(battleSpaceDisplayer);
				playerController.setBounds(0, 0, (int) (32 * GAMESCALE), (int) (18 * GAMESCALE));
				break;
		}

		frame.pack();
		frame.repaint();
		gamemode = mode;

	}

	public static ProjectSpaceComponent.Gamemode getGamemode() {
		return gamemode;
	}

	private static void initShip(Starship starship) {
		float componentIntegrity = 2;
		int shieldOutput = 4;
		int reactorOutput = 3;
		int engineOutput = 10;
		int missileRechargeTime = 5;

		starship.setComponent(new EngineComponent(componentIntegrity, engineOutput), 1, 0);
		starship.setComponent(new EngineComponent(componentIntegrity, engineOutput), 2, 0);
		starship.setComponent(new EngineComponent(componentIntegrity, engineOutput), 3, 0);

		starship.setComponent(new MissileComponent(componentIntegrity, missileRechargeTime), 0, 1);
		starship.setComponent(new ShieldComponent(componentIntegrity, shieldOutput), 1, 1);
		starship.setComponent(new ShieldComponent(componentIntegrity, shieldOutput), 2, 1);
		starship.setComponent(new ShieldComponent(componentIntegrity, shieldOutput), 3, 1);
		starship.setComponent(new ShieldComponent(componentIntegrity, shieldOutput), 4, 1);

		starship.setComponent(new EngineComponent(componentIntegrity, engineOutput), 0, 2);
		starship.setComponent(new EngineComponent(componentIntegrity, engineOutput), 2, 2);
		starship.setComponent(new EngineComponent(componentIntegrity, engineOutput), 4, 2);

		starship.setComponent(new EngineComponent(componentIntegrity, engineOutput), 0, 3);
		starship.setComponent(new EngineComponent(componentIntegrity, engineOutput), 1, 3);
		starship.setComponent(new EngineComponent(componentIntegrity, engineOutput), 2, 3);
		starship.setComponent(new EngineComponent(componentIntegrity, engineOutput), 3, 3);
		starship.setComponent(new EngineComponent(componentIntegrity, engineOutput), 4, 3);

		starship.setComponent(new ReactorComponent(componentIntegrity, reactorOutput), 1, 4);
		starship.setComponent(new ReactorComponent(componentIntegrity, reactorOutput), 3, 4);
	}
}
