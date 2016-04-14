package game;

import ship.Starship;
import ship.component.ShipComponent;
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
public class BattleSpace extends GeneralVisibleEntity {

	private final List<Team> teams;
	private final Set<Projectile> projectiles;
	private Random rng;
	private Team winningTeam;
	private boolean gameover;

	public BattleSpace() {
		rng = new Random();

		teams = new ArrayList<>();

		projectiles = new HashSet<>();
		winningTeam = null;
		gameover = false;
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
	 * @return false if failed
	 * @see #getNumberOfTeams
	 */
	public boolean addShip(final Starship ship, final Team team) {
		team.add(ship);
		ship.setTeam(team);
		return true;
	}

	/**
	 * Adds an addition team.
	 */
	public void addTeam(String teamName) {
		teams.add(new Team(teamName));
	}

	public int getNumberOfTeams() {
		return teams.size();
	}

	public void addProjectiles(final Collection<Projectile> projectiles) {
		synchronized (projectiles) {
			this.projectiles.addAll(projectiles);
		}
	}

	//TODO Detta är otrolig fulprogramering, fixa FFS! :P
	public Team getRandomHostileTeam(Team ownTeam){
		teams.remove(teams.indexOf(ownTeam));
		Team hostileTeam = teams.get(rng.nextInt(teams.size()));
		teams.add(ownTeam);
		return hostileTeam;
	}

	public Starship getRandomShipOfTeam(final Team team) {
		if (!teams.contains(team)) {
			throw new IllegalArgumentException(team.getTeamName() + ": No such team!");
		}
		return teams.get(teams.indexOf(team)).getRandomMember();
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

	public void placeShip(Starship ship){
		if(ship.getTeam().getTeamName() == teams.get(0).getTeamName()){
			ship.setXPosition(1);
			ship.setYPosition((teams.get(0).getTeamSize() - 1) * 8);
		}else if (ship.getTeam().getTeamName() == teams.get(1).getTeamName()){
			ship.setXPosition(17);
			ship.setYPosition((teams.get(1).getTeamSize() - 1) * 8);
		}
	}
}
