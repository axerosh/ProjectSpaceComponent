package graphics;

import game.Battlefield;
import game.VisibleEntityListener;

import javax.swing.*;
import java.awt.*;

/**
 * JComponent extension for drawing the game.
 */
public class GameDisplayer extends JComponent implements PSCGraphics, VisibleEntityListener {

    private Battlefield battlefield;

	/**
	 * The scale from virtual coordinates/distances to ones one the screen.
	 * (If set to an integer, it is equal to a components width in pixels.)
	 */
    //private final static float SCALE = 48.0f;
	private final static float SCALE = 78.0f;
	//As of 2016-03-02, this number need to be equal to or greater than ~16 for shielding/power bars to be readable.
	//Can definelty not be 0! (Will result in division by 0)

    public GameDisplayer(final Battlefield battlefield) {
	this.battlefield = battlefield;
    }

    @Override public Dimension getPreferredSize() {
	return new Dimension(600, 480);
    }


    @Override protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		battlefield.draw(g, SCALE);
    }

	public static float getSCALE() {
		return SCALE;
	}

	@Override public float getVirtualX(int screenX) {
		return screenX / SCALE;
	}

	@Override public float getVirtualY(int screenY) {
		return screenY / SCALE;
	}

	@Override public void visualUpdateRequested() {
		repaint();
	}
}
