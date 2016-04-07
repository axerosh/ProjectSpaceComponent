package game;

import component.utility.EngineComponent;
import component.utility.ReactorComponent;
import component.utility.ShieldComponent;
import component.weapon.MissileComponent;
import control.BasicAI;
import control.MouseAndKeyboard;
import graphics.displayers.GameDisplayer;
import graphics.displayers.MenuDisplayer;
import graphics.PSCFrame;
import graphics.displayers.WorkshopDisplayer;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Class for testing the game.
 */
public final class Test
{

	private static JFrame frame;

	private Test() {}
	public static Gamemode gameMode = Gamemode.MENU;
	private static float SCALE = 40.0f;

	public static void main(String[] args) {
		Battlefield arena = new Battlefield();

		final int team1 = 0;
		final int team2 = 1;
		final float shipIntegrity = 10;

		Menu menu = new Menu();
		MenuDisplayer menuDisplayer = new MenuDisplayer(menu);

		Workshop workshop = new Workshop(32,18, SCALE);
		WorkshopDisplayer workshopDisplayer = new WorkshopDisplayer(workshop);

		Starship playerShip = new Starship(5, 5, shipIntegrity);
		initShip(playerShip);
		workshop.addShip(playerShip);
		arena.addShip(playerShip, team1);

		Starship enemyShip = new Starship(5, 5, shipIntegrity);
		initShip(enemyShip);
		arena.addShip(enemyShip, team2);
		BasicAI AI = new BasicAI(arena, enemyShip);

		GameDisplayer gameDisplayer = new GameDisplayer(arena);
		playerShip.addVisibleEntityListener(gameDisplayer);
		JComponent playerController = new MouseAndKeyboard(arena, playerShip, gameDisplayer, workshopDisplayer, menuDisplayer);

		frame = new PSCFrame();
		frame.add(menuDisplayer);
		frame.pack();
		frame.add(playerController);
		gameDisplayer.repaint();

		//playerShip.printShip();

		Timer timer = new Timer(8, new AbstractAction() {
			private long lastTime = System.nanoTime();

			@Override public void actionPerformed(final ActionEvent e) {
				final long nanosPerSecond = 1000000000;
				float passedSeconds = (float) (System.nanoTime() - lastTime) / nanosPerSecond;
				lastTime = System.nanoTime();

				if(gameMode == Gamemode.WORKSHOP){
					workshop.update();
					workshopDisplayer.repaint();
				}else if(gameMode == Gamemode.BATTLE){
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

	public static enum Gamemode{
		MENU, WORKSHOP, BATTLE
	}

	public static void changeGamemode(Gamemode mode, GameDisplayer gameDisplayer, WorkshopDisplayer workshopDisplayer, MenuDisplayer menuDisplayer){
		switch (gameMode){
			case MENU:
				System.out.println("cheking in! ;)");
				frame.remove(menuDisplayer);
				break;
			case WORKSHOP:
				frame.remove(workshopDisplayer);
				break;
			case BATTLE:
				frame.remove(gameDisplayer);
				break;
		}

		switch (mode){
			case MENU:
				System.out.println("Show men that menu ;)");
				frame.add(menuDisplayer);
				break;
			case WORKSHOP:
				System.out.println("Work it babe ;)");
				frame.add(workshopDisplayer);
				break;
			case BATTLE:
				System.out.println("Do you wanna play a game? ;)");
				frame.add(gameDisplayer);
				break;
		}
		frame.pack();
		frame.repaint();
		gameMode = mode;
		System.out.println("Looking good babe ;)");
	}

	public static Gamemode getGamemode(){
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
