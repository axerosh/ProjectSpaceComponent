package game;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ProjectSpaceComponent implements Runnable {


	private int gameWidth = 32;
	private int gameHeight = 18;
	private int workshopWidth = gameWidth / 2;
	private int workshopHeight = gameHeight / 2;
	private int menuWidth = 10;
	private int menuHeight = 20;
	private float gamescale = 40.0f;
	private float workshopscale = 2 * gamescale;
	private float menuscale = gamescale;
	private float maxFramerate = 60;
	// :D

	public ProjectSpaceComponent() {

	}

	@Override public void run() {

		final long millisPerSecond = 1000;
		int wantedBetweenUpdates = Math.round(millisPerSecond / maxFramerate);
		int timeBetweenUpdates = (int) Math.ceil(wantedBetweenUpdates);
		Timer timer = new Timer(timeBetweenUpdates, new AbstractAction() {
			private long lastTime = System.nanoTime();

			@Override public void actionPerformed(final ActionEvent e) {
				final long nanosPerSecond = 1000000000;
				float passedSeconds = (float) (System.nanoTime() - lastTime) / nanosPerSecond;
				lastTime = System.nanoTime();

				if (gamemode == Gamemode.WORKSHOP) {
					workshop.update();
					workshopDisplayer.repaint();
				} else if (gamemode == Gamemode.BATTLE) {
					//ai.update();
					arena.update(passedSeconds);
					gameDisplayer.repaint();
				}


			}
		});
		timer.setCoalesce(true);
		timer.start();
	}
}
