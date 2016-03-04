package game;

import graphics.GameDisplayer;
import graphics.PSCFrame;
import shipcomponents.utilitycomponents.EngineComponent;
import shipcomponents.utilitycomponents.ReactorComponent;
import shipcomponents.utilitycomponents.ShieldComponent;
import shipcomponents.weaponscomponents.AbstractWeaponComponent;
import shipcomponents.weaponscomponents.MissileComponent;
import temp.Order;

import javax.swing.*;

public final class Test {

	private Test() {}

    public static void main(String[] args) {
		BattleField field = new BattleField();
		StarShip playerShip = new StarShip(1f, 1f, 5, 5);

		int componentHP = 2;
		int shieldOutput = 4 * 10;
		int reactorOutput = 3 * 10;
		int engineOutput = 10;
		int missileRechargeTime = 5;

		AbstractWeaponComponent missileComponent = new MissileComponent(componentHP, missileRechargeTime);

		playerShip.setComponent(new EngineComponent(componentHP, engineOutput), 1, 0);
		playerShip.setComponent(new EngineComponent(componentHP, engineOutput), 2, 0);
		playerShip.setComponent(new EngineComponent(componentHP, engineOutput), 3, 0);

		playerShip.setComponent(missileComponent, 0, 1);
		playerShip.setComponent(new ShieldComponent(componentHP, shieldOutput), 1, 1);
		playerShip.setComponent(new ShieldComponent(componentHP, shieldOutput), 2, 1);
		playerShip.setComponent(new ShieldComponent(componentHP, shieldOutput), 3, 1);
		playerShip.setComponent(new ShieldComponent(componentHP, shieldOutput), 4, 1);

		playerShip.setComponent(new EngineComponent(componentHP, engineOutput), 0, 2);
		playerShip.setComponent(new EngineComponent(componentHP, engineOutput), 2, 2);
		playerShip.setComponent(new EngineComponent(componentHP, engineOutput), 4, 2);

		playerShip.setComponent(new EngineComponent(componentHP, engineOutput), 0, 3);
		playerShip.setComponent(new EngineComponent(componentHP, engineOutput), 1, 3);
		playerShip.setComponent(new EngineComponent(componentHP, engineOutput), 2, 3);
		playerShip.setComponent(new EngineComponent(componentHP, engineOutput), 3, 3);
		playerShip.setComponent(new EngineComponent(componentHP, engineOutput), 4, 3);

		playerShip.setComponent(new ReactorComponent(componentHP, reactorOutput), 1, 4);
		playerShip.setComponent(new ReactorComponent(componentHP, reactorOutput), 3, 4);

		field.addFriendlyShip(playerShip);
		GameDisplayer gc = new GameDisplayer(field);
		playerShip.addVisibleEntityListener(gc);

		JFrame frame = new PSCFrame(field, gc);
		gc.repaint();

		playerShip.printShip();
		int cursorX = Math.round(5.3f * GameDisplayer.getSCALE());
		int cursorY = Math.round(6.5f * GameDisplayer.getSCALE());

		boolean running = true;
		int tick = 0;
		while (running) {
			field.update();
			try{
				Thread.sleep(1000);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}

			if(tick == 1){
			missileComponent.giveOrder(new Order(1, 2, 3, 3, playerShip));
			System.out.println("Order has been given");
				//field.activateWithCursor(gc.getVirtualX(cursorX), gc.getVirtualY(cursorY));

			} else if(tick == 2){
			missileComponent.increasePower();
			System.out.println("Shoot should be fired");
				//field.deactivateWithCursor(gc.getVirtualX(cursorX), gc.getVirtualY(cursorY));
			} else if(tick == 12) {
				running = false;
			}
			gc.repaint();

			tick++;
		}

		playerShip.printShip();


		}
}
