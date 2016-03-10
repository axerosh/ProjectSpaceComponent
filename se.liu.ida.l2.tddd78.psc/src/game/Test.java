package game;

import graphics.GameDisplayer;
import graphics.PSCFrame;
import ship_components.utility_components.EngineComponent;
import ship_components.utility_components.ReactorComponent;
import ship_components.utility_components.ShieldComponent;
import ship_components.weapon_components.AbstractWeaponComponent;
import ship_components.weapon_components.MissileComponent;
import weaponry.FiringOrder;

import javax.swing.*;

/**
 * Class for testing the game.
 */
public final class Test
{

	private Test() {}

	public static void main(String[] args) {
		Battlefield arena = new Battlefield();
		Starship playerShip = new Starship(1, 1, 5, 5);

		float componentIntegrity = 2;
		int shieldOutput = 4;
		int reactorOutput = 3;
		int engineOutput = 10;
		int missileRechargeTime = 5;

		AbstractWeaponComponent missileComponent = new MissileComponent(componentIntegrity, missileRechargeTime);

		playerShip.setComponent(new EngineComponent(componentIntegrity, engineOutput), 1, 0);
		playerShip.setComponent(new EngineComponent(componentIntegrity, engineOutput), 2, 0);
		playerShip.setComponent(new EngineComponent(componentIntegrity, engineOutput), 3, 0);

		playerShip.setComponent(missileComponent, 0, 1);
		playerShip.setComponent(new ShieldComponent(componentIntegrity, shieldOutput), 1, 1);
		playerShip.setComponent(new ShieldComponent(componentIntegrity, shieldOutput), 2, 1);
		playerShip.setComponent(new ShieldComponent(componentIntegrity, shieldOutput), 3, 1);
		playerShip.setComponent(new ShieldComponent(componentIntegrity, shieldOutput), 4, 1);

		playerShip.setComponent(new EngineComponent(componentIntegrity, engineOutput), 0, 2);
		playerShip.setComponent(new EngineComponent(componentIntegrity, engineOutput), 2, 2);
		playerShip.setComponent(new EngineComponent(componentIntegrity, engineOutput), 4, 2);

		playerShip.setComponent(new EngineComponent(componentIntegrity, engineOutput), 0, 3);
		playerShip.setComponent(new EngineComponent(componentIntegrity, engineOutput), 1, 3);
		playerShip.setComponent(new EngineComponent(componentIntegrity, engineOutput), 2, 3);
		playerShip.setComponent(new EngineComponent(componentIntegrity, engineOutput), 3, 3);
		playerShip.setComponent(new EngineComponent(componentIntegrity, engineOutput), 4, 3);

		playerShip.setComponent(new ReactorComponent(componentIntegrity, reactorOutput), 1, 4);
		playerShip.setComponent(new ReactorComponent(componentIntegrity, reactorOutput), 3, 4);

		arena.addFriendlyShip(playerShip);
		GameDisplayer gameDisplayer = new GameDisplayer(arena);
		playerShip.addVisibleEntityListener(gameDisplayer);

		JFrame frame = new PSCFrame(arena, gameDisplayer);

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

			if (tick == 1) {
				missileComponent.giveFiringOrder(new FiringOrder(1, 2, 3, 3, playerShip));
				System.out.println("Firing order has been given");

			} else if (tick == 2) {
				missileComponent.increasePower();
				System.out.println("Shot should be fired");
			} else if (tick == runTime) {
				running = false;
			}
			gameDisplayer.repaint();

			tick++;
		}

		playerShip.printShip();


	}
}
