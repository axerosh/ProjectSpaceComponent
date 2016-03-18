package game;

import component.ShipComponent;
import weaponry.projectile.Projectile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * An area for two teams of starships to battle each other.
 *
 * @see Starship
 */
public class Battlefield extends GeneralVisibleEntity
{

	private final List<Team> teams;
	private final Set<Projectile> projectiles;
	private Random rng;
	private Team winningTeam;
	private boolean gameover;

	public Battlefield() {
		rng = new Random();

		teams = new ArrayList<>();
		teams.add(new Team());
		teams.add(new Team());

		projectiles = new HashSet<>();
		winningTeam = null;
		gameover = false;
	}

	public void update() {
		if (!gameover) {
			updateProjectiles();
			List<Team> undefeatedTeams = new ArrayList<>();
			for (Team team : teams) {
				if (team.isDefeated()) {
					continue;
				}
				for (Starship ship : team) {
					ship.update();
					addProjectiles(ship.getProjectilesToFire());
				}
				undefeatedTeams.add(team);
			}
			if (undefeatedTeams.size() == 1) {
				winningTeam = undefeatedTeams.get(0);
			}
			if (undefeatedTeams.size() <= 1) {
				gameover = true;
			}
		}
	}

	private void updateProjectiles() {
		Collection<Projectile> projectilesToRemove = new ArrayList<>();
		for (Projectile p : projectiles) {
			p.update();
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
	 * @param teamNumber team number from 0 to number of teams - 1
	 *
	 * @return false if failed
	 * @see #getNumberOfTeams
	 */
	public boolean addShip(final Starship ship, final int teamNumber) {
		if (teamNumber >= teams.size() || teamNumber < 0) {
			return false;
		} else {
			teams.get(teamNumber).add(ship);
			ship.setTeam(teamNumber);
			return true;
		}
	}

	/**
	 * Adds an addition team.
	 */
	public void addTeam() {
		teams.add(new Team());
	}

	public int getNumberOfTeams() {
		return teams.size();
	}

	public void addProjectiles(final Collection<Projectile> projectiles) {
		synchronized (projectiles) {
			this.projectiles.addAll(projectiles);
		}
	}

	public Starship getRandomShipOfTeam(final int teamNumber) {
		if (teamNumber >= teams.size() || teamNumber < 0) {
			throw new IllegalArgumentException(teamNumber + ": No such team!");
		}
		return teams.get(teamNumber).getRandomMember();
	}

	/**
	 * Draws this battlefield with the specified scaling.
	 *
	 * @param g     the Graphics object with which to draw this battlefield
	 * @param scale the scale with which to scale virtual positions to get on-screen positions
	 */
	public void draw(final Graphics g, final float scale) {
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
	}
}
