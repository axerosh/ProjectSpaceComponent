package graphics;

import game.BattleField;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Dimension;

/**
 * JComponent extension for drawing the game.
 */
public class GameComponent extends JComponent {

    private BattleField battleField;

	/**
	 * The scale from virtual coordinates/distances to ones one the screen.
	 * (If set to an integer, it is equal to a components width in pixels.)
	 */
    private final static float SCALE = 16f;
	//As of 2016-03-02, this numebr need to be equal to or greater than ~16 for shielding/power bars to be readable.

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
