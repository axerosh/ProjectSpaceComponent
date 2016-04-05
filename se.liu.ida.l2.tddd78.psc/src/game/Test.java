package game;

import component.utility.EngineComponent;
import component.utility.ReactorComponent;
import component.utility.ShieldComponent;
import component.weapon.MissileComponent;
import control.BasicAI;
import control.MouseAndKeyboard;
import graphics.GameDisplayer;
import graphics.PSCFrame;
import graphics.WorkshopDisplayer;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Class for testing the game.
 */
public final class Test
{

	private static JFrame frame;

	private Test() {}
	private static int gameMode = -1;

	public static void main(String[] args) {
		Battlefield arena = new Battlefield();



		final int team1 = 0;
		final int team2 = 1;
		final float shipIntegrity = 10;

		Workshop workshop = new Workshop();

		WorkshopDisplayer workshopDisplayer = new WorkshopDisplayer(workshop);

		Starship playerShip = new Starship(1, 1, 5, 5, shipIntegrity);
		initShip(playerShip);
		arena.addShip(playerShip, team1);

		Starship enemyShip = new Starship(7, 1, 5, 5, shipIntegrity);
		initShip(enemyShip);
		arena.addShip(enemyShip, team2);
		BasicAI AI = new BasicAI(arena, enemyShip);

		GameDisplayer gameDisplayer = new GameDisplayer(arena);
		playerShip.addVisibleEntityListener(gameDisplayer);
		JComponent playerController = new MouseAndKeyboard(arena, playerShip, gameDisplayer, workshopDisplayer);

		frame = new PSCFrame();
		//frame.add(gameDisplayer);
		//frame.pack();
		frame.add(playerController);
		//gameDisplayer.repaint();

		//playerShip.printShip();

		Timer timer = new Timer(8, new AbstractAction() {
			private long lastTime = System.nanoTime();

			@Override public void actionPerformed(final ActionEvent e) {
				final long nanosPerSecond = 1000000000;
				float passedSeconds = (float) (System.nanoTime() - lastTime) / nanosPerSecond;
				lastTime = System.nanoTime();

				if(gameMode == 0){
					workshop.update();
					workshopDisplayer.repaint();
				}else if(gameMode == 1){
					AI.update();
					arena.update(passedSeconds);
					gameDisplayer.repaint();
				}


			}
		});
		timer.setCoalesce(true);
		timer.start();

		//playerShip.printShip();
	}

	public static void changeGamemode(int i, GameDisplayer gameDisplayer, WorkshopDisplayer workshopDisplayer){
		frame.removeAll();
		switch (gameMode){
			case(0):
				frame.add(workshopDisplayer);
				break;
			case(1):
				frame.add(gameDisplayer);
				break;
		}
		frame.pack();
	}

	public static int getGamemode(){
		return gameMode;
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
