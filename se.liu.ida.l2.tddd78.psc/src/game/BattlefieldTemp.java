package game;

import weaponry.projectiles.Projectile;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An area for two teams of starships to battle each other.
 *
 * @see StarshipTemp
 */
public class BattlefieldTemp extends GeneralVisibleEntity {

	private List<StarshipTemp> friendlyShips;
	private List<StarshipTemp> enemyShips;
	private List<Projectile> projectiles;

	public BattlefieldTemp() {
		friendlyShips = new ArrayList<>();
		enemyShips = new ArrayList<>();
		projectiles = new ArrayList<>();
	}

	public void update() {
		updateProjectiles();
		for (StarshipTemp ship : friendlyShips) {
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
		for (StarshipTemp ship : friendlyShips) {
			if (ship.contains(vx, vy)) {
				ship.increaseShieldingOfComponentAt(vx, vy);
				return;
			}
		}
		for (StarshipTemp ship : enemyShips) {
			if (ship.contains(vx, vy)) {
				ship.increaseShieldingOfComponentAt(vx, vy);
				return;
			}
		}
	}

	public void decreaseShieldingOfShipAt(final float vx, final float vy) {
		for (StarshipTemp ship : friendlyShips) {
			if (ship.contains(vx, vy)) {
				ship.decreaseShieldingOfComponentAt(vx, vy);
				return;
			}
		}
		for (StarshipTemp ship : enemyShips) {
			if (ship.contains(vx, vy)) {
				ship.decreaseShieldingOfComponentAt(vx, vy);
				return;
			}
		}
	}

	public void increasePowerOfShipAt(final float vx, final float vy) {
		for (StarshipTemp ship : friendlyShips) {
			if (ship.contains(vx, vy)) {
				ship.increasePowerOfComponentAt(vx, vy);
				return;
			}
		}
		for (StarshipTemp ship : enemyShips) {
			if (ship.contains(vx, vy)) {
				ship.increasePowerOfComponentAt(vx, vy);
				return;
			}
		}
	}

	public void decreasePowerOfShipAt(final float vx, final float vy) {
		for (StarshipTemp ship : friendlyShips) {
			if (ship.contains(vx, vy)) {
				ship.decreasePowerOfComponentAt(vx, vy);
				return;
			}
		}
		for (StarshipTemp ship : enemyShips) {
			if (ship.contains(vx, vy)) {
				ship.decreasePowerOfComponentAt(vx, vy);
				return;
			}
		}
	}

	public void addFriendlyShip(final StarshipTemp ship) {
		friendlyShips.add(ship);
	}

	public void addEnemyShip(final StarshipTemp ship) {
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
		for (StarshipTemp friendly : friendlyShips) {
			friendly.draw(g, scale);
		}
		for (StarshipTemp enemy : enemyShips) {
			enemy.draw(g, scale);
		}
		for (Projectile projectile : projectiles) {
			projectile.draw(g, scale);
		}
	}
}
