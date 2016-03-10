package game;

import component.ShipComponent;
import weaponry.projectile.Projectile;

import java.awt.Graphics;
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
public class Battlefield extends GeneralVisibleEntity {

	private final List<List<Starship>> teams;
	private final Set<Projectile> projectiles;
	private Random rng;

	public Battlefield() {
		rng = new Random();

		teams = new ArrayList<>();
		teams.add(new ArrayList<>());
		teams.add(new ArrayList<>());
		projectiles = new HashSet<>();
	}

	public void update() {
		updateProjectiles();
		for (List<Starship> team : teams) {
			for (Starship ship : team) {
				ship.update();
				addProjectiles(ship.getProjectilesToFire());
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
		for (List<Starship> team : teams) {
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
	 * @param team team number from 0 to number of teams - 1
	 *
	 * @return false if failed
	 * @see #getNumberOfTeams
	 */
	public boolean addShip(final Starship ship, final int team) {
		if (team >= teams.size()) {
			return false;
		} else {
			teams.get(team).add(ship);
			ship.setTeam(team);
			return true;
		}
	}

	/**
	 * Adds an addition team.
	 */
	public void addTeam() {
		teams.add(new ArrayList<>());
	}

	public int getNumberOfTeams() {
		return teams.size();
	}

	public void addProjectiles(final Collection<Projectile> projectiles) {
		this.projectiles.addAll(projectiles);
	}

	public Starship getRandomShipOfTeam(final int teamNumber) {
		if (teamNumber >= teams.size()) {
			throw new IllegalArgumentException(teamNumber + ": No such team!");
		}
		List<Starship> team = teams.get(teamNumber);
		if (team.isEmpty()) {
			return null;
		}
		return team.get(rng.nextInt(team.size()));
	}
	/**
	 * Draws this battlefield with the specified scaling.
	 *
	 * @param g     the Graphics object with which to draw this battlefield
	 * @param scale the scale with which to scale virtual positions to get on-screen positions
	 */
	public void draw(final Graphics g, final float scale) {
		for (List<Starship> team : teams) {
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
