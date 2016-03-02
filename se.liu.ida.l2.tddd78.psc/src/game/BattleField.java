package game;

import projectiles.Projectile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BattleField {

    private List<StarShip> friendlyShips;
    private List<StarShip> enemyShips;
    private List<Projectile> projectiles;

    public BattleField() {
        friendlyShips = new ArrayList<>();
        enemyShips = new ArrayList<>();
        projectiles = new ArrayList<>();
    }

    public void update(){
        for(StarShip ship : friendlyShips){
            ship.update();
        }
        updateProjectiles();
    }

    private void updateProjectiles() {
        Collection<Projectile> projectilesToRemove = new ArrayList<>();
        for(Projectile p : projectiles){
            p.update();
            if(p.hasImpact()){
                projectilesToRemove.add(p);
            }

        }
        for(Projectile p : projectilesToRemove){
            projectiles.remove(p);
        }

    }

    /**
	 * Performs the activation action of the ship that the cursor hovers over.
	 *
	 * @param vx the cursor's virtual x-position
	 * @param vy the cursor's virtual y-position
	 */
	public void activateWithCursor(final float vx, final float vy) {
		for (StarShip ship: friendlyShips) {
			if (ship.contains(vx, vy)) {
				ship.activateWithCursor(vx, vy);
				return;
			}
		}
		for (StarShip ship: enemyShips) {
			if (ship.contains(vx, vy)) {
				ship.activateWithCursor(vx, vy);
				return;
			}
		}
	}

	/**
	 * Performs the deactivation action of the ship that the cursor hovers over.
	 *
	 * @param vx the cursor's virtual x-position
	 * @param vy the cursor's virtual y-position
	 */
	public void deactivateWithCursor(final float vx, final float vy) {
		for (StarShip ship: friendlyShips) {
			if (ship.contains(vx, vy)) {
				ship.deactivateWithCursor(vx, vy);
				return;
			}
		}
		for (StarShip ship: enemyShips) {
			if (ship.contains(vx, vy)) {
				ship.deactivateWithCursor(vx, vy);
				return;
			}
		}
	}

    public void addFriendlyShip(final StarShip ship) {
        friendlyShips.add(ship);
    }

    public void addEnemyShip(final StarShip ship) {
        enemyShips.add(ship);
    }

    public void addProjectile(final Projectile projectile){
        projectiles.add(projectile);
    }

    /**
     * Draws this battlefield with the specified scaling.
     *
     * @param g the Graphics object with which to draw this battlefield
     * @param scale the scale with which to scale virtual positions to get on-screen positions
     */
    public void draw(final Graphics g, final float scale) {
        for (StarShip friendly: friendlyShips) {
            friendly.draw(g, scale);
        }
        for (StarShip enemy: enemyShips) {
            enemy.draw(g, scale);
        }
        for (Projectile projectile: projectiles){
            projectile.draw(g, scale);
        }
    }
}
