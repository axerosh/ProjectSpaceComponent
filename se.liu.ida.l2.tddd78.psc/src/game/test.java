package game;

import graphics.GameComponent;
import shipcomponents.utilitycomponents.EngineComponent;

import javax.swing.*;

public class test {
    public static void main(String[] args) {
	BattleField field = new BattleField();
	StarShip playerShip = new StarShip(5f, 5f, 5, 5);
	int engineHP = 2;
	int engineDodgeRate = 70;
	EngineComponent engine = new EngineComponent(engineHP, engineDodgeRate);
	playerShip.setComponent(engine, 1, 0);
	playerShip.setComponent(engine, 2, 0);
	playerShip.setComponent(engine, 3, 0);

	playerShip.setComponent(engine, 0, 1);
	playerShip.setComponent(engine, 1, 1);
	playerShip.setComponent(engine, 2, 1);
	playerShip.setComponent(engine, 3, 1);
	playerShip.setComponent(engine, 4, 1);

	playerShip.setComponent(engine, 0, 2);
	playerShip.setComponent(engine, 2, 2);
	playerShip.setComponent(engine, 4, 2);

	playerShip.setComponent(engine, 0, 3);
	playerShip.setComponent(engine, 1, 3);
	playerShip.setComponent(engine, 2, 3);
	playerShip.setComponent(engine, 3, 3);
	playerShip.setComponent(engine, 4, 3);

	playerShip.setComponent(engine, 1, 4);
	playerShip.setComponent(engine, 3, 4);

	field.addFriendlyShip(playerShip);
	GameComponent gc = new GameComponent(field);

	JFrame frame = new JFrame("testing is FUUUUUUUUUUUUUUUUUUUUUUUN!");
	frame.add(gc);
	frame.pack();
	frame.setVisible(true);
	frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	gc.repaint();

	System.out.println((int)(-0.5));
    }
}
