package game;

import weaponry.projectiles.Projectile;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An area for two teams of starships to battle each other.
 *
 * @see Starship
 */
public class Battlefield extends GeneralVisibleEntity {

	private List<Starship> friendlyShips;
	private List<Starship> enemyShips;
	private List<Projectile> projectiles;

	public Battlefield() {
		friendlyShips = new ArrayList<>();
		enemyShips = new ArrayList<>();
		projectiles = new ArrayList<>();
	}

	public void update() {
		updateProjectiles();
		for (Starship ship : friendlyShips) {
			addProjectiles(ship.update());
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
		for (Projectile p : projectilesToRemove) {
			projectiles.remove(p);
		}

	}

	public void increaseShieldingOfShipAt(final float vx, final float vy) {
		for (Starship ship : friendlyShips) {
			if (ship.contains(vx, vy)) {
				ship.increaseShieldingOfComponentAt(vx, vy);
				return;
			}
		}
		for (Starship ship : enemyShips) {
			if (ship.contains(vx, vy)) {
				ship.increaseShieldingOfComponentAt(vx, vy);
				return;
			}
		}
	}

	public void decreaseShieldingOfShipAt(final float vx, final float vy) {
		for (Starship ship : friendlyShips) {
			if (ship.contains(vx, vy)) {
				ship.decreaseShieldingOfComponentAt(vx, vy);
				return;
			}
		}
		for (Starship ship : enemyShips) {
			if (ship.contains(vx, vy)) {
				ship.decreaseShieldingOfComponentAt(vx, vy);
				return;
			}
		}
	}

	public void increasePowerOfShipAt(final float vx, final float vy) {
		for (Starship ship : friendlyShips) {
			if (ship.contains(vx, vy)) {
				ship.increasePowerOfComponentAt(vx, vy);
				return;
			}
		}
		for (Starship ship : enemyShips) {
			if (ship.contains(vx, vy)) {
				ship.increasePowerOfComponentAt(vx, vy);
				return;
			}
		}
	}

	public void decreasePowerOfShipAt(final float vx, final float vy) {
		for (Starship ship : friendlyShips) {
			if (ship.contains(vx, vy)) {
				ship.decreasePowerOfComponentAt(vx, vy);
				return;
			}
		}
		for (Starship ship : enemyShips) {
			if (ship.contains(vx, vy)) {
				ship.decreasePowerOfComponentAt(vx, vy);
				return;
			}
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
		for (Projectile projectile : projectiles) {
			projectile.draw(g, scale);
		}
	}
}
