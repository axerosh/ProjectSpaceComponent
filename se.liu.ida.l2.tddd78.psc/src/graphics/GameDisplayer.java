package graphics;

import game.Battlefield;
import game.VisibleEntityListener;

import javax.swing.*;
import java.awt.*;

/**
 * JComponent extension that displays a game.
 */
public class GameDisplayer extends JComponent implements PSCGraphics, VisibleEntityListener
{

	/**
	 * The scale from virtual coordinates/distances to ones one the screen. (If set to an integer, it is equal to a components
	 * width in pixels.)
	 */
	private final static float SCALE = 48.0f;
	private Battlefield battlefield;
	//As of 2016-03-02, this number need to be equal to or greater than ~16 for shielding/power bars to be readable.
	//Can definelty not be 0! (Will result in division by 0)

	public GameDisplayer(final Battlefield battlefield) {
		this.battlefield = battlefield;
	}

	@Override public Dimension getPreferredSize() {
		final int battlefieldWidth = 13;
		final int battlefieldHeight = 7;
		return new Dimension((int) (battlefieldWidth * SCALE), (int) (battlefieldHeight * SCALE));
	}

	@Override protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		battlefield.draw(g, SCALE);
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
