package game;

import graphics.GameComponent;
import projectiles.MissileProjectile;
import shipcomponents.ShipComponent;
import shipcomponents.utilitycomponents.EngineComponent;
import shipcomponents.utilitycomponents.ReactorComponent;
import shipcomponents.utilitycomponents.ShieldComponent;
import shipcomponents.weaponscomponents.MissileComponent;
import temp.Order;

import javax.swing.*;

public final class Test {

	private Test() {}

    public static void main(String[] args) {
		BattleField field = new BattleField();
		StarShip playerShip = new StarShip(5f, 5f, 5, 5);
		int componentHP = 2;
		int componentOutput = 70;
		int reactorOutput = 3;
		int engineOutput = 10;
		ShipComponent engine = new EngineComponent(componentHP, engineOutput);
		ShipComponent shield = new ShieldComponent(componentHP, componentOutput);
		ShipComponent coolShield = new ShieldComponent(componentHP, componentOutput);
		ShipComponent reactor = new ReactorComponent(componentHP, reactorOutput);
		MissileComponent missleComponent = new MissileComponent(5, 5);
		playerShip.setComponent(engine, 1, 0);
		playerShip.setComponent(engine, 2, 0);
		playerShip.setComponent(engine, 3, 0);

	playerShip.setComponent(missleComponent, 0, 1);
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

	playerShip.printShip();

	for(int tick = 0; tick< 20; tick++){
	    field.update();
	    try{
		Thread.sleep(1000);
	    } catch(InterruptedException e) {
		e.printStackTrace();
	    }

	    if(tick == 5){
		missleComponent.giveOrder(new Order(15, 15, 8, 9, playerShip));
		System.out.println("Order has been givin");
	    }
	    if(tick == 10){
		missleComponent.increasePower();
		System.out.println("Shoot should be fired");
	    }
	    gc.repaint();

	}

	playerShip.printShip();


    }
}
