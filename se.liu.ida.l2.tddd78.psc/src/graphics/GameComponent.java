package graphics;

import game.BattleField;

import javax.swing.*;
import java.awt.*;

public class GameComponent extends JComponent {

    BattleField battleField;
    private final static float SCALE = 20f;

    public GameComponent(final BattleField battleField) {
	this.battleField = battleField;
    }

    @Override public Dimension getPreferredSize() {
	return new Dimension(600, 480);
    }


    @Override protected void paintComponent(final Graphics g) {
	super.paintComponent(g);
	battleField.draw(g, SCALE);
    }
}
