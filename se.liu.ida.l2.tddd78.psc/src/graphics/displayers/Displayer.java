package graphics.displayers;

import game.VisibleEntityListener;
import graphics.PSCGraphics;

import javax.swing.*;
import java.awt.*;

public abstract class Displayer extends JComponent implements PSCGraphics, VisibleEntityListener{

	/**
	 * The scale from virtual coordinates/distances to ones one the screen. (If set to an integer, it is equal to a components
	 * width in pixels.)
	 */
	protected final static float SCALE = 40.0f;
	//As of 2016-03-02, this number need to be equal to or greater than ~16 for shielding/power bars to be readable.
	//Can definelty not be 0! (Will result in division by 0)

	protected final int WIDTH = (int) (32 * SCALE);
	protected final int HEIGHT = (int) (18 * SCALE);

	@Override public float getVirtualX(int screenX) {
		return (int)(screenX / SCALE);
	}

	@Override public float getVirtualY(int screenY) {
		return (int)(screenY / SCALE);
	}

	@Override public void visualUpdateRequested() {
		repaint();
	}

	@Override public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
}
