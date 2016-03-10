package game;

import ship_components.ShipComponent;
import weaponry.projectiles.Projectile;

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
public class Battlefield extends GeneralVisibleEntity
{

	private Random rng;

	private final List<Starship> friendlyShips;
	private final List<Starship> enemyShips;
	private final Set<Projectile> projectiles;

	public Battlefield() {
		rng = new Random();

		friendlyShips = new ArrayList<>();
		enemyShips = new ArrayList<>();
		projectiles = new HashSet<>();
	}

	public void update() {
		updateProjectiles();
		for (Starship ship : friendlyShips) {
			ship.update();
			addProjectiles(ship.getProjectilesToFire());
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
		for (Starship friendly : friendlyShips) {
			if (friendly.contains(vx, vy)) {
				return friendly;
			}
		}
		for (Starship enemy : enemyShips) {
			if (enemy.contains(vx, vy)) {
				return enemy;
			}
		}
		return null;
	}

	public ShipComponent getComponentAt(final float vx, final float vy) {
		Starship targetShip = getShipAt(vx, vy);
		if (targetShip != null) {
			System.out.println("Ship");
			return targetShip.getComponentAt(vx, vy);
		} else {
			System.out.println("No ship");
			return null;
		}
	}

	public void addFriendlyShip(final Starship ship) {
		friendlyShips.add(ship);
	}

	public void addEnemyShip(final Starship ship) {
		enemyShips.add(ship);
	}

	public void addProjectiles(final Collection<Projectile> projectiles) {
		this.projectiles.addAll(projectiles);
	}

	public Starship getRandomFriendlyShip(){
		return friendlyShips.get(rng.nextInt(friendlyShips.size()));
	}

	public Starship getRandomEnemyShip(){
			return friendlyShips.get(rng.nextInt(enemyShips.size()));
	}

	/**
	 * Draws this battlefield with the specified scaling.
	 *
	 * @param g     the Graphics object with which to draw this battlefield
	 * @param scale the scale with which to scale virtual positions to get on-screen positions
	 */
	public void draw(final Graphics g, final float scale) {
		for (Starship friendly : friendlyShips) {
			friendly.draw(g, scale);
		}
		for (Starship enemy : enemyShips) {
			enemy.draw(g, scale);
		}
		synchronized (projectiles) {
			for (Projectile projectile : projectiles) {
				projectile.draw(g, scale);
			}
		}
	}
}
