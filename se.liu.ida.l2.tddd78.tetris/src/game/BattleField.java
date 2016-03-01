package game;

import java.util.ArrayList;
import java.util.List;

public class BattleField {

    private List<StarShip> alliedShips;
    private List<StarShip> enemyShips;

    public BattleField(final List<StarShip> starShips) {
        this.alliedShips = new ArrayList<>();
        this.enemyShips = new ArrayList<>();
    }

    public void addAlliedShip(final StarShip ship) {
        alliedShips.add(ship);
    }

    public void addEnemyShip(final StarShip ship) {
        alliedShips.add(ship);
    }
}
