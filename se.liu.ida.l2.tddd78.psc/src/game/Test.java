package game;

import component.utility.EngineComponent;
import component.utility.ReactorComponent;
import component.utility.ShieldComponent;
import component.weapon.MissileComponent;
import control.MouseAndKeyboard;
import graphics.GameDisplayer;
import graphics.PSCFrame;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * Class for testing the game.
 */
public final class Test {

	private Test() {}

	public static void main(String[] args) {
		Battlefield arena = new Battlefield();

		final int team1 = 0;
		final int team2 = 1;

		Starship playerShip = new Starship(1, 1, 5, 5);
		initShip(playerShip);
		arena.addShip(playerShip, team1);

		Starship enemyShip = new Starship(7, 1, 5, 5);
		initShip(enemyShip);
		arena.addShip(enemyShip, team2);

		GameDisplayer gameDisplayer = new GameDisplayer(arena);
		playerShip.addVisibleEntityListener(gameDisplayer);
		JComponent playerController = new MouseAndKeyboard(arena, playerShip, gameDisplayer);

		JFrame frame = new PSCFrame();
		frame.add(gameDisplayer);
		frame.pack();
		frame.add(playerController);
		gameDisplayer.repaint();

		playerShip.printShip();

		boolean running = true;
		int tick = 0;
		final int runTime = 60;

		while (running) {
			arena.update();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (tick == runTime) {
				running = false;
			}
			gameDisplayer.repaint();

			tick++;
		}

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
