package game;

import ship_components.ShipComponent;
import weaponry.projectiles.Projectile;

import java.awt.geom.Point2D;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An area for two teams of starships to battle each other.
 *
 * @see Starship
 */
public class Battlefield extends GeneralVisibleEntity
{

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

	/**
	 * @return the virtual position of the specified component; null if the component is null or not on the battlefield.
	 */
	public Point2D.Float getPositionOf(ShipComponent component) {
		if (component == null) {
			return null;
		}
		for (Starship friendly : friendlyShips) {
			Point2D.Float friendlyComponentPosition = friendly.getPositionOf(component);
			if (friendlyComponentPosition != null) {
				return friendlyComponentPosition;
			}
		}
		for (Starship enemy : friendlyShips) {
			Point2D.Float enemyComponentPosition = enemy.getPositionOf(component);
			if (enemyComponentPosition != null) {
				return enemyComponentPosition;
			}
		}
		return null;
	}

	public Starship getShipAt(final float vx, final float vy) {
		for (Starship friendly : friendlyShips) {
			if (friendly.contains(vx, vy)) {
				return friendly;
			}
		}
		for (Starship enemy : friendlyShips) {
			if (enemy.contains(vx, vy)) {
				return enemy;
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
