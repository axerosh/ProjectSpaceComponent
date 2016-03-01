package game;

import projectiles.Projectile;

import java.awt.Graphics;

import java.util.ArrayList;
import java.util.List;

public class BattleField {

    private List<StarShip> alliedShips;
    private List<StarShip> enemyShips;
    private List<Projectile> projectiles;

    public BattleField(final List<StarShip> starShips) {
        alliedShips = new ArrayList<>();
        enemyShips = new ArrayList<>();
        projectiles = new ArrayList<>();
    }

    public void addAlliedShip(final StarShip ship) {
        alliedShips.add(ship);
    }

    public void addEnemyShip(final StarShip ship) {
        enemyShips.add(ship);
    }

    public void addProjectile(final Projectile projectile){
        projectiles.add(projectile);
    }
}
