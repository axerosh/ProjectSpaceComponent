package game;

import graphics.GameComponent;
import graphics.PSCFrame;
import shipcomponents.ShipComponent;
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
		int shieldOutput = 4;
		int reactorOutput = 3;
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
		GameComponent gc = new GameComponent(field);

		JFrame frame = new PSCFrame(field, gc);
		gc.repaint();

		playerShip.printShip();
		int cursorX = Math.round(5.3f * GameComponent.getSCALE());
		int cursorY = Math.round(6.5f * GameComponent.getSCALE());

		for(int tick = 0; tick< 20; tick++){
			field.update();
			try{
				Thread.sleep(1000);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}

			if(tick == 1){
			missleComponent.giveOrder(new Order(5, 6, 8, 9, playerShip));
			System.out.println("Order has been givven");
				//field.activateWithCursor(gc.getVirtualX(cursorX), gc.getVirtualY(cursorY));

			} else if(tick == 2){
			missleComponent.increasePower();
			System.out.println("Shoot should be fired");
				//field.deactivateWithCursor(gc.getVirtualX(cursorX), gc.getVirtualY(cursorY));
			}
			gc.repaint();

		}

		playerShip.printShip();


		}
}
