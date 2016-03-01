package game;

import graphics.GameComponent;
import projectiles.MissileProjectile;
import shipcomponents.utilitycomponents.EngineComponent;
import shipcomponents.utilitycomponents.ReactorComponent;
import shipcomponents.utilitycomponents.ShieldComponent;

import javax.swing.*;

public final class test {

    public static void main(String[] args) {
		BattleField field = new BattleField();
		StarShip playerShip = new StarShip(5f, 5f, 5, 5);
		int engineHP = 2;
		int engineDodgeRate = 70;
		EngineComponent engine = new EngineComponent(engineHP, engineDodgeRate);
		ShieldComponent shield = new ShieldComponent(engineHP, engineDodgeRate);
		ShieldComponent coolShield = new ShieldComponent(engineHP, engineDodgeRate);
		ReactorComponent reactor = new ReactorComponent(engineHP, engineDodgeRate);
		playerShip.setComponent(engine, 1, 0);
		playerShip.setComponent(engine, 2, 0);
		playerShip.setComponent(engine, 3, 0);

		playerShip.setComponent(shield, 0, 1);
		playerShip.setComponent(shield, 1, 1);
		playerShip.setComponent(shield, 2, 1);
		playerShip.setComponent(shield, 3, 1);
		playerShip.setComponent(coolShield, 4, 1);

		playerShip.setComponent(engine, 0, 2);
		playerShip.setComponent(engine, 2, 2);
		playerShip.setComponent(engine, 4, 2);

		playerShip.setComponent(engine, 0, 3);
		playerShip.setComponent(engine, 1, 3);
		playerShip.setComponent(engine, 2, 3);
		playerShip.setComponent(engine, 3, 3);
		playerShip.setComponent(engine, 4, 3);

		playerShip.setComponent(reactor, 1, 4);
		playerShip.setComponent(reactor, 3, 4);

		field.addFriendlyShip(playerShip);
		GameComponent gc = new GameComponent(field);

		JFrame frame = new JFrame("testing is FUUUUUUUUUUUUUUUUUUUUUUUN!");
		frame.add(gc);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		gc.repaint();


	//	playerShip.printShip();
	//	playerShip.increasePower(4 + 5,1 + 5);
	//	playerShip.increaseShielding(3 + 5,4 + 5);
	//	playerShip.printShip();
		playerShip.update();

		playerShip.increasePower(4 + 5,1 + 5);
		playerShip.increasePower(0 + 5,1 + 5);
		playerShip.update();
	//	playerShip.printShip();

		playerShip.increaseShielding(4 + 5,1 + 5);
		playerShip.increaseShielding(4 + 5,1 + 5);
		playerShip.increaseShielding(4 + 5,1 + 5);
		playerShip.increaseShielding(4 + 5,1 + 5);
		playerShip.update();
	//	playerShip.printShip();

		/*
		playerShip.decreasePower(4 + 5, 1 + 5);
		playerShip.update();
		playerShip.printShip();
		*/

		playerShip.printShip();

		try{
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}

		field.addProjectile(new MissileProjectile(15, 15,  4 + 5, 1 + 5, 4, playerShip, 2, 2));
		gc.repaint();

		try{
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}

		field.update();
		gc.repaint();

		try{
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}

		field.update();
		gc.repaint();


		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}

		field.update();
		gc.repaint();
		playerShip.printShip();
    }
}
