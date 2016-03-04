package game;

import projectiles.Projectile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BattleField extends GeneralVisibleEntity {

    private List<StarShip> friendlyShips;
    private List<StarShip> enemyShips;
    private List<Projectile> projectiles;

	private List<VisibleEntityListener> visibleEntityListeners;

    public BattleField() {
        friendlyShips = new ArrayList<>();
        enemyShips = new ArrayList<>();
        projectiles = new ArrayList<>();
    }

    public void update(){
        updateProjectiles();
        for(StarShip ship : friendlyShips){
            addProjectiles(ship.update());
        }

    }

    private void updateProjectiles() {
        List<Projectile> projectilesToRemove = new ArrayList<>();
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
	 * Tries to change the stat, which indicator bar is at the specified virtual position, with the specified amount.
	 *
	 * @param vx a virtual x-position
	 * @param vy a virtual y-position
	 * @param change amount with which the stat is to be changed
	 */
	public void changeStatIndicatedAt(final float vx, final float vy, int change) {
		for (StarShip ship: friendlyShips) {
			if (ship.contains(vx, vy)) {
				ship.changeStatIndicatedAt(vx, vy, change);
				return;
			}
		}
		for (StarShip ship: enemyShips) {
			if (ship.contains(vx, vy)) {
				ship.changeStatIndicatedAt(vx, vy, change);
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

    public void addProjectiles(final List<Projectile> projectiles){
        this.projectiles.addAll(projectiles);
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
