package game;

import graphics.DisplayableEnvironment;
import ship.Starship;
import ship.component.ShipComponent;
import ship.component.weapon.Projectile;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An area for two teams of starships to battle each other.
 *
 * @see Starship
 */
public class BattleSpace implements DisplayableEnvironment {

	//Static because the same background is to be used in any case.
	private final static String BACKGROUND_IMAGE_PATH = "se.liu.ida.l2.tddd78.psc/resources/space.png";

	//Static because the same background is to be used in any case.
	private final static ImageIcon BACKGROUND_IMAGE = new ImageIcon(BACKGROUND_IMAGE_PATH);

	static {
		if (BACKGROUND_IMAGE.getImageLoadStatus() != MediaTracker.COMPLETE) {
			Logger.getGlobal().log(Level.INFO, "The battle space background could not be loaded from '" + BACKGROUND_IMAGE_PATH + "'.");
		}
	}

	private final List<Team> teams;
	private final Set<Projectile> projectiles;
	private Random rng;
	private Team winningTeam;
	private boolean gameover;
	private float width;
	private float height;

	public BattleSpace(float width, float height) {
		rng = new Random();

		teams = new ArrayList<>();

		projectiles = new HashSet<>();
		winningTeam = null;
		gameover = false;

		this.width = width;
		this.height = height;
	}

	public void update(float deltaSeconds) {
		if (!gameover) {
			updateProjectiles(deltaSeconds);
			List<Team> undefeatedTeams = new ArrayList<>();
			for (Team team : teams) {
				if (team.isDefeated()) {
					continue;
				}
				for (Starship ship : team) {
					ship.update(deltaSeconds);
					addProjectiles(ship.getProjectilesToFire());
				}
				undefeatedTeams.add(team);
			}
			if (undefeatedTeams.size() == 1) {
				winningTeam = undefeatedTeams.get(0);
			}
			if (undefeatedTeams.size() <= 1) {
				//gameover = true;
			}
		}
	}

	private void updateProjectiles(float deltaSeconds) {
		Collection<Projectile> projectilesToRemove = new ArrayList<>();
		for (Projectile p : projectiles) {
			p.updateMovement(deltaSeconds);
			if (p.hasImpact()) {
				projectilesToRemove.add(p);
			}
		}
		synchronized (projectiles) {
			for (Projectile p : projectilesToRemove) {
				projectiles.remove(p);
			}
		}
	}

	public Starship getShipAt(final float vx, final float vy) {
		for (Team team : teams) {
			for (Starship ship : team) {
				if (ship.contains(vx, vy)) {
					return ship;
				}
			}
		}
		return null;
	}

	public ShipComponent getComponentAt(final float vx, final float vy) {
		Starship targetShip = getShipAt(vx, vy);
		if (targetShip != null) {
			return targetShip.getComponentAt(vx, vy);
		} else {
			return null;
		}
	}

	/**
	 * Adds the specified ship to the specified team.
	 *
	 * @param ship a StarShip to add
	 * @param team the Team to add the ship to.
	 */
	public void addShip(final Starship ship, final Team team) {
		team.add(ship);
		ship.setTeam(team);
	}

	/**
	 * Adds an addition team.
	 */
	public void addTeam(Team team) {
		teams.add(team);
	}

	public void addProjectiles(final Collection<Projectile> projectiles) {
		synchronized (this.projectiles) {
			this.projectiles.addAll(projectiles);
		}
	}

	public Team getRandomHostileTeam(Team ownTeam){
		List<Team> hostileTeams = new ArrayList<>();
		for (Team team : teams) {
			if (!team.equals(ownTeam)) {
				hostileTeams.add(team);
			}
		}
		return hostileTeams.get(rng.nextInt(hostileTeams.size()));
	}

	public Starship getRandomShipOfTeam(final Team team) {
		if (!teams.contains(team)) {
			String message = team.getTeamName() + ": No such team!";
			IllegalArgumentException exception =  new IllegalArgumentException(message);
			Logger.getGlobal().log(Level.CONFIG, message, exception);
			return null;
		}
		return teams.get(teams.indexOf(team)).getRandomMember();
	}

	/**
	 * Draws this battlefield with the specified scaling.
	 *
	 * @param g     the Graphics object with which to draw this battlefield
	 * @param scale the scale with which to scale virtual positions to get on-screen positions
	 */
	@Override
	public void display(final Graphics g, final float scale) {
		g.drawImage(BACKGROUND_IMAGE.getImage(), 0, 0, null);

		for (Team team : teams) {
			for (Starship ship : team) {
				ship.draw(g, scale);
			}
		}
		synchronized (projectiles) {
			for (Projectile projectile : projectiles) {
				projectile.draw(g, scale);
			}
		}

		if (winningTeam != null) {
			g.setColor(Color.RED);
			final int fontSize = 50;
			g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, fontSize));
			g.drawString(winningTeam.getTeamName() + " has won!", 0, fontSize);
		}
	}

	/**
	 * Relocates all ships to have them fit the battle space.
	 */
	public void pack(int shipWidth, int shipHeight) {
		final int outerMarginX = 1;
		final float outerMarginY = 0.5f;
		final int middleMarginX = 2;
		final int middleMarginY = 1;

		for (int teamIndex = 0; teamIndex < teams.size(); teamIndex++) {
			Team team = teams.get(teamIndex);
			for (Starship ship : team) {
				ship.setX(outerMarginX + teamIndex * (shipWidth + middleMarginX));
				ship.setY(outerMarginY + (team.indexOf(ship)) * (shipHeight + middleMarginY));
			}
		}
	}

	/**
	 * Restore all ship to full capacity and clears the battlesace of projetiles.
	 */
	public void reset(){
		winningTeam = null;
		projectiles.clear();
		for(Team team : teams){
			team.setDefeated(false);
		}
		for (int teamIndex = 0; teamIndex < teams.size(); teamIndex++) {
			Team team = teams.get(teamIndex);
			for (Starship ship : team) {
				ship.restore();
			}
		}
	}

	@Override public float getWidth() {
		return width;
	}

	@Override public float getHeight() {
		return height;
	}
}
