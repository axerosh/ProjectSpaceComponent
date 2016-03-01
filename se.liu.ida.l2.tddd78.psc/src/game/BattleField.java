package game;

import projectiles.Projectile;

import java.awt.Graphics;

import java.util.ArrayList;
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
        //updateShips();
        updateProjectiles();
    }

    private void updateProjectiles() {
        for(Projectile p : projectiles){
            p.update();
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
