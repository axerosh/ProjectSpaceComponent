import game.BattleField;
import game.StarShip;
import graphics.GameComponent;
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
		playerShip.addComponent(engine, 1, 0);
		playerShip.addComponent(engine, 2, 0);
		playerShip.addComponent(engine, 3, 0);

		playerShip.addComponent(shield, 0, 1);
		playerShip.addComponent(shield, 1, 1);
		playerShip.addComponent(shield, 2, 1);
		playerShip.addComponent(shield, 3, 1);
		playerShip.addComponent(coolShield, 4, 1);

		playerShip.addComponent(engine, 0, 2);
		playerShip.addComponent(engine, 2, 2);
		playerShip.addComponent(engine, 4, 2);

		playerShip.addComponent(engine, 0, 3);
		playerShip.addComponent(engine, 1, 3);
		playerShip.addComponent(engine, 2, 3);
		playerShip.addComponent(engine, 3, 3);
		playerShip.addComponent(engine, 4, 3);

		playerShip.addComponent(reactor, 1, 4);
		playerShip.addComponent(reactor, 3, 4);

		field.addFriendlyShip(playerShip);
		GameComponent gc = new GameComponent(field);

		JFrame frame = new JFrame("testing is FUUUUUUUUUUUUUUUUUUUUUUUN!");
		frame.add(gc);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		gc.repaint();

		playerShip.printShip();
		playerShip.increasePower(4 + 5,1 + 5);
		playerShip.increaseShielding(3 + 5,4 + 5);
		playerShip.printShip();
		playerShip.update();

		playerShip.increasePower(4 + 5,1 + 5);
		playerShip.update();
		playerShip.printShip();

		playerShip.increaseShielding(3 + 5,4 + 5);
		playerShip.update();
		playerShip.printShip();

		playerShip.decreasePower(4 + 5, 1 + 5);
		playerShip.update();
		playerShip.printShip();
    }
}
