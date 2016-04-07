package game;

import control.BasicAI;
import control.MouseAndKeyboard;
import graphics.GameDisplayer;
import graphics.PSCFrame;
import ship.ShipFactory;
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
public final class Test
{

	private Test() {}

	public static void main(String[] args) {
		Battlefield arena = new Battlefield();

		final int team1 = 0;
		final int team2 = 1;

		String playerShipRepresentaiton = "width=5, height=5, integrity=10.0, maxIntegrity=10.0;\n" +
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
										 ".S.RE;";

		Starship playerShip = ShipFactory.getStarship(1, 1, playerShipRepresentaiton);
		//Starship playerShip = new Starship(1, 1, 5, 5, shipIntegrity);
		//initShip(playerShip);
		arena.addShip(playerShip, team1);

		Starship enemyShip = ShipFactory.getStarship(7, 1, enemyShipRepresentaiton);
		//Starship enemyShip = new Starship(7, 1, 5, 5, shipIntegrity);
		//initShip(enemyShip);
		arena.addShip(enemyShip, team2);
		BasicAI ai = new BasicAI(arena, enemyShip);

		GameDisplayer gameDisplayer = new GameDisplayer(arena);
		playerShip.addVisibleEntityListener(gameDisplayer);
		JComponent playerController = new MouseAndKeyboard(arena, playerShip, gameDisplayer);

		JFrame frame = new PSCFrame();
		frame.add(gameDisplayer);
		frame.pack();
		frame.add(playerController);
		gameDisplayer.repaint();

		playerShip.printShip();
		System.out.println(playerShip.getTextRepresentation());

		Timer timer = new Timer(8, new AbstractAction() {
			private long lastTime = System.nanoTime();

			@Override public void actionPerformed(final ActionEvent e) {
				final long nanosPerSecond = 1000000000;
				float passedSeconds = (float) (System.nanoTime() - lastTime) / nanosPerSecond;
				lastTime = System.nanoTime();
				ai.update();
				arena.update(passedSeconds);
				gameDisplayer.repaint();
			}
		});
		timer.setCoalesce(true);
		timer.start();

		playerShip.printShip();
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
